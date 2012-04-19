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

package com.ocpsoft.socialpm.model.project;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Index;
import org.jboss.errai.common.client.api.annotations.Portable;

import com.ocpsoft.socialpm.model.PersistentObject;
import com.ocpsoft.socialpm.model.project.story.Story;

@Portable
@Entity
@Table(name = "milestones", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "targetDate", "project_id" }) })
public class Milestone extends PersistentObject<Milestone>
{
   private static final long serialVersionUID = -2552532402109367367L;

   @Pattern(regexp = "[a-zA-Z0-9 _-]+")
   @Column(nullable = false, length = 32)
   private String name;

   @Column(length = 2048)
   private String description;

   @Column(nullable = false)
   @Temporal(TemporalType.DATE)
   private Date targetDate;

   @OneToMany(fetch = FetchType.LAZY, mappedBy = "milestone")
   private Set<Story> stories = new HashSet<Story>();

   @ManyToOne
   @JoinColumn(nullable = false, updatable = false)
   @Index(name = "milestoneProjectIndex")
   private Project project;

   public Milestone()
   {
   }

   public Milestone(final String name, final Date targetDate)
   {
      this.name = name;
      this.targetDate = targetDate;
   }

   public boolean isUpcoming()
   {
      return new Date().before(targetDate);
   }

   public String getName()
   {
      return name;
   }

   public void setName(final String name)
   {
      this.name = name;
   }

   public Date getTargetDate()
   {
      return targetDate;
   }

   public void setTargetDate(final Date date)
   {
      targetDate = date;
   }

   /**
    * Adds the specified element to this set and returns true if it is not already present If this set already contains
    * the element, the call leaves the set unchanged and returns false.
    */
   public boolean addStory(final Story story)
   {
      return stories.add(story);
   }

   public Set<Story> getStories()
   {
      return stories;
   }

   public void setStories(final Set<Story> stories)
   {
      this.stories = stories;
   }

   public Project getProject()
   {
      return project;
   }

   public void setProject(final Project project)
   {
      this.project = project;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + ((targetDate == null) ? 0 : targetDate.hashCode());
      result = prime * result + ((name == null) ? 0 : name.hashCode());
      result = prime * result + ((project == null) ? 0 : project.hashCode());
      return result;
   }

   @Override
   public boolean equals(final Object obj)
   {
      if (this == obj)
      {
         return true;
      }
      if (!super.equals(obj))
      {
         return false;
      }
      if (!(obj instanceof Milestone))
      {
         return false;
      }
      Milestone other = (Milestone) obj;
      if (targetDate == null)
      {
         if (other.targetDate != null)
         {
            return false;
         }
      }
      else if (!targetDate.equals(other.targetDate))
      {
         return false;
      }
      if (name == null)
      {
         if (other.name != null)
         {
            return false;
         }
      }
      else if (!name.equals(other.name))
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

   public String getDescription()
   {
      return description;
   }

   public void setDescription(final String description)
   {
      this.description = description;
   }
}
