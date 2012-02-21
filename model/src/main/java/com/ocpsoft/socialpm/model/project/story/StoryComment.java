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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "story_comments")
public class StoryComment extends PersistentObject<StoryComment>
{
   private static final long serialVersionUID = -7877352379663554907L;

   @OneToOne
   @JoinColumn(updatable = false, nullable = false)
   @Cascade(value = CascadeType.REFRESH)
   @Index(name = "commentAuthorIndex")
   private Profile author;

   @Column(nullable = false, length = 2048)
   private String text;

   @ManyToOne
   @Index(name = "commentStoryIndex")
   @JoinColumn(updatable = false, nullable = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   private Story story;

   public Profile getAuthor()
   {
      return author;
   }

   public void setAuthor(final Profile author)
   {
      this.author = author;
   }

   public String getText()
   {
      return text;
   }

   public void setText(final String text)
   {
      this.text = text;
   }

   public void setStory(final Story story)
   {
      this.story = story;
   }

   public Story getStory()
   {
      return story;
   }
}
