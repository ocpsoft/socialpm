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
import org.jboss.errai.common.client.api.annotations.Portable;

import com.ocpsoft.socialpm.model.PersistentObject;
import com.ocpsoft.socialpm.model.user.Profile;

@Portable
@Entity
@Table(name = "tasks")
public class Task extends PersistentObject<Task>
{
   private static final long serialVersionUID = -4511905664407432745L;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(nullable = false, updatable = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @Index(name = "taskStoryIndex")
   private Story story;

   @OneToOne(fetch = FetchType.EAGER)
   @JoinColumn
   @Index(name = "taskAssigneeIndex")
   private Profile assignee;

   @Column(length = 255)
   private String text;

   @Column(length = 255)
   private String impediments;

   @ManyToOne
   private Profile impedimentReporter;

   @Column(nullable = false)
   @Enumerated(EnumType.ORDINAL)
   private Status status = Status.NOT_STARTED;

   @Column(updatable = false, nullable = false)
   private int initialHours;

   @Cascade({ CascadeType.ALL })
   @OnDelete(action = OnDeleteAction.CASCADE)
   @OneToMany(fetch = FetchType.EAGER, mappedBy = "task")
   private Set<TaskHours> hours = new HashSet<TaskHours>();

   public Task()
   {}

   public Task(final String description, final int hoursRemain, final Status status)
   {
      this.text = description;
      this.status = status;
      setHoursRemain(hoursRemain);
   }

   public boolean isDone()
   {
      return Status.DONE.equals(status);
   }

   public void close()
   {
      setStatus(Status.DONE);
      setHoursRemain(0);
   }

   public void reopen()
   {
      setStatus(Status.IN_PROGRESS);
   }

   public void setHoursRemain(final int hoursRemain)
   {
      TaskHours currentHours = getCurrentHours();
      Date today = new Date(System.currentTimeMillis());
      if ((currentHours != null) && currentHours.getHoursRemain() == hoursRemain)
      {
         return;
      }
      else
      {
         currentHours = new TaskHours();
         currentHours.setDate(today);
         currentHours.setTask(this);
         hours.add(currentHours);
         currentHours.setHoursRemain(hoursRemain);
      }
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

   public Status getStatus()
   {
      return status;
   }

   public void setStatus(final Status status)
   {
      this.status = status;
   }

   public Profile getAssignee()
   {
      return assignee;
   }

   public void setAssignee(final Profile assignee)
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
      result = (prime * result) + ((story == null) ? 0 : story.hashCode());
      result = (prime * result) + ((text == null) ? 0 : text.hashCode());
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
      return "Task [assignee=" + assignee + ", hours=" + hours + ", initialHours=" + initialHours + ", status="
               + status + ", story=" + story + ", text=" + text + ", getCreatedOn()=" + getCreatedOn() + ", getId()="
               + getId() + ", getLastUpdate()=" + getLastUpdate() + ", getVersion()=" + getVersion() + "]";
   }

   public String getImpediments()
   {
      return impediments;
   }

   public void setImpediments(final String impediments)
   {
      this.impediments = impediments;
   }

   public boolean isImpeded()
   {
      return Status.IMPEDED.equals(getStatus());
   }

   public void clearImpediments()
   {
      setImpediments(null);
      setImpedimentReporter(null);
      if (getHoursRemain() == 0)
      {
         setStatus(Status.DONE);
      }
      else
      {
         setStatus(Status.IN_PROGRESS);
      }
   }

   public Profile getImpedimentReporter()
   {
      return impedimentReporter;
   }

   public void setImpedimentReporter(final Profile impedimentReporter)
   {
      this.impedimentReporter = impedimentReporter;
   }
}
