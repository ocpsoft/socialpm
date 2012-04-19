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

package com.ocpsoft.socialpm.model.project.story;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
import org.jboss.errai.common.client.api.annotations.Portable;

import com.ocpsoft.socialpm.model.DeletableObject;
import com.ocpsoft.socialpm.model.project.Feature;
import com.ocpsoft.socialpm.model.project.Milestone;
import com.ocpsoft.socialpm.model.project.Points;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.project.iteration.Iteration;
import com.ocpsoft.socialpm.model.user.Profile;

@Portable
@Entity
@Table(name = "stories")
@NamedQueries({
         @NamedQuery(name = "Story.byProjectAndNumber", query = "from Story where project = ? and number = ?"),
         @NamedQuery(name = "Story.byProjectId", query = "from Story where project.id = ?"),
         @NamedQuery(name = "Story.withTasksFor", query = "from Story s where s.id in (select t.story from Task t where t.assignee = ? and t.status != ?)")
})
public class Story extends DeletableObject<Story>
{
   private static final long serialVersionUID = 719438791700341079L;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(nullable = false, updatable = false)
   private Project project;

   @Formula("(SELECT count(st.id) + 1 FROM stories st WHERE st.project_id = project_id AND st.id < id)")
   private int number;

   @Temporal(TemporalType.DATE)
   private Date closedOn;

   @ManyToOne
   private Profile closedBy;

   @Column
   private Integer priority;

   @Column(nullable = false, length = 255)
   private String role;

   @Column(nullable = false, length = 255)
   private String objective;

   @Column(nullable = false, length = 255)
   private String result;

   @ManyToOne(optional = false)
   private Iteration iteration;

   @ManyToOne
   @JoinColumn
   private Milestone milestone;

   @ManyToMany(fetch = FetchType.LAZY)
   private Set<Feature> features = new HashSet<Feature>();

   @Column(nullable = false)
   private Points storyPoints = Points.NOT_POINTED;

   @Column(nullable = false)
   private Points businessValue = Points.NOT_POINTED;

   @Column(nullable = false)
   @Enumerated(EnumType.STRING)
   private StoryBurner burner = StoryBurner.FRONT;

   @Fetch(FetchMode.SUBSELECT)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @OneToMany(fetch = FetchType.LAZY, mappedBy = "story")
   private List<ValidationCriteria> validations = new ArrayList<ValidationCriteria>();

   @Fetch(FetchMode.SUBSELECT)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @OneToMany(fetch = FetchType.LAZY, mappedBy = "story")
   private List<Task> tasks = new ArrayList<Task>();

   @Fetch(FetchMode.SUBSELECT)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @OneToMany(fetch = FetchType.LAZY, mappedBy = "story")
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

   public boolean isFrontBurner()
   {
      return StoryBurner.FRONT.equals(burner);
   }

   public void setFrontBurner(final boolean value)
   {
      if (value)
      {
         burner = StoryBurner.FRONT;
      }
      else {
         burner = StoryBurner.BACK;
      }
   }

   public Project getProject()
   {
      return project;
   }

   public void setProject(final Project project)
   {
      this.project = project;
   }

   public Set<Feature> getFeatures()
   {
      return features;
   }

   public void setFeatures(final Set<Feature> feature)
   {
      this.features = feature;
   }

   public void add(final Task task)
   {
      task.setStory(this);
      tasks.add(task);
   }

   public int getTaskCount()
   {
      return tasks.size();
   }

   public List<Task> getTasks()
   {
      return tasks;
   }

   public List<Task> getImpededTasks()
   {
      List<Task> result = new ArrayList<Task>();
      for (Task task : tasks) {
         if (task.isImpeded())
         {
            result.add(task);
         }
      }
      return result;
   }

   public int getImpededTaskCount()
   {
      return getImpededTasks().size();
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

   public Status getProgressStatus()
   {
      Status result = Status.NOT_STARTED;
      for (Task t : tasks)
      {
         Status status = t.getStatus();
         if (status.isStrongerThan(result))
         {
            result = status;
         }
      }
      return result;
   }

   public boolean isImpeded()
   {
      return Status.IMPEDED == getProgressStatus();
   }

   public boolean isOpen()
   {
      boolean result = false;
      if ((getClosedBy() == null) && (getClosedOn() == null))
      {
         result = true;
      }
      return result;
   }

   public boolean isStarted()
   {
      return Status.IMPEDED != getProgressStatus();
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

   public StoryComment getStoryComment(final long commentId) throws IllegalArgumentException
   {
      for (StoryComment comment : getComments())
      {
         if (commentId == comment.getId())
         {
            return comment;
         }
      }

      throw new IllegalArgumentException("Comment " + commentId + " does not exist for story: " + getId());
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
      long result = (getId() == null ? 0 : getId()) + 1;
      result = (prime * result) + ((project == null) ? 0 : project.hashCode());
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

   public Iteration getIteration()
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

   public int getValidationCount()
   {
      return validations.size();
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

   public Profile getClosedBy()
   {
      return closedBy;
   }

   public void setClosedBy(final Profile closedBy)
   {
      this.closedBy = closedBy;
   }

   public String getRole()
   {
      return role;
   }

   public void setRole(final String role)
   {
      this.role = role;
   }

   public String getObjective()
   {
      return objective;
   }

   public void setObjective(final String objective)
   {
      this.objective = objective;
   }

   public String getResult()
   {
      return result;
   }

   public void setResult(final String result)
   {
      this.result = result;
   }
}