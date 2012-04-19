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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.jboss.errai.common.client.api.annotations.Portable;

import com.ocpsoft.socialpm.model.PersistentObject;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.project.story.Status;
import com.ocpsoft.socialpm.model.project.story.Story;
import com.ocpsoft.socialpm.model.project.story.StoryBurner;

@Portable
@Entity
@Table(name = "iterations")
@NamedQueries({
   @NamedQuery(name = "Iteration.byProjectAndNumber", query = "from Iteration where project = ? and number = ?"),
   @NamedQuery(name = "Iteration.byProjectId", query = "from Iteration where project.id = ?")
})
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

   @Temporal(TemporalType.TIMESTAMP)
   private Date closedOn;

   @Column(length = 2048)
   private String goals;

   @Pattern(regexp = "[a-zA-Z0-9 _-]+")
   @Column(length = 32)
   private String title;

   @OneToMany(mappedBy = "iteration", fetch = FetchType.LAZY)
   private final Set<Story> stories = new LinkedHashSet<Story>();

   @OnDelete(action = OnDeleteAction.CASCADE)
   @OneToMany(fetch = FetchType.EAGER, mappedBy = "iteration", cascade = { CascadeType.ALL })
   private Set<IterationStatistics> statistics = new LinkedHashSet<IterationStatistics>();

   public Iteration()
   {}

   @Override
   public String toString()
   {
      return getNumber() + " - " + getTitle();
   }

   public Iteration(final String title)
   {
      super();

      this.title = title;
   }

   @PrePersist
   @PreUpdate
   public void beforeCreate()
   {}

   public int getTaskHoursRemain()
   {
      IterationStatistics stats = getLatestStatistics();
      return stats.getHoursRemain();
   }

   public int getTaskHoursCommitment()
   {
      IterationStatistics stats = getCommitmentStatistics();
      if (stats == null)
      {
         return -1;
      }
      return stats.getHoursRemain();
   }

   public Status getStatus()
   {
      Status result = Status.NOT_STARTED;
      if (committedOn != null)
      {
         result = Status.IN_PROGRESS;
         if (closedOn != null)
         {
            result = Status.DONE;
         }
      }

      for (Story s : getStories()) {
         if (s.isImpeded())
         {
            result = Status.IMPEDED;
            break;
         }
      }
      return result;
   }

   public IterationStatistics getFirstStatistics()
   {
      IterationStatistics result = null;
      for (IterationStatistics s : statistics)
      {
         if (result == null || s.getDate().before(result.getDate()))
         {
            result = s;
         }
      }
      return result;
   }

   public IterationStatistics getLatestStatistics()
   {
      IterationStatistics result = null;
      for (IterationStatistics s : statistics)
      {
         if (result == null || result.getDate().before(s.getDate()))
         {
            result = s;
         }
      }
      return result;
   }

   /**
    * Return the {@link IterationStatistics} from {@link #getCommittedOn()} or null if this {@link Iteration} is not
    * committed.
    */
   public IterationStatistics getCommitmentStatistics()
   {
      IterationStatistics result = null;
      for (IterationStatistics s : statistics)
      {
         if (s.isCommitmentStats())
         {
            result = s;
            break;
         }
      }
      return result;
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
      return isPersistent() && (getNumber() == 1);
   }

   public List<Story> getBackShelfStories()
   {
      return getStoriesByShelf(StoryBurner.BACK);
   }

   public int getNumStories()
   {
      return getNumFrontShelfStories() + getNumBackShelfStories();
   }

   public int getNumOpenStories()
   {
      return getNumOpenFrontShelfStories() + getNumOpenBackShelfStories();
   }

   public int getNumBackShelfStories()
   {
      return getBackShelfStories().size();
   }

   public int getNumOpenBackShelfStories()
   {
      return getOpenBackShelfStories().size();
   }

   public List<Story> getOpenBackShelfStories()
   {
      return getOpenStoriesByShelf(StoryBurner.BACK);
   }

   public List<Story> getFrontShelfStories()
   {
      List<Story> result = getStoriesByShelf(StoryBurner.FRONT);

      Collections.sort(result, new Comparator<Story>() {
         @Override
         public int compare(final Story left, final Story right)
         {
            Integer l = left.getPriority();
            Integer r = right.getPriority();

            if ((l != null) && (r != null))
            {
               return l.compareTo(r);
            }
            if (r != null)
            {
               return 1;
            }
            return 0;
         }
      });

      Collections.sort(result, new Comparator<Story>() {
         @Override
         public int compare(final Story left, final Story right)
         {
            if (left.isOpen() && !right.isOpen())
               return 0;
            if (left.isOpen())
               return -1;
            if (right.isOpen())
               return 1;
            else
               return 0;
         }
      });

      return result;
   }

   public List<Story> getOpenFrontShelfStories()
   {
      return getOpenStoriesByShelf(StoryBurner.FRONT);
   }

   public int getNumFrontShelfStories()
   {
      return getFrontShelfStories().size();
   }

   public int getNumOpenFrontShelfStories()
   {
      return getOpenFrontShelfStories().size();
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

   private List<Story> getOpenStoriesByShelf(final StoryBurner shelf)
   {
      List<Story> result = new ArrayList<Story>();
      for (Story s : stories)
      {
         if (s.isOpen() && shelf.equals(s.getBurner()))
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
         return closedOn != null;
      }
      return false;
   }

   public boolean isInProgress()
   {
      boolean result = false;

      if (isDefault())
      {
         boolean defaultIsInProgress = true;
         Set<Iteration> iterations = project.getAvailableIterations();
         for (Iteration iteration : iterations) {
            if (!iteration.isDefault() && iteration.isInProgress())
            {
               defaultIsInProgress = false;
            }
         }
         result = defaultIsInProgress;
      }
      else if ((committedOn != null) && (closedOn == null))
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
      else if (committedOn == null)
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

   public List<Story> getStoryList()
   {
      return new ArrayList<Story>(getStories());
   }

   public Set<IterationStatistics> getStatistics()
   {
      return statistics;
   }

   public void setStatistics(final Set<IterationStatistics> statistics)
   {
      this.statistics = statistics;
   }

   public int getNumber()
   {
      return number;
   }

   public void setNumber(final int number)
   {
      this.number = number;
   }

   public Date getClosedOn()
   {
      return closedOn;
   }

   public void setClosedOn(final Date closedOn)
   {
      this.closedOn = closedOn;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = (prime * result) + number;
      result = (prime * result) + ((project == null) ? 0 : project.hashCode());
      return result;
   }

   @Override
   public boolean equals(final Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      Iteration other = (Iteration) obj;
      if (number != other.number)
         return false;
      if (project == null) {
         if (other.project != null)
            return false;
      }
      else if (!project.equals(other.project))
         return false;
      return true;
   }

}
