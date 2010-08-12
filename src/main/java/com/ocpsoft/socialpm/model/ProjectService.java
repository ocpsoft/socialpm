/**
 * This file is part of SocialPM: Agile Project Management Tools (SocialPM) 
 *
 * Copyright (c)2010 Lincoln Baxter, III <lincoln@ocpsoft.com> (OcpSoft)
 * 
 * If you are developing and distributing open source applications under 
 * the GPL Licence, then you are free to use SocialPM under the GPL 
 * License:
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
 * For OEMs, ISVs, and VARs who distribute SocialPM with their products, 
 * host their product online, OcpSoft provides flexible OEM commercial 
 * Licences. 
 * 
 * Optionally, customers may choose a Commercial License. For additional 
 * details, contact OcpSoft (http://ocpsoft.com)
 */

package com.ocpsoft.socialpm.model;

import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.ocpsoft.data.PersistenceUtil;
import com.ocpsoft.socialpm.domain.feed.ProjectCreated;
import com.ocpsoft.socialpm.domain.project.Feature;
import com.ocpsoft.socialpm.domain.project.MemberRole;
import com.ocpsoft.socialpm.domain.project.Membership;
import com.ocpsoft.socialpm.domain.project.Milestone;
import com.ocpsoft.socialpm.domain.project.Points;
import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.domain.project.iteration.Iteration;
import com.ocpsoft.socialpm.domain.project.stories.Story;
import com.ocpsoft.socialpm.domain.project.stories.StoryStatus;
import com.ocpsoft.socialpm.domain.project.stories.Task;
import com.ocpsoft.socialpm.domain.project.stories.TaskStatus;
import com.ocpsoft.socialpm.domain.user.User;
import com.ocpsoft.util.Strings;

@Stateful
public class ProjectService extends PersistenceUtil
{
   private static final long serialVersionUID = 6301111544805213272L;

   @PersistenceContext(type = PersistenceContextType.EXTENDED)
   private EntityManager entityManager;

   @Override
   protected EntityManager getEntityManager()
   {
      return entityManager;
   }

   @Inject
   private IterationService is;

   @Inject
   StoryService ss;

   @SuppressWarnings("unchecked")
   public List<Project> list(final int limit, final int offset)
   {
      Query query = getEntityManager().createNamedQuery("project.list", Project.class);
      query.setFirstResult(offset);
      query.setMaxResults(limit);
      return query.getResultList();
   }

   public int getProjectCount()
   {
      return ((Long) this.findUniqueByNamedQuery("project.count")).intValue();
   }

   public Project getByName(final String name)
   {
      return this.findUniqueByNamedQuery("project.byCanonicalName", Strings.canonicalize(name));
   }

   public void createProject(final User owner, final Project project)
   {
      project.setCanonicalName(Strings.canonicalize(project.getName()));

      this.create(project);

      Iteration iteration = new Iteration();
      iteration.setTitle("Unassigned");
      addIteration(project, iteration);

      Feature feature = new Feature();
      feature.setName("Bug Fixes");
      addFeature(project, feature);

      feature = new Feature();
      feature.setName("Enhancements");
      addFeature(project, feature);

      feature = new Feature();
      feature.setName("Unclassified");
      addFeature(project, feature);

      addMembership(project, new Membership(project, owner, MemberRole.OWNER));

      this.save(project);
      this.create(new ProjectCreated(project, owner));
   }

   private void addMembership(final Project project, final Membership membership)
   {
      project.getMemberships().add(membership);
      membership.setProject(project);
      this.create(membership);
   }

   public void saveProjectDetails(final long projectId, final Project project)
   {
      Project p = this.findById(Project.class, projectId);
      p.setVision(project.getVision());
      p.setGoals(project.getGoals());
      p.setObjectives(project.getObjectives());
      this.save(p);
   }

   public void inviteMember(final long projectId, final long userId)
   {
      Project project = this.findById(Project.class, projectId);
      User user = this.findById(User.class, userId);

      if (!project.getAllMembers().contains(user))
      {
         Membership membership = new Membership(project, user, MemberRole.INVITED);
         project.getMemberships().add(membership);
      }
      this.save(project);
   }

   public void requestMembership(final Project project, final User user)
   {
      if (!project.getAllMembers().contains(user))
      {
         Membership membership = new Membership(project, user, MemberRole.REQUESTED);
         project.getMemberships().add(membership);
      }
      this.save(project);
   }

   public void setMemberRole(final Project project, final User user, final MemberRole role)
   {
      Membership member = project.getMembership(user);
      member.setRole(role);
      this.save(member);
   }

   public void leaveProject(final Project project, final User user)
   {
      Membership member = project.getMembership(user);
      if (project.getOwners().size() >= 1)
      {
         removeTaskAssignments(project, user);
         project.getMemberships().remove(member);
         this.delete(member);
      }
      else
      {
         // TODO handle this with nice client exception message
         throw new PersistenceException("Cannot remove last Owner of a Project");
      }
   }

   @SuppressWarnings("unchecked")
   private void removeTaskAssignments(final Project project, final User user)
   {
      Query query = getEntityManager().createQuery("FROM Task t WHERE t.story.project = :project");
      query.setParameter("project", project);
      List<Task> tasks = query.getResultList();

      query = getEntityManager().createQuery(
               "UPDATE Task t SET t.assignee = null WHERE t.assignee = :user "
                        + "AND t.status <> :status AND t IN (:list)");
      query.setParameter("user", user);
      query.setParameter("status", TaskStatus.DONE);
      query.setParameter("list", tasks);
      query.executeUpdate();
   }

   public void addStory(final Project project, final Iteration iteration, final Story story)
   {
      story.setProject(project);
      project.getStories().add(story);

      if (story.getStoryPoints() == null)
      {
         story.setStoryPoints(Points.NOT_POINTED);
      }

      if (story.getBusinessValue() == null)
      {
         story.setBusinessValue(Points.NOT_POINTED);
      }

      story.setStatus(StoryStatus.OPEN);

      /*
       * For some reason, these operations must take place in the following order, otherwise iterations are not added to
       * the history collection.
       */
      iteration.getStories().add(story);
      story.setIteration(iteration);
      create(story);
      refresh(story);

      is.updateIterationStatistics(iteration);
   }

   public Story getStory(final Project project, final int storyNumber)
   {
      Story s = this.findUniqueByNamedQuery("Story.byProjectAndNumber", project.getId(), storyNumber);
      return s;
   }

   public List<Story> getStories(final long projectId)
   {
      return this.findByNamedQuery("Story.byProjectId", projectId);
   }

   public List<Story> getStoriesByFeature(final long projectId, final long featureId)
   {
      return this.findByNamedQuery("Story.byProjectAndFeatureId", projectId, featureId);
   }

   public List<Story> getFrontBurnerStories(final long projectId, final long iterationId)
   {
      Iteration iteration = this.findById(Iteration.class, iterationId);
      return iteration.getFrontShelfStories();
   }

   public void addFeature(final Project project, final Feature feature)
   {
      project.getFeatures().add(feature);
      feature.setProject(project);
      this.create(feature);
   }

   public void removeFeature(final Project project, final Feature feature)
   {
      if (feature.getProject().equals(project))
      {
         project.getFeatures().remove(feature);
         feature.setProject(null);
      }
      save(project);
   }

   public int mergeFeatures(final Feature from, final Feature to)
   {
      assert from.getProject().equals(to.getProject());

      Query query = getEntityManager()
               .createQuery("update Story set feature = :newFeature where feature = :oldFeature");
      query.setParameter("newFeature", to);
      query.setParameter("oldFeature", from);
      int updated = query.executeUpdate();

      from.getProject().getFeatures().remove(from);
      this.delete(from);

      return updated;
   }

   public void addIteration(final Project project, final Iteration iteration)
   {
      project.getIterations().add(iteration);
      iteration.setProject(project);
      this.save(project);
   }

   public void commitIteration(final long projectId, final long iterationId)
   {
      Iteration iteration = this.findById(Iteration.class, iterationId);
      is.commit(iteration);
   }

   public void removeIteration(final long projectId, final long iterationId)
   {
      Iteration iteration = this.findById(Iteration.class, iterationId);
      is.removeIteration(iteration);
   }

   public void addMilestone(final Project project, final Milestone milestone)
   {
      milestone.setProject(project);
      project.getMilestones().add(milestone);
      save(project);
   }

   public void saveMilestone(final long projectId, final Milestone milestone)
   {
      Milestone m = this.findById(Milestone.class, milestone.getId());

      m.setTargetDate(milestone.getTargetDate());
      m.setDescription(milestone.getDescription());
      m.setName(milestone.getName());

      this.save(m);
   }

   public void removeMilestone(final Project project, final Milestone milestone)
   {
      for (Story s : milestone.getStories())
      {
         s.setMilestone(null);
         milestone.getStories().remove(s);
      }

      milestone.getProject().getMilestones().remove(milestone);
      milestone.setProject(null);

      this.delete(milestone);
   }
}
