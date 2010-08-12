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

package com.ocpsoft.socialpm.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.ocpsoft.data.PersistentObject;

@Entity
@Table(name = "user_profiles")
@NamedQueries({ @NamedQuery(name = "userProfile.byUserId", query = "from UserProfile prof where prof.user.id = ?") })
public class UserProfile extends PersistentObject<UserProfile>
{
   private static final long serialVersionUID = 1894975986966495155L;

   @Index(name = "userProfileUserIndex")
   @OneToOne(optional = false)
   private User user;

   @Column
   private boolean emailSecret = true;

   @Column(length = 70)
   private String fullName;

   @Column(length = 50)
   private String screenName;

   @Column(length = 50)
   private String employer;

   @Column(length = 512)
   private String bio;

   public User getUser()
   {
      return user;
   }

   public void setUser(final User user)
   {
      this.user = user;
   }

   public String getScreenName()
   {
      return screenName;
   }

   public void setScreenName(final String screenName)
   {
      this.screenName = screenName;
   }

   public String getEmployer()
   {
      return employer;
   }

   public void setEmployer(final String employer)
   {
      this.employer = employer;
   }

   public String getBio()
   {
      return bio;
   }

   public void setBio(final String bio)
   {
      this.bio = bio;
   }

   public boolean isEmailSecret()
   {
      return emailSecret;
   }

   public String getFullName()
   {
      return fullName;
   }

   public void setFullName(final String fullName)
   {
      this.fullName = fullName;
   }

   public void setEmailSecret(final boolean emailSecret)
   {
      this.emailSecret = emailSecret;
   }
}
