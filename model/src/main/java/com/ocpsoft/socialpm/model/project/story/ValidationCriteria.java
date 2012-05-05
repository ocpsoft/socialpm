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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.jboss.errai.common.client.api.annotations.Portable;

import com.ocpsoft.socialpm.model.PersistentObject;
import com.ocpsoft.socialpm.model.user.Profile;

@Portable
@Entity
@Table(name = "story_validations")
public class ValidationCriteria extends PersistentObject<ValidationCriteria>
{
   private static final long serialVersionUID = -3800665096974303384L;

   @XmlElement(name = "storyId")
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(nullable = false, updatable = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @Index(name = "vcStoryIndex")
   private Story story;

   @Column(length = 255)
   private String text;

   @OneToOne
   private Profile acceptedBy;

   @Temporal(TemporalType.TIMESTAMP)
   private Date acceptedOn;

   public ValidationCriteria()
   {}

   public ValidationCriteria(final String criteria)
   {
      this.text = criteria;
   }

   public void accept(final Profile profile)
   {
      setAcceptedBy(profile);
      setAcceptedOn(new Date());
   }

   public void reject()
   {
      acceptedBy = null;
      acceptedOn = null;
   }

   public String getText()
   {
      return text;
   }

   public void setText(final String validation)
   {
      this.text = validation;
   }

   public boolean isAccepted()
   {
      return acceptedBy != null;
   }

   public Profile getAcceptedBy()
   {
      return acceptedBy;
   }

   public void setAcceptedBy(final Profile acceptedBy)
   {
      this.acceptedBy = acceptedBy;
   }

   public Date getAcceptedOn()
   {
      return acceptedOn;
   }

   public void setAcceptedOn(final Date acceptedOn)
   {
      this.acceptedOn = acceptedOn;
   }

   public Story getStory()
   {
      return story;
   }

   public void setStory(final Story story)
   {
      this.story = story;
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
      if (getClass() != obj.getClass())
      {
         return false;
      }
      ValidationCriteria other = (ValidationCriteria) obj;
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
      if (getId() != other.getId())
      {
         return false;
      }
      return true;
   }
}
