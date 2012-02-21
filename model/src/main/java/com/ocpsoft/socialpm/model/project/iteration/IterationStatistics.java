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

package com.ocpsoft.socialpm.model.project.iteration;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Index;
import org.jboss.errai.common.client.api.annotations.Portable;

import com.ocpsoft.socialpm.model.PersistentObject;

@Portable
@Entity
@Table(name = "iteration_statistics", uniqueConstraints = { @UniqueConstraint(columnNames = { "iteration_id", "date" }) })
public class IterationStatistics extends PersistentObject<IterationStatistics>
{
   private static final long serialVersionUID = -7224291074624921875L;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(nullable = false, updatable = false)
   @Index(name = "iterationStatsIterationIndex")
   private Iteration iteration;

   @Temporal(TemporalType.DATE)
   private Date date;

   /* remaining */
   private int hoursRemain;
   private int totalTasks;
   private int totalStories;
   private int totalPoints;
   private int totalValue;

   /* progress */
   private int completedTasks;
   private int completedStories;
   private int completedPoints;
   private int completedValue;

   @Override
   public String toString()
   {
      return "IterationStatistics [id=" + getId() + ", version=" + getVersion() + ", completedPoints="
               + completedPoints + ", completedStories=" + completedStories + ", completedTasks=" + completedTasks
               + ", completedValue=" + completedValue + ", date=" + date + ", storiesAdded=" + ", totalHours="
               + hoursRemain + ", totalPoints=" + totalPoints + ", totalStories=" + totalStories + ", totalTasks="
               + totalTasks + ", totalValue=" + totalValue + "]";
   }

   public boolean isCommitmentStats()
   {
      return date == null;
   }

   /* get & set */

   public Iteration getIteration()
   {
      return iteration;
   }

   public void setIteration(final Iteration iteration)
   {
      this.iteration = iteration;
   }

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

   public void setHoursRemain(final int totalHours)
   {
      this.hoursRemain = totalHours;
   }

   public int getTotalTasks()
   {
      return totalTasks;
   }

   public void setTotalTasks(final int totalTasks)
   {
      this.totalTasks = totalTasks;
   }

   public int getTotalStories()
   {
      return totalStories;
   }

   public void setTotalStories(final int totalStories)
   {
      this.totalStories = totalStories;
   }

   public int getTotalPoints()
   {
      return totalPoints;
   }

   public void setTotalPoints(final int totalPoints)
   {
      this.totalPoints = totalPoints;
   }

   public int getTotalValue()
   {
      return totalValue;
   }

   public void setTotalValue(final int totalValue)
   {
      this.totalValue = totalValue;
   }

   public int getCompletedTasks()
   {
      return completedTasks;
   }

   public void setCompletedTasks(final int completedTasks)
   {
      this.completedTasks = completedTasks;
   }

   public int getCompletedStories()
   {
      return completedStories;
   }

   public void setCompletedStories(final int completedStories)
   {
      this.completedStories = completedStories;
   }

   public int getCompletedPoints()
   {
      return completedPoints;
   }

   public void setCompletedPoints(final int completedPoints)
   {
      this.completedPoints = completedPoints;
   }

   public int getCompletedValue()
   {
      return completedValue;
   }

   public void setCompletedValue(final int completedValue)
   {
      this.completedValue = completedValue;
   }

}
