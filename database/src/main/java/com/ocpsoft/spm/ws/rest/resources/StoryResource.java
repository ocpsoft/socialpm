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

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.ocpsoft.socialpm.domain.NoSuchObjectException;
import com.ocpsoft.socialpm.domain.PersistenceUtil;
import com.ocpsoft.socialpm.domain.project.Milestone;
import com.ocpsoft.socialpm.domain.project.iteration.Iteration;
import com.ocpsoft.socialpm.domain.project.stories.Story;
import com.ocpsoft.socialpm.domain.project.stories.StoryComment;
import com.ocpsoft.socialpm.domain.project.stories.StoryStatus;
import com.ocpsoft.socialpm.domain.project.stories.Task;
import com.ocpsoft.socialpm.domain.project.stories.TaskStatus;
import com.ocpsoft.socialpm.domain.project.stories.ValidationCriteria;
import com.ocpsoft.socialpm.domain.user.Profile;

@Path("/stories")
public class StoryResource extends PersistenceUtil
{
   private static final long serialVersionUID = -660622418750643864L;

   private IterationService is;

   @PersistenceContext(type = PersistenceContextType.EXTENDED)
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   @PUT
   @Path("/{sid}")
   @Consumes("application/xml")
   public void saveStoryDetails(@PathParam("sid") final long storyId, final Story story)
   {
      Story s = findById(Story.class, storyId);
      s.setBurner(story.getBurner());
      s.setFeature(s.getProject().getFeature(story.getFeature().getName()));
      s.setStoryPoints(story.getStoryPoints());
      s.setBusinessValue(story.getBusinessValue());
      s.setText(story.getText());
      if (StoryStatus.CLOSED.equals(story.getStatus()) && (story.getStatus() != s.getStatus()))
      {
         for (Task t : s.getTasks())
         {
            if (!TaskStatus.DONE.equals(t.getStatus()))
            {
               t.close();
            }
         }
      }

      s.setStatus(story.getStatus());
      save(s);
      is.updateIterationStatistics(s.getIteration());
   }

   @PUT
   @Path("/{sid}/iteration")
   @Consumes("text/plain")
   public void changeIteration(@PathParam("sid") final long storyId, final long iterationId)
   {
      Story story = findById(Story.class, storyId);
      Iteration iter = findById(Iteration.class, iterationId);
      is.changeStoryIteration(iter, story);
      return;
   }

   @POST
   @Path("/{id}/comments")
   @Consumes("application/xml")
   @Produces("application/xml")
   public Story addComment(@PathParam("id") final long storyId, final StoryComment comment)
   {
      Story story = findById(Story.class, storyId);
      Profile u = findById(Profile.class, comment.getAuthor().getId());

      StoryComment c = new StoryComment();
      c.setAuthor(u);
      c.setText(comment.getText());
      c.setStory(story);
      story.getComments().add(c);
      create(c);
      return story;
   }

   @DELETE
   @Path("/{sid}/comments/{cid}")
   public void removeComment(@PathParam("sid") final long storyId, @PathParam("cid") final long commentId)
   {
      StoryComment comment = findById(StoryComment.class, commentId);
      comment.getStory().getComments().remove(comment);
      delete(comment);
   }

   @POST
   @Path("/{id}/tasks")
   @Consumes("application/xml")
   @Produces("application/xml")
   public Story addTask(@PathParam("id") final long storyId, final Task task)
   {
      Story story = findById(Story.class, storyId);
      task.setStory(story);
      task.setStatus(TaskStatus.NOT_STARTED);
      task.setInitialHours(task.getInitialHours());

      create(task);
      is.updateIterationStatistics(story.getIteration());
      return story;
   }

   @DELETE
   @Path("/{sid}/tasks/{tid}")
   public void deleteTask(@PathParam("sid") final long storyId, @PathParam("tid") final long taskId)
   {
      Task task = findById(Task.class, taskId);
      task.getStory().getTasks().remove(task);
      delete(task);
      is.updateIterationStatistics(task.getStory().getIteration());
   }

   @PUT
   @Path("/{sid}/tasks/{tid}")
   @Consumes("application/xml")
   @Produces("application/xml")
   public Task saveTask(@PathParam("sid") final long storyId, @PathParam("tid") final long taskId, final Task task)
   {
      Task t = findById(Task.class, taskId);

      t.setText(task.getText());

      if (TaskStatus.DONE.equals(task.getStatus()) && !t.getStatus().equals(task.getStatus()))
      {
         t.close();
      }
      else if (t.getHoursRemain() != task.getHoursRemain())
      {
         t.setHoursRemain(task.getHoursRemain());
         t.setStatus(task.getStatus());
      }
      else
      {
         t.setStatus(task.getStatus());
      }

      if ((task.getAssignee() != null) && !task.getAssignee().equals(t.getAssignee()))
      {
         Profile u = findById(Profile.class, task.getAssignee().getId());
         if (t.getStory().getProject().hasActiveMember(u))
         {
            t.setAssignee(u);
         }
         else
         {
            throw new NoSuchObjectException("Assignee was not found in project");
         }
      }
      else if ((task.getAssignee() == null))
      {
         t.setAssignee(null);
      }
      save(t);
      is.updateIterationStatistics(t.getStory().getIteration());
      return t;
   }

   @POST
   @Path("/{sid}/validations")
   @Consumes("application/xml")
   @Produces("application/xml")
   public Story addValidation(@PathParam("sid") final long storyId, final ValidationCriteria validation)
   {
      Story story = findById(Story.class, storyId);
      validation.setStory(story);
      story.getValidations().add(validation);
      save(story);
      return story;
   }

   @PUT
   @Path("/{sid}/validations/{vid}")
   @Consumes("text/plain")
   public void saveValidationDescription(@PathParam("sid") final long storyId,
            @PathParam("vid") final long validationId, final String text)
   {
      Story story = findById(Story.class, storyId);
      for (ValidationCriteria v : story.getValidations())
      {
         if (v.getId() == validationId)
         {
            v.setText(text);
            save(v);
            return;
         }
      }
   }

   @DELETE
   @Path("/{sid}/validations/{vid}")
   public void removeValidation(@PathParam("sid") final long storyId, @PathParam("vid") final long validationId)
   {
      Story story = findById(Story.class, storyId);
      for (ValidationCriteria v : story.getValidations())
      {
         if (v.getId() == validationId)
         {
            story.getValidations().remove(v);
            delete(v);
            return;
         }
      }
   }

   @PUT
   @Produces("application/xml")
   @Path("/{sid}/validations/{vid}/accept")
   public ValidationCriteria acceptValidation(@PathParam("sid") final long storyId,
            @PathParam("vid") final long validationId, @QueryParam("user") final long userId)
   {
      Story story = findById(Story.class, storyId);
      Profile user = findById(Profile.class, userId);

      for (ValidationCriteria v : story.getValidations())
      {
         if (v.getId() == validationId)
         {
            v.setAccepted(true);
            v.setAcceptedBy(user);
            v.setAcceptedOn(new Date());
            save(v);
            return v;
         }
      }

      throw new NoSuchObjectException("No such validation criteria: storyId[" + storyId + "] validationId["
               + validationId + "]");
   }

   @PUT
   @Produces("application/xml")
   @Path("/{sid}/validations/{vid}/reject")
   public ValidationCriteria rejectValidation(@PathParam("sid") final long storyId,
            @PathParam("vid") final long validationId)
   {
      Story story = findById(Story.class, storyId);

      for (ValidationCriteria v : story.getValidations())
      {
         if (v.getId() == validationId)
         {
            v.setAccepted(false);
            v.setAcceptedOn(null);
            save(v);
            return v;
         }
      }

      throw new NoSuchObjectException("No such validation criteria: storyId[" + storyId + "] validationId["
               + validationId + "]");
   }

   @PUT
   @Path("/{sid}/priority")
   public void changePriority(@PathParam("sid") final long storyId, @QueryParam("p") final Integer newPriority)
   {
      Story story = findById(Story.class, storyId);
      new ChangePriorityCommand(story, newPriority).perform();
   }

   @PUT
   @Path("/{sid}/milestone")
   public void setStoryMilestone(@PathParam("sid") final long storyId, @QueryParam("m") final long milestoneId)
   {
      Story story = findById(Story.class, storyId);
      Milestone milestone = findById(Milestone.class, milestoneId);

      story.setMilestone(milestone);
      milestone.getStories().add(story);

      save(milestone);
   }

   @DELETE
   @Path("/{sid}/milestone")
   public void removeStoryMilestone(@PathParam("sid") final long storyId)
   {
      Story story = findById(Story.class, storyId);
      Milestone milestone = story.getMilestone();
      if (milestone != null)
      {
         milestone.getStories().remove(story);
         story.setMilestone(null);
         save(milestone);
      }
   }
}
