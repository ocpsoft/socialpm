/**
 * This file is part of OCPsoft SocialPM: Agile Project Management Tools (SocialPM)
 *
 * Copyright (c)2011 Lincoln Baxter, III <lincoln@ocpsoft.com> (OCPsoft)
 * Copyright (c)2011 OCPsoft.com (http://ocpsoft.com)
 *
 * If you are developing and distributing open source applications under
 * the GNU General Public License (GPL), then you are free to re-distribute SocialPM
 * under the terms of the GPL, as follows:
 *
 * SocialPM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SocialPM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SocialPM.  If not, see <http://www.gnu.org/licenses/>.
 *
 * For individuals or entities who wish to use SocialPM privately, or
 * internally, the following terms do not apply:
 *
 * For OEMs, ISVs, and VARs who wish to distribute SocialPM with their
 * products, or host their product online, OCPsoft provides flexible
 * OEM commercial licenses.
 *
 * Optionally, Customers may choose a Commercial License. For additional
 * details, contact an OCPsoft representative (sales@ocpsoft.com)
 */
package com.ocpsoft.socialpm.gwt.server.rpc;

import javax.ejb.TransactionAttribute;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

import org.jboss.errai.bus.server.annotations.Service;
import org.ocpsoft.common.util.Assert;

import com.ocpsoft.socialpm.gwt.client.shared.rpc.ProjectService;
import com.ocpsoft.socialpm.gwt.client.shared.rpc.StoryService;
import com.ocpsoft.socialpm.gwt.server.security.authorization.ProfileBinding;
import com.ocpsoft.socialpm.gwt.server.security.authorization.ProjectAdmin;
import com.ocpsoft.socialpm.gwt.server.util.HibernateDetachUtility;
import com.ocpsoft.socialpm.gwt.server.util.HibernateDetachUtility.SerializationType;
import com.ocpsoft.socialpm.gwt.server.util.PersistenceUtil;
import com.ocpsoft.socialpm.model.feed.StoryCreated;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.project.story.Story;
import com.ocpsoft.socialpm.model.user.Profile;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
@RequestScoped
@Service
public class StoryServiceImpl extends PersistenceUtil implements StoryService
{
   private static final long serialVersionUID = -3585401813289703156L;

   @PersistenceContext(type = PersistenceContextType.EXTENDED)
   protected EntityManager em;

   @Inject
   private Event<StoryCreated> storyCreated;

   @Inject
   private ProjectService ps;

   @Override
   public EntityManager getEntityManager()
   {
      return em;
   }

   @Override
   @TransactionAttribute
   @ProjectAdmin
   public Story create(@ProfileBinding Profile owner, Project project, Story story)
   {
      if (!em.contains(project))
         project = ps.getByOwnerAndSlug(owner, project.getSlug());
      story.setProject(project);

      if (story.getIteration() != null && !em.contains(story.getIteration()))
         story.setIteration(reload(story.getIteration()));
      else if (story.getIteration() == null)
         story.setIteration(project.getDefaultIteration());

      project.getStories().add(story);

      super.create(story);

      story.setNumber(getNextStoryNumber(owner, project) - 1);

      StoryCreated createdEvent = new StoryCreated(owner, story);
      super.create(createdEvent);
      storyCreated.fire(createdEvent);

      return story;
   }

   private int getNextStoryNumber(Profile owner, Project project)
   {
      if (!em.contains(project))
         project = ps.getByOwnerAndSlug(owner, project.getSlug());

      TypedQuery<Long> query = em.createQuery("SELECT count(*) + 1 FROM Story s WHERE s.project = :project",
               Long.class);
      query.setParameter("project", project);
      return query.getSingleResult().intValue();
   }

   @Override
   public Story getByOwnerSlugAndNumber(Profile profile, String slug, int storyNumber)
   {
      Assert.notNull(profile, "Profile was null");
      Assert.notNull(slug, "Project slug was null");

      Project project = ps.getByOwnerAndSlug(profile, slug);

      Story result = null;
      try {
         result = findUniqueByNamedQuery("Story.byProjectAndNumber", project, storyNumber);
         em.detach(result);
         HibernateDetachUtility.nullOutUninitializedFields(result, SerializationType.SERIALIZATION);
      }
      catch (NoResultException e) {
         // okay
      }

      return result;
   }
}
