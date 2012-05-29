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

package com.ocpsoft.socialpm.model.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;

import org.hibernate.annotations.Index;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;

import com.ocpsoft.socialpm.model.PersistentObject;

@Bindable
@Portable
@Entity
@Table(name = "user_profiles")
public class Profile extends PersistentObject<Profile>
{
   private static final long serialVersionUID = 1894975986966495155L;

   @Column
   private boolean confirmed;

   @Column
   private boolean showBootcamp;

   @Index(name = "userEmailIndex")
   @Column(nullable = false, length = 128, unique = true)
   private String email;

   @ElementCollection(fetch = FetchType.EAGER)
   @CollectionTable(name = "user_profiles_keys")
   private Set<String> identityKeys = new HashSet<String>();

   @Column
   private boolean emailSecret = true;

   @Column(length = 24, nullable = false)
   private String username;

   @Column(length = 70)
   private String fullName;

   @Column(length = 50)
   private String screenName;

   @Column(length = 50)
   private String employer;

   @Column(length = 512)
   private String bio;

   public Profile()
   {}

   public Profile(String username)
   {
      this.username = username;
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

   public String getEmail()
   {
      return email;
   }

   public void setEmail(final String email)
   {
      this.email = email;
   }

   public String getUsername()
   {
      return username;
   }

   public void setUsername(String username)
   {
      if (username != null)
      {
         username = username.toLowerCase();
      }
      this.username = username;
   }

   public Set<String> getIdentityKeys()
   {
      return identityKeys;
   }

   public void setIdentityKeys(final Set<String> keys)
   {
      this.identityKeys = keys;
   }

   public boolean isUsernameConfirmed()
   {
      return confirmed;
   }

   public void setUsernameConfirmed(final boolean confirmed)
   {
      this.confirmed = confirmed;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = (prime * result) + ((username == null) ? 0 : username.hashCode());
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
      Profile other = (Profile) obj;
      if (username == null) {
         if (other.username != null)
            return false;
      }
      else if (!username.equals(other.username))
         return false;
      return true;
   }

   public boolean isShowBootcamp()
   {
      return showBootcamp;
   }

   public void setShowBootcamp(final boolean showBootcamp)
   {
      this.showBootcamp = showBootcamp;
   }

   @Override
   public String toString()
   {
      return username;
   }

}
