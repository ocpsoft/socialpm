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

import java.util.Date;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

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
import com.ocpsoft.socialpm.domain.user.User;

@Stateful
public class StoryService extends PersistenceUtil
{
   private static final long serialVersionUID = -7326281066237817776L;

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
   private ChangeStoryPriority changePriority;

   public void saveStoryDetails(final Story story)
   {
      if (StoryStatus.CLOSED.equals(story.getStatus()))
      {
         for (Task t : story.getTasks())
         {
            if (!TaskStatus.DONE.equals(t.getStatus()))
            {
               t.close();
            }
         }
      }

      this.save(story);
      is.updateIterationStatistics(story.getIteration());
   }

   public void changeIteration(final Story story, final Iteration iteration)
   {
      is.changeStoryIteration(iteration, story);
   }

   public Story addComment(final Story story, final StoryComment comment)
   {
      story.getComments().add(comment);
      this.create(comment);
      return story;
   }

   public void removeComment(final StoryComment comment)
   {
      comment.getStory().getComments().remove(comment);
      comment.setStory(null);
      save(comment.getStory());
   }

   public Story addTask(final Story story, final Task task)
   {
      task.setStatus(TaskStatus.NOT_STARTED);
      task.setInitialHours(task.getInitialHours());

      task.setStory(story);
      story.getTasks().add(task);

      this.create(task);
      is.updateIterationStatistics(story.getIteration());
      return story;
   }

   public void deleteTask(final Task task)
   {
      Story story = task.getStory();
      story.getTasks().remove(task);
      task.setStory(null);
      this.save(story);
      is.updateIterationStatistics(story.getIteration());
   }

   public void saveTask(final Task task)
   {
      if (TaskStatus.DONE.equals(task.getStatus()))
      {
         task.close();
      }

      User assignee = task.getAssignee();
      if ((assignee != null) && task.getStory().getProject().hasActiveMember(assignee))
      {
         throw new NoSuchObjectException("Assignee was not found in project");
      }

      this.save(task);
      is.updateIterationStatistics(task.getStory().getIteration());
   }

   public void addValidation(final Story story, final ValidationCriteria validation)
   {
      validation.setStory(story);
      story.getValidations().add(validation);
      this.save(story);
   }

   public void saveValidationDescription(final ValidationCriteria v)
   {
      this.save(v);
   }

   public void removeValidation(final ValidationCriteria validation)
   {
      validation.getStory().getValidations().remove(validation);
      this.delete(validation);
   }

   public void acceptValidation(final ValidationCriteria validation, final User user)
   {
      validation.setAccepted(true);
      validation.setAcceptedBy(user);
      validation.setAcceptedOn(new Date());
      this.save(validation);
   }

   public void rejectValidation(final ValidationCriteria validation)
   {
      validation.setAccepted(false);
      validation.setAcceptedBy(null);
      validation.setAcceptedOn(null);
      this.save(validation);
   }

   public void changePriority(final long storyId, final Integer newPriority)
   {
      Story story = this.findById(Story.class, storyId);
      changePriority.execute(story, newPriority);
   }

   public void setStoryMilestone(final Story story, final Milestone milestone)
   {
      story.setMilestone(milestone);
      milestone.getStories().add(story);

      this.save(milestone);
   }

   public void removeStoryMilestone(final Story story)
   {
      Milestone milestone = story.getMilestone();
      if (milestone != null)
      {
         milestone.getStories().remove(story);
         story.setMilestone(null);
         this.save(milestone);
      }
   }
}
