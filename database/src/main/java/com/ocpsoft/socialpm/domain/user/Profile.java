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

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.ocpsoft.socialpm.domain.PersistentObject;
import com.ocpsoft.socialpm.domain.security.IdentityObject;

@Entity
@Table(name = "user_profiles")
public class Profile extends PersistentObject<Profile>
{
   private static final long serialVersionUID = 1894975986966495155L;

   @Index(name = "userEmailIndex")
   @Column(nullable = false, length = 128, unique = true)
   private String email;

   @OneToMany
   private Set<IdentityObject> identities;

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

   public String getEmail()
   {
      return email;
   }

   public void setEmail(final String email)
   {
      this.email = email;
   }

   public Set<IdentityObject> getIdentities()
   {
      return identities;
   }

   public void setIdentities(final Set<IdentityObject> identities)
   {
      this.identities = identities;
   }
}
