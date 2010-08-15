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

package com.ocpsoft.socialpm.domain.project.stories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.ocpsoft.socialpm.domain.NoSuchObjectException;
import com.ocpsoft.socialpm.domain.PersistentObject;
import com.ocpsoft.socialpm.domain.project.Feature;
import com.ocpsoft.socialpm.domain.project.Milestone;
import com.ocpsoft.socialpm.domain.project.Points;
import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.domain.project.iteration.Iteration;
import com.ocpsoft.socialpm.domain.user.User;

@Entity
@Table(name = "stories")
@NamedQueries({
         @NamedQuery(name = "Story.byProjectAndNumber", query = "from Story where project.id = ? and number = ?"),
         @NamedQuery(name = "Story.byProjectId", query = "from Story where project.id = ?"),
         @NamedQuery(name = "Story.byProjectAndFeatureId", query = "from Story where project.id = ? and feature.id = ?"),
         @NamedQuery(name = "Story.withTasksFor", query = "from Story s where s.id in (select t.story from Task t where t.assignee = ? and t.status != ?)"),
         @NamedQuery(name = "Story.byFeature", query = "from Story where project.name = ? and feature.name = ?") })
public class Story extends PersistentObject<Story>
{
   private static final long serialVersionUID = 719438791700341079L;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(nullable = false, updatable = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   private Project project;

   @Formula("(SELECT count(st.id) + 1 FROM stories st WHERE st.project_id = project_id AND st.id < id)")
   private int number;

   @Temporal(TemporalType.DATE)
   private Date closedOn;

   @ManyToOne
   private User closedBy;

   @Column
   private Integer priority;

   @ManyToOne(optional = false)
   private Iteration iteration;

   @ManyToOne
   @JoinColumn
   private Milestone milestone;

   @ManyToOne
   @JoinColumn(nullable = false)
   private Feature feature;

   @Column(nullable = false, length = 255)
   private String description;

   @Column(nullable = false)
   private Points storyPoints;

   @Column(nullable = false)
   private Points businessValue;

   @Column(nullable = false, length = 32)
   @Enumerated(EnumType.STRING)
   private StoryStatus status;

   @Column(nullable = false)
   @Enumerated(EnumType.ORDINAL)
   private StoryBurner burner;

   @Fetch(FetchMode.SUBSELECT)
   @OneToMany(fetch = FetchType.LAZY, mappedBy = "story", orphanRemoval = true, cascade = CascadeType.ALL)
   private List<ValidationCriteria> validations = new ArrayList<ValidationCriteria>();

   @Fetch(FetchMode.SUBSELECT)
   @OneToMany(fetch = FetchType.LAZY, mappedBy = "story", orphanRemoval = true, cascade = CascadeType.ALL)
   private List<Task> tasks = new ArrayList<Task>();

   @Fetch(FetchMode.SUBSELECT)
   @OneToMany(fetch = FetchType.LAZY, mappedBy = "story", orphanRemoval = true, cascade = CascadeType.ALL)
   private List<StoryComment> comments = new ArrayList<StoryComment>();

   @Override
   public String toString()
   {
      return "Story: " + getNumber() + ", id: " + getId();
   }

   public StoryBurner getBurner()
   {
      return burner;
   }

   public void setBurner(final StoryBurner burner)
   {
      this.burner = burner;
   }

   public boolean onFrontBurner()
   {
      return StoryBurner.FRONT.equals(burner);
   }

   public Project getProject()
   {
      return project;
   }

   public void setProject(final Project project)
   {
      this.project = project;
   }

   public Feature getFeature()
   {
      return feature;
   }

   public void setFeature(final Feature feature)
   {
      this.feature = feature;
   }

   public boolean getIsImpeded()
   {
      for (Task t : tasks)
      {
         if (TaskStatus.IMPEDED.equals(t.getStatus()))
         {
            return true;
         }
      }
      return false;
   }

   // TODO test this
   public void add(final Task task)
   {
      if (!(tasks instanceof Set<?>))
      {
         tasks = new ArrayList<Task>();
      }
      task.setStory(this);
      tasks.add(task);
   }

   public List<Task> getTasks()
   {
      return tasks;
   }

   public List<Task> getTaskList()
   {
      ArrayList<Task> taskList = new ArrayList<Task>();
      taskList.addAll(tasks);
      return taskList;
   }

   public void setTasks(final List<Task> tasks)
   {
      this.tasks = tasks;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription(final String description)
   {
      this.description = description;
   }

   public boolean isImpeded()
   {
      if (this.isOpen())
      {
         for (Task t : tasks)
         {
            if (TaskStatus.IMPEDED.equals(t.getStatus()))
            {
               return true;
            }
         }
      }
      return false;
   }

   public boolean isOpen()
   {
      return StoryStatus.OPEN.equals(status);
   }

   public boolean isStarted()
   {
      if (this.isOpen())
      {
         for (Task t : tasks)
         {
            if (false == TaskStatus.NOT_STARTED.equals(t.getStatus()))
            {
               return true;
            }
         }
      }
      return false;
   }

   public boolean isValidated()
   {
      for (ValidationCriteria v : validations)
      {
         if (!v.isAccepted())
         {
            return false;
         }
      }
      return !validations.isEmpty();
   }

   public StoryStatus getStatus()
   {
      return status;
   }

   public void setStatus(final StoryStatus status)
   {
      this.status = status;
   }

   public Points getStoryPoints()
   {
      return storyPoints;
   }

   public void setStoryPoints(final Points size)
   {
      this.storyPoints = size;
   }

   public Integer getPriority()
   {
      return priority;
   }

   public void setPriority(final Integer priority)
   {
      this.priority = priority;
   }

   public Milestone getMilestone()
   {
      return milestone;
   }

   public void setMilestone(final Milestone release)
   {
      this.milestone = release;
   }

   public StoryComment getStoryComment(final long commentId)
   {
      for (StoryComment comment : getComments())
      {
         if (commentId == comment.getId())
         {
            return comment;
         }
      }

      throw new NoSuchObjectException("Comment " + commentId + " does not exist for story: " + getId());
   }

   public List<StoryComment> getComments()
   {
      return comments;
   }

   public void setComments(final List<StoryComment> comments)
   {
      this.comments = comments;
   }

   public int getTotalTaskHoursRemaining()
   {
      int totalHoursRemaining = 0;

      for (Task task : tasks)
      {
         totalHoursRemaining += task.getHoursRemain();
      }

      return totalHoursRemaining;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      long result = getId() + 1;
      result = prime * result + ((project == null) ? 0 : project.hashCode());
      return (int) result;
   }

   @Override
   public boolean equals(final Object obj)
   {
      if (this == obj)
      {
         return true;
      }
      if (obj == null)
      {
         return false;
      }
      if (!(obj instanceof Story))
      {
         return false;
      }
      Story other = (Story) obj;
      if (getId() != other.getId())
      {
         return false;
      }
      if (project == null)
      {
         if (other.project != null)
         {
            return false;
         }
      }
      else if (!project.equals(other.project))
      {
         return false;
      }
      return true;
   }

   public int getNumber()
   {
      return number;
   }

   public void setNumber(final int number)
   {
      this.number = number;
   }

   public Iteration getIteration() throws NoSuchObjectException
   {
      return iteration;
   }

   public void setIteration(final Iteration iteration)
   {
      this.iteration = iteration;
   }

   public List<ValidationCriteria> getValidations()
   {
      return validations;
   }

   public void setValidations(final List<ValidationCriteria> validations)
   {
      this.validations = validations;
   }

   public Points getBusinessValue()
   {
      return businessValue;
   }

   public void setBusinessValue(final Points businessValue)
   {
      this.businessValue = businessValue;
   }

   public Date getClosedOn()
   {
      return closedOn;
   }

   public void setClosedOn(final Date closedOn)
   {
      this.closedOn = closedOn;
   }

   public User getClosedBy()
   {
      return closedBy;
   }

   public void setClosedBy(final User closedBy)
   {
      this.closedBy = closedBy;
   }
}