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

import com.ocpsoft.socialpm.domain.PersistentObject;
import com.ocpsoft.socialpm.domain.user.User;

@Entity(name = "FeedEvent")
@Table(name = "activity_feed")
@DiscriminatorValue("userevent")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING, length = 15)
@NamedQueries({
         @NamedQuery(name = "feedEvent.byUser", query = "from FeedEvent where user = ? order by createdOn desc"),
         @NamedQuery(name = "feedEvent.all", query = "from FeedEvent order by createdOn desc") })
public class FeedEvent extends PersistentObject<FeedEvent>
{
   private static final long serialVersionUID = 7038341222060982180L;

   @OneToOne
   private User user;

   protected FeedEvent()
   {
   }

   public FeedEvent(final User user)
   {
      this.user = user;
   }

   public User getUser()
   {
      return user;
   }

   public void setUser(final User user)
   {
      this.user = user;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((user == null) ? 0 : user.hashCode());
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
      FeedEvent other = (FeedEvent) obj;
      if (user == null)
      {
         if (other.user != null)
         {
            return false;
         }
      }
      else if (!user.equals(other.user))
      {
         return false;
      }
      return true;
   }

   @Override
   public String toString()
   {
      return this.getClass().getSimpleName() + " [user=" + user + "]";
   }

}
