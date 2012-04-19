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

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.jboss.errai.bus.server.annotations.Service;
import org.ocpsoft.common.util.Assert;

import com.ocpsoft.socialpm.gwt.client.shared.rpc.ProjectService;
import com.ocpsoft.socialpm.gwt.server.security.authorization.ProfileBinding;
import com.ocpsoft.socialpm.gwt.server.security.authorization.ProjectAdmin;
import com.ocpsoft.socialpm.gwt.server.util.HibernateDetachUtility;
import com.ocpsoft.socialpm.gwt.server.util.HibernateDetachUtility.SerializationType;
import com.ocpsoft.socialpm.gwt.server.util.PersistenceUtil;
import com.ocpsoft.socialpm.model.feed.ProjectCreated;
import com.ocpsoft.socialpm.model.project.Feature;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.project.iteration.Iteration;
import com.ocpsoft.socialpm.model.user.Profile;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
@RequestScoped
@Service
public class ProjectServiceImpl extends PersistenceUtil implements ProjectService
{
   private static final long serialVersionUID = 1403645951285144409L;

   @PersistenceContext(type = PersistenceContextType.EXTENDED)
   protected EntityManager em;

   @Inject
   private Event<ProjectCreated> projectCreated;

   @Override
   public EntityManager getEntityManager()
   {
      return em;
   }

   @Override
   @TransactionAttribute
   @ProjectAdmin
   public Project create(@ProfileBinding Profile owner, String projectName)
   {
      Project project = new Project();
      project.setName(projectName);
      project.setSlug(projectName);

      Assert.notNull(owner, "Profile was null");

      project.setOwner(reload(owner));

      super.create(project);

      Iteration unassigned = new Iteration();
      unassigned.setProject(project);
      unassigned.setTitle("Backlog");
      project.getIterations().add(unassigned);
      super.create(unassigned);

      Feature bugFixes = new Feature();
      bugFixes.setName("Bug Fixes");
      bugFixes.setProject(project);
      project.getFeatures().add(bugFixes);
      bugFixes.setProject(project);
      super.create(bugFixes);

      Feature enhancements = new Feature();
      enhancements.setName("Enhancements");
      enhancements.setProject(project);
      project.getFeatures().add(enhancements);
      enhancements.setProject(project);
      super.create(enhancements);

      Feature unclassified = new Feature();
      unclassified.setName("Unclassified");
      unclassified.setProject(project);
      project.getFeatures().add(unclassified);
      unclassified.setProject(project);
      super.create(unclassified);

      super.save(project);

      // p.getMemberships().add(new Membership(p, owner, MemberRole.OWNER));

      ProjectCreated createdEvent = new ProjectCreated(owner, project);
      super.create(createdEvent);
      projectCreated.fire(createdEvent);

      return project;
   }

   @Override
   public boolean verifyAvailable(Profile owner, String projectName)
   {
      return null == getByOwnerAndSlug(owner, projectName);
   }

   public void save(final Project p)
   {
      super.save(p);
   }

   public long getProjectCount()
   {
      return count(Project.class);
   }

   @Override
   public Project getByOwnerAndSlug(final Profile profile, final String slug)
   {
      Assert.notNull(profile, "Profile was null");
      Assert.notNull(slug, "Project slug was null");

      Project result = null;
      try {
         result = findUniqueByNamedQuery("Project.byProfileAndSlug", profile.getUsername(), slug);
         em.detach(result);
         HibernateDetachUtility.nullOutUninitializedFields(result, SerializationType.SERIALIZATION);
      }
      catch (NoResultException e) {
         // okay
      }

      return result;
   }

   @Override
   public List<Project> getByOwner(Profile profile)
   {
      Assert.notNull(profile, "Profile was null");
      Assert.notNull(profile.getUsername(), "Username was null");

      List<Project> list = findByNamedQuery("Project.byProfileName", profile.getUsername());
      for (Project project : list) {
         em.detach(project);
         HibernateDetachUtility.nullOutUninitializedFields(project, SerializationType.SERIALIZATION);
      }
      System.out.println(list);
      return list;
   }
}
