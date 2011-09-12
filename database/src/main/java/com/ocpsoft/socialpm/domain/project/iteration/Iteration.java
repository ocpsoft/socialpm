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

package com.ocpsoft.socialpm.domain.project.iteration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Formula;

import com.ocpsoft.socialpm.domain.NoSuchObjectException;
import com.ocpsoft.socialpm.domain.PersistentObject;
import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.domain.project.stories.Story;
import com.ocpsoft.socialpm.domain.project.stories.StoryBurner;
import com.ocpsoft.socialpm.model.project.iteration.StatsCalculator;
import com.ocpsoft.socialpm.util.Dates;

@Entity
@Table(name = "iterations")
public class Iteration extends PersistentObject<Iteration>
{
   private static final long serialVersionUID = -5063597697743935212L;

   @ManyToOne
   @JoinColumn(nullable = false, updatable = false)
   private Project project;

   @Formula("(SELECT count(i.id) + 1 FROM iterations i WHERE i.project_id = project_id AND i.id < id)")
   private int number;

   @Temporal(TemporalType.DATE)
   private Date startDate;

   @Temporal(TemporalType.DATE)
   private Date endDate;

   @Temporal(TemporalType.TIMESTAMP)
   private Date committedOn;

   @Column(length = 2048)
   private String goals;

   @Pattern(regexp = "[a-zA-Z0-9 _-]+")
   @Column(length = 32)
   private String title;

   @OneToMany(mappedBy = "iteration", fetch = FetchType.LAZY)
   private final Set<Story> stories = new HashSet<Story>();

   @OneToMany(fetch = FetchType.LAZY, mappedBy = "iteration", cascade = { CascadeType.ALL }, orphanRemoval = true)
   private Set<IterationStatistics> statistics = new HashSet<IterationStatistics>();

   public Iteration()
   {
   }

   @Override
   public String toString()
   {
      return "Iteration [committedOn=" + committedOn + ", endDate=" + endDate + ", goals=" + goals + ", number=" + number + ", project=" + project + ", startDate=" + startDate + ", statistics=" + statistics + ", title=" + title + "]";
   }

   public Iteration(final String title, final Date startDate, final Date endDate)
   {
      super();

      this.title = title;
      this.startDate = startDate;
      this.endDate = endDate;
   }

   public IterationStatistics getCommitmentStats() throws IllegalStateException
   {
      if (!isDefault())
      {
         for (IterationStatistics stat : statistics)
         {
            if (stat.isCommitmentStats())
            {
               return stat;
            }
         }
      }

      throw new IllegalStateException("The iteration does not have a commitment, or commitment stats");
   }

   public void updateCommitmentStats() throws IllegalStateException
   {
      if (this.isDefault())
      {
         throw new IllegalStateException("The default iteration does not have a commitment, or commitment stats");
      }

      IterationStatistics stats;
      try
      {
         stats = getStatistics(null);
         new StatsCalculator().update(this, stats);
      }
      catch (NoSuchObjectException e)
      {
         stats = new StatsCalculator().calculate(this);
         stats.setIteration(this);
         statistics.add(stats);
      }
   }

   public IterationStatistics getStatistics(final Date date) throws NoSuchObjectException
   {
      for (IterationStatistics stat : statistics)
      {
         if ((date == null) && stat.isCommitmentStats())
         {
            return stat;
         }
         else if ((date != null) && !stat.isCommitmentStats() && Dates.isSameDay(date, stat.getDate()))
         {
            return stat;
         }
      }

      throw new NoSuchObjectException("No stats found for date: " + date);
   }

   public boolean isCommitted()
   {
      if (committedOn != null)
      {
         return true;
      }
      return false;
   }

   public boolean isDefault()
   {
      return isPersistent() && (startDate == null) && (endDate == null);
   }

   public List<Story> getBackShelfStories()
   {
      return getStoriesByShelf(StoryBurner.BACK);
   }

   public List<Story> getFrontShelfStories()
   {
      return getStoriesByShelf(StoryBurner.FRONT);
   }

   private List<Story> getStoriesByShelf(final StoryBurner shelf)
   {
      List<Story> result = new ArrayList<Story>();
      for (Story s : stories)
      {
         if (shelf.equals(s.getBurner()))
         {
            result.add(s);
         }
      }
      return result;
   }

   public boolean isEnded()
   {
      if (!isDefault())
      {
         return Dates.isDateInPast(endDate);
      }
      return false;
   }

   public boolean isInProgress()
   {
      boolean result = false;
      Date now = Dates.now();

      if (isDefault())
      {
         result = false;
      }
      else if (Dates.isInPrecisionRange(startDate, endDate, now, Calendar.DATE))
      {
         result = true;
      }
      return result;
   }

   public boolean isUpcoming()
   {
      boolean result = false;
      if (isDefault())
      {
         result = false;
      }
      else if (!Dates.isSameDay(new Date(), startDate) && new Date().before(startDate))
      {
         result = true;
      }
      return result;
   }

   public Project getProject()
   {
      return project;
   }

   public void setProject(final Project project)
   {
      this.project = project;
   }

   public Date getStartDate()
   {
      return startDate;
   }

   public void setStartDate(final Date startDate)
   {
      this.startDate = startDate;
   }

   public Date getEndDate()
   {
      return endDate;
   }

   public void setEndDate(final Date endDate)
   {
      this.endDate = endDate;
   }

   public Date getCommittedOn()
   {
      return committedOn;
   }

   public void setCommittedOn(final Date committedOn)
   {
      this.committedOn = committedOn;
   }

   public void setGoals(final String goals)
   {
      this.goals = goals;
   }

   public String getGoals()
   {
      return goals;
   }

   public String getTitle()
   {
      return title;
   }

   public void setTitle(final String title)
   {
      this.title = title;
   }

   public Set<Story> getStories()
   {
      return stories;
   }

   public Set<IterationStatistics> getStatistics()
   {
      return statistics;
   }

   public void setStatistics(final Set<IterationStatistics> statistics)
   {
      this.statistics = statistics;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((project == null) ? 0 : project.hashCode());
      result = prime * result + ((title == null) ? 0 : title.hashCode());
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
      if (getClass() != obj.getClass())
      {
         return false;
      }
      Iteration other = (Iteration) obj;
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
      if (title == null)
      {
         if (other.title != null)
         {
            return false;
         }
      }
      else if (!title.equals(other.title))
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

}
