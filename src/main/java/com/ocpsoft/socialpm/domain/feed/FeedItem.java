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

package com.ocpsoft.socialpm.domain.feed;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.ocpsoft.data.PersistentObject;
import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.domain.user.User;

@XmlRootElement
@Entity
@Table(name = "activity_feed")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING, length = 15)
@DiscriminatorValue("basic")
@NamedQueries( { @NamedQuery(name = "feedItem.byUser", query = "from FeedItem where user = ? order by createdOn"), @NamedQuery(name = "feedItem.byProject", query = "from FeedItem where project = ? order by createdOn") })
public class FeedItem extends PersistentObject<FeedItem>
{
   private static final long serialVersionUID = 7038341222060982180L;

   @OneToOne
   private Project project;

   @OneToOne
   private User user;

   protected FeedItem()
   {
   }

   public FeedItem(final Project project, final User user)
   {
      this.project = project;
      this.user = user;
   }

   public Project getProject()
   {
      return project;
   }

   public FeedItem setProject(final Project project)
   {
      this.project = project;
      return this;
   }

   public User getUser()
   {
      return user;
   }

   public FeedItem setUser(final User user)
   {
      this.user = user;
      return this;
   }
}
