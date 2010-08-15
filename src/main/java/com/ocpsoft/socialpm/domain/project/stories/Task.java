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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.ocpsoft.socialpm.domain.PersistentObject;
import com.ocpsoft.socialpm.domain.user.User;
import com.ocpsoft.socialpm.util.Dates;

@Entity
@Table(name = "tasks")
public class Task extends PersistentObject<Task>
{
   private static final long serialVersionUID = -4511905664407432745L;

   public static final String UNASSIGNED = "---";

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(nullable = false, updatable = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @Index(name = "taskStoryIndex")
   private Story story;

   @OneToOne(fetch = FetchType.EAGER)
   @JoinColumn
   @Index(name = "taskAssigneeIndex")
   private User assignee;

   @Column(length = 255)
   private String text;

   @Column(nullable = false)
   @Enumerated(EnumType.ORDINAL)
   private TaskStatus status;

   @Column(updatable = false, nullable = false)
   private int initialHours;

   @OneToMany(fetch = FetchType.EAGER, mappedBy = "task", orphanRemoval = true)
   @Cascade({ CascadeType.ALL })
   private Set<TaskHours> hours = new HashSet<TaskHours>();

   public Task()
   {
   }

   public Task(final String description, final int hoursRemain, final TaskStatus status)
   {
      this.text = description;
      this.status = status;
      setHoursRemain(hoursRemain);
   }

   public boolean isDone()
   {
      return TaskStatus.DONE.equals(status);
   }

   public void close()
   {
      setStatus(TaskStatus.DONE);
      setHoursRemain(0);
   }

   public void reopen()
   {
      setStatus(TaskStatus.IN_PROGRESS);
   }

   public void setHoursRemain(final int hoursRemain)
   {
      TaskHours currentHours = getCurrentHours();
      Date today = new Date(System.currentTimeMillis());
      if ((currentHours == null) || !Dates.isSameDay(today, currentHours.getDate()))
      {
         currentHours = new TaskHours();
         currentHours.setDate(today);
         currentHours.setTask(this);
         hours.add(currentHours);
      }
      currentHours.setHoursRemain(hoursRemain);
   }

   private TaskHours getCurrentHours()
   {
      TaskHours result = null;
      for (TaskHours current : hours)
      {
         if (result == null)
         {
            result = current;
         }
         else if (current.getDate().after(result.getDate()))
         {
            result = current;
         }
      }
      return result;
   }

   public int getHoursRemain()
   {
      if (getCurrentHours() != null)
      {
         return getCurrentHours().getHoursRemain();
      }
      return 0;
   }

   public Story getStory()
   {
      return story;
   }

   public void setStory(final Story story)
   {
      this.story = story;
   }

   public String getText()
   {
      return text;
   }

   public void setText(final String description)
   {
      this.text = description;
   }

   public TaskStatus getStatus()
   {
      return status;
   }

   public void setStatus(final TaskStatus status)
   {
      this.status = status;
   }

   public User getAssignee()
   {
      return assignee;
   }

   public void setAssignee(final User assignee)
   {
      this.assignee = assignee;
   }

   public int getInitialHours()
   {
      return initialHours;
   }

   public void setInitialHours(final int hoursPlanned)
   {
      setHoursRemain(hoursPlanned);
      initialHours = hoursPlanned;
   }

   public void setHours(final Set<TaskHours> hours)
   {
      this.hours = hours;
   }

   public Set<TaskHours> getHours()
   {
      return hours;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      long result = getId() + 1;
      result = prime * result + ((story == null) ? 0 : story.hashCode());
      result = prime * result + ((text == null) ? 0 : text.hashCode());
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
      if (!(obj instanceof Task))
      {
         return false;
      }
      Task other = (Task) obj;
      if (getId() != other.getId())
      {
         return false;
      }
      if (text == null)
      {
         if (other.text != null)
         {
            return false;
         }
      }
      else if (!text.equals(other.text))
      {
         return false;
      }
      if (story == null)
      {
         if (other.story != null)
         {
            return false;
         }
      }
      else if (!story.equals(other.story))
      {
         return false;
      }
      return true;
   }

   @Override
   public String toString()
   {
      return "Task [assignee=" + assignee + ", hours=" + hours + ", initialHours=" + initialHours + ", status=" + status + ", story=" + story + ", text=" + text + ", getCreatedOn()=" + getCreatedOn() + ", getId()=" + getId() + ", getLastUpdate()=" + getLastUpdate() + ", getVersion()=" + getVersion() + "]";
   }
}
