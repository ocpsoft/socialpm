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

package com.ocpsoft.spm.ws.rest.resources;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.ocpsoft.socialpm.domain.DataException;
import com.ocpsoft.socialpm.domain.PersistenceUtil;
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
import com.ocpsoft.socialpm.domain.project.stories.ValidationCriteria;
import com.ocpsoft.socialpm.domain.user.Profile;
import com.ocpsoft.socialpm.model.project.IterationService;
import com.ocpsoft.socialpm.util.Strings;

@Path("/projects")
public class ProjectResource extends PersistenceUtil
{
   private static final long serialVersionUID = 8078531824053093624L;

   @Inject
   private IterationService is;

   @PersistenceContext(type = PersistenceContextType.EXTENDED)
   private EntityManager entityManager;

   @Override
   protected EntityManager getEntityManager()
   {
      return entityManager;
   }

   @GET
   @Produces("application/xml")
   @SuppressWarnings("unchecked")
   public List<Project> list(@QueryParam("limit") @DefaultValue("10") final int limit,
            @QueryParam("offset") final int offset)
   {
      Query query = entityManager.createNamedQuery("project.list", Project.class);
      query.setFirstResult(offset);
      query.setMaxResults(limit);
      List<Project> result = query.getResultList();
      return result;
   }

   @GET
   @Path("/count")
   @Produces("text/plain")
   public int getProjectCount()
   {
      return ((Long) findUniqueByNamedQuery("project.count")).intValue();
   }

   @GET
   @Path("/named/{name}")
   @Produces("application/xml")
   public Project getByName(@PathParam("name") final String name)
   {
      return findUniqueByNamedQuery("project.bySlug", Strings.canonicalize(name));
   }

   @PUT
   @Path("/{pid}")
   @Consumes("application/xml")
   public void saveProjectDetails(@PathParam("pid") final long projectId, final Project project)
   {
      Project p = findById(Project.class, projectId);
      p.setVision(project.getVision());
      save(p);
   }

   @POST
   @Path("/{pid}/invite")
   public void inviteMember(@PathParam("pid") final long projectId, @QueryParam("uid") final long userId)
   {
      Project project = findById(Project.class, projectId);
      Profile user = findById(Profile.class, userId);

      if (!project.getAllMembers().contains(user))
      {
         Membership membership = new Membership(project, user, MemberRole.INVITED);
         project.getMemberships().add(membership);
      }
      save(project);
   }

   @POST
   @Path("/{pid}/join")
   public void requestMembership(@PathParam("pid") final long projectId, @QueryParam("uid") final long userId)
   {
      Project project = findById(Project.class, projectId);
      Profile user = findById(Profile.class, userId);

      if (!project.getAllMembers().contains(user))
      {
         Membership membership = new Membership(project, user, MemberRole.REQUESTED);
         project.getMemberships().add(membership);
      }
      save(project);
   }

   @PUT
   @Path("/{pid}/members/{uid}")
   @Consumes("application/xml")
   public void setMemberRole(@PathParam("pid") final long projectId, @PathParam("uid") final long userId,
            final MemberRole role)

   {
      Project project = findById(Project.class, projectId);
      Profile user = findById(Profile.class, userId);
      Membership member = project.getMembership(user);
      member.setRole(role);
      save(member);
   }

   @DELETE
   @Path("/{pid}/members/{uid}")
   public void leaveProject(@PathParam("pid") final long projectId, @PathParam("uid") final long userId)
   {
      Project project = findById(Project.class, projectId);
      Profile user = findById(Profile.class, userId);
      Membership member = project.getMembership(user);
      if (project.getOwners().size() >= 1)
      {
         removeTaskAssignments(project, user);
         project.getMemberships().remove(member);
         delete(member);
      }
      else
      {
         throw new DataException("Cannot remove last Owner of a Project");
      }
   }

   @SuppressWarnings("unchecked")
   private List<Task> removeTaskAssignments(final Project project, final Profile user)
   {
      Query query = entityManager.createQuery("FROM Task t WHERE t.story.project = :project");
      query.setParameter("project", project);
      List<Task> tasks = query.getResultList();

      query = entityManager.createQuery("UPDATE Task t SET t.assignee = null WHERE t.assignee = :user "
               + "AND t.status <> :status AND t IN (:list)");
      query.setParameter("user", user);
      query.setParameter("status", TaskStatus.DONE);
      query.setParameter("list", tasks);
      query.executeUpdate();

      query = entityManager.createQuery("FROM Task t WHERE t IN (:list)", Task.class);
      query.setParameter("list", tasks);

      List<Task> result = query.getResultList();
      return result;

   }

   @POST
   @Path("/{pid}/stories")
   @Consumes("application/xml")
   @Produces("text/plain")
   public int addStory(@PathParam("pid") final long projectId, final Story story)
   {
      Project project = findById(Project.class, projectId);

      Story s = new Story();

      for (ValidationCriteria v : story.getValidations())
      {
         v.setStory(s);
         s.getValidations().add(v);
      }

      project.getStories().add(s);
      s.setBurner(story.getBurner());
      s.setStatus(StoryStatus.OPEN);

      if (story.getStoryPoints() == null)
      {
         s.setStoryPoints(Points.NOT_POINTED);
      }
      else
      {
         s.setStoryPoints(story.getStoryPoints());
      }

      if (story.getBusinessValue() == null)
      {
         s.setBusinessValue(Points.NOT_POINTED);
      }
      else
      {
         s.setBusinessValue(story.getBusinessValue());
      }

      // s.getIIterationHistory().add(project.getDefaultIteration());
      create(s);
      is.changeStoryIteration(project.getIteration(story.getIteration().getTitle()), s);
      refresh(s);

      is.updateIterationStatistics(s.getIteration());
      return s.getNumber();
   }

   @GET
   @Path("/{pid}/stories")
   @Produces("application/xml")
   public List<Story> getStories(@PathParam("pid") final long projectId)
   {
      return findByNamedQuery("Story.byProjectId", projectId);
   }

   @GET
   @Path("/{pid}/features/{fid}")
   @Produces("application/xml")
   public List<Story> getStoriesByFeature(@PathParam("pid") final long projectId,
            @PathParam("fid") final long featureId)
   {
      return findByNamedQuery("Story.byProjectAndFeatureId", projectId, featureId);
   }

   @GET
   @Path("/{pid}/iterations/{iid}/backburner")
   @Produces("application/xml")
   public List<Story> getBackBurnerStories(@PathParam("pid") final long projectId,
            @PathParam("iid") final long iterationId)
   {
      Iteration iteration = findById(Iteration.class, iterationId);
      return iteration.getBackShelfStories();
   }

   @GET
   @Path("/{pid}/iterations/{iid}/frontburner")
   @Produces("application/xml")
   public List<Story> getFrontBurnerStories(@PathParam("pid") final long projectId,
            @PathParam("iid") final long iterationId)
   {
      Iteration iteration = findById(Iteration.class, iterationId);
      return iteration.getFrontShelfStories();
   }

   @POST
   @Path("/{pid}/features")
   @Produces("text/plain")
   @Consumes("application/xml")
   public long addFeature(@PathParam("pid") final long projectId, final Feature feature)
   {
      Project project = findById(Project.class, projectId);
      project.getFeatures().add(feature);
      feature.setProject(project);
      create(feature);
      return feature.getId();
   }

   @DELETE
   @Path("/{pid}/features/{fid}")
   public void removeFeature(@PathParam("pid") final long projectId, @PathParam("fid") final long featureId)
   {
      Feature feature = findById(Feature.class, featureId);
      if (feature.getProject().getId() == projectId)
      {
         feature.getProject().getFeatures().remove(feature);
         delete(feature);
      }
   }

   @PUT
   @Path("/{pid}/features/{fid}")
   @Consumes("application/xml")
   public void saveFeatureDetails(@PathParam("pid") final long projectId, @PathParam("fid") final long featureId,
            final Feature feature)
   {
      Feature f = findById(Feature.class, featureId);
      f.setName(feature.getName());
      save(f);
   }

   @PUT
   @Produces("text/plain")
   @Path("/{pid}/features/{fid}/merge")
   public int mergeFeatures(@PathParam("pid") final long projectId, @PathParam("fid") final long featureFromId,
            @QueryParam("toFeature") final long featureToId)
   {
      final Feature from = findById(Feature.class, featureFromId);
      final Feature to = findById(Feature.class, featureToId);

      assert from.getProject().getId() == projectId;
      assert to.getProject().getId() == projectId;

      Query query = entityManager.createQuery("update Story set feature = :newFeature where feature = :oldFeature");
      query.setParameter("newFeature", to);
      query.setParameter("oldFeature", from);
      int updated = query.executeUpdate();

      from.getProject().getFeatures().remove(from);
      delete(from);

      return updated;
   }

   @POST
   @Path("/{pid}/iterations")
   @Consumes("application/xml")
   public void addIteration(@PathParam("pid") final long projectId, final Iteration iteration)
   {
      Project project = findById(Project.class, projectId);
      project.getIterations().add(iteration);
      iteration.setProject(project);
      save(project);
   }

   @PUT
   @Path("/{pid}/iterations/{iid}/commit")
   public void commitIteration(@PathParam("pid") final long projectId, @PathParam("iid") final long iterationId)
   {
      Iteration iteration = findById(Iteration.class, iterationId);
      is.commit(iteration);
   }

   @DELETE
   @Path("/{pid}/iterations/{iid}")
   public void removeIteration(@PathParam("pid") final long projectId, @PathParam("iid") final long iterationId)
   {
      Iteration iteration = findById(Iteration.class, iterationId);
      is.removeIteration(iteration);
   }

   @POST
   @Path("/{pid}/milestones")
   @Consumes("application/xml")
   public void addMilestone(@PathParam("pid") final long projectId, final Milestone milestone)
   {
      Project project = findById(Project.class, projectId);

      Milestone m = new Milestone();
      m.setName(milestone.getName());
      m.setDescription(milestone.getDescription());
      m.setTargetDate(milestone.getTargetDate());
      m.setProject(project);

      project.getMilestones().add(m);

      save(project);
   }

   @PUT
   @Path("/{pid}/milestones")
   @Consumes("application/xml")
   public void saveMilestone(@PathParam("pid") final long projectId, final Milestone milestone)
   {
      Milestone m = findById(Milestone.class, milestone.getId());

      m.setTargetDate(milestone.getTargetDate());
      m.setDescription(milestone.getDescription());
      m.setName(milestone.getName());

      save(m);
   }

   @DELETE
   @Path("/{pid}/milestones/{mid}")
   public void removeMilestone(@PathParam("pid") final long projectId, @PathParam("mid") final long milestoneId)
   {
      Milestone milestone = findById(Milestone.class, milestoneId);

      for (Story s : milestone.getStories())
      {
         s.setMilestone(null);
         milestone.getStories().remove(s);
      }

      milestone.getProject().getMilestones().remove(milestone);
      milestone.setProject(null);

      deleteById(Milestone.class, milestoneId);
   }
}
