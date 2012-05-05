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
import org.jboss.seam.security.annotations.LoggedIn;
import org.ocpsoft.common.util.Assert;

import com.ocpsoft.socialpm.gwt.client.shared.rpc.ProjectService;
import com.ocpsoft.socialpm.gwt.client.shared.rpc.StoryService;
import com.ocpsoft.socialpm.gwt.server.security.authentication.Authenticated;
import com.ocpsoft.socialpm.gwt.server.util.HibernateDetachUtility;
import com.ocpsoft.socialpm.gwt.server.util.HibernateDetachUtility.SerializationType;
import com.ocpsoft.socialpm.gwt.server.util.PersistenceUtil;
import com.ocpsoft.socialpm.model.feed.StoryCreated;
import com.ocpsoft.socialpm.model.feed.TaskCreated;
import com.ocpsoft.socialpm.model.feed.ValidationCreated;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.project.story.Status;
import com.ocpsoft.socialpm.model.project.story.Story;
import com.ocpsoft.socialpm.model.project.story.Task;
import com.ocpsoft.socialpm.model.project.story.ValidationCriteria;
import com.ocpsoft.socialpm.model.user.Profile;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
@Service
@RequestScoped
public class StoryServiceImpl extends PersistenceUtil implements StoryService
{
   private static final long serialVersionUID = -3585401813289703156L;

   @PersistenceContext(type = PersistenceContextType.EXTENDED)
   protected EntityManager em;

   @Inject
   private Event<StoryCreated> storyCreated;

   @Inject
   private Event<TaskCreated> taskCreated;

   @Inject
   private Event<ValidationCreated> validationCreated;

   @Inject
   private ProjectService ps;

   @Inject
   @Authenticated
   private Profile user;

   @Override
   public EntityManager getEntityManager()
   {
      return em;
   }

   @Override
   @LoggedIn
   @TransactionAttribute
   public Task createTask(Story story, Task task)
   {
      if (!em.contains(story))
         story = reload(story);

      task.setStory(story);

      task.setStatus(Status.NOT_STARTED);
      if (task.getAssignee() != null && !em.contains(task.getAssignee()))
         task.setAssignee(reload(task.getAssignee()));

      super.create(task);

      TaskCreated createdEvent = new TaskCreated(user, story, task);

      super.create(createdEvent);

      taskCreated.fire(createdEvent);

      return task;
   }

   @Override
   @LoggedIn
   @TransactionAttribute
   public ValidationCriteria createValidation(Story story, ValidationCriteria criteria)
   {
      if (!em.contains(story))
         story = reload(story);

      criteria.setStory(story);

      super.create(criteria);

      ValidationCreated createdEvent = new ValidationCreated(user, story, criteria);

      super.create(createdEvent);

      validationCreated.fire(createdEvent);

      return criteria;
   }

   @Override
   @LoggedIn
   @TransactionAttribute
   public Story create(Project project, Story story)
   {
      if (!em.contains(project))
         project = reload(project);

      story.setProject(project);

      if (story.getIteration() != null && !em.contains(story.getIteration()))
         story.setIteration(reload(story.getIteration()));
      else if (story.getIteration() == null)
         story.setIteration(project.getDefaultIteration());

      story.setNumber(getNextStoryNumber(project));

      super.create(story);

      StoryCreated createdEvent = new StoryCreated(user, story);
      super.create(createdEvent);
      storyCreated.fire(createdEvent);

      return story;
   }

   private int getNextStoryNumber(Project project)
   {
      if (!em.contains(project))
         project = reload(project);

      TypedQuery<Long> query = em.createQuery("SELECT count(*) + 1 FROM Story s WHERE s.project = :project",
               Long.class);
      query.setParameter("project", project);
      return query.getSingleResult().intValue();
   }

   @Override
   @TransactionAttribute
   public Story getByOwnerSlugAndNumber(Profile owner, String slug, int storyNumber)
   {
      Assert.notNull(owner, "Profile was null");
      Assert.notNull(slug, "Project slug was null");

      Project project = ps.getByOwnerAndSlug(owner, slug);

      Story result = null;
      try {
         result = findUniqueByNamedQuery("Story.byProjectAndNumber", project, storyNumber);
         result = reload(result);
         em.detach(result);
         HibernateDetachUtility.nullOutUninitializedFields(result, SerializationType.SERIALIZATION);
      }
      catch (NoResultException e) {
         // okay
      }

      return result;
   }
}
