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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "task_hours", uniqueConstraints = { @UniqueConstraint(columnNames = { "task_id", "date" }) })
public class TaskHours
{
   private static final long serialVersionUID = -7476573232041610503L;

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private long id = 0;

   @Version
   @Column(name = "version")
   private int version = 0;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(nullable = false, updatable = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @Index(name = "taskHoursTaskIndex")
   private Task task;

   @Temporal(TemporalType.DATE)
   private Date date;

   private int hoursRemain;

   public Date getDate()
   {
      return date;
   }

   public void setDate(final Date date)
   {
      this.date = date;
   }

   public int getHoursRemain()
   {
      return hoursRemain;
   }

   public void setHoursRemain(final int hoursRemain)
   {
      this.hoursRemain = hoursRemain;
   }

   @Override
   public String toString()
   {
      return "(" + date + "," + hoursRemain + ")";
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((date == null) ? 0 : date.hashCode());
      return result;
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
      if (!(obj instanceof TaskHours))
      {
         return false;
      }
      TaskHours other = (TaskHours) obj;
      if (date == null)
      {
         if (other.date != null)
         {
            return false;
         }
      }
      else if (!date.equals(other.date))
      {
         return false;
      }
      return true;
   }

   public Task getTask()
   {
      return task;
   }

   public void setTask(final Task task)
   {
      this.task = task;
   }

   public long getId()
   {
      return id;
   }

   public void setId(final long id)
   {
      this.id = id;
   }

   public int getVersion()
   {
      return version;
   }

   public void setVersion(final int version)
   {
      this.version = version;
   }
}
