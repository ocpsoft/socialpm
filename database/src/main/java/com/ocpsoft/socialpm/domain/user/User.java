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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Index;

import com.ocpsoft.socialpm.domain.PersistentObject;
import com.ocpsoft.socialpm.domain.user.auth.UserAccountLocked;
import com.ocpsoft.socialpm.domain.user.auth.UserEnabled;
import com.ocpsoft.socialpm.domain.user.auth.UserVerified;
import com.ocpsoft.socialpm.util.pattern.Visitor;

@Entity
@Table(name = "users")
@NamedQueries({
         @NamedQuery(name = "user.byEmail", query = "from User where email = ?"),
         @NamedQuery(name = "user.byRegKey", query = "from User where registrationKey = ?"),
         @NamedQuery(name = "user.byName", query = "from User where username = ?") })
public class User extends PersistentObject<User>
{
   @Transient
   private static final long serialVersionUID = 7655987424212407525L;

   @Index(name = "userNameIndex")
   @Column(nullable = false, unique = true, length = 36)
   private String username;

   @Column(nullable = false, length = 64)
   private String password;

   @Index(name = "userEmailIndex")
   @Column(nullable = false, length = 128, unique = true)
   private String email;

   @OneToOne(cascade = CascadeType.ALL)
   private UserProfile profile;

   @Index(name = "userRegKeyIndex")
   @Column(nullable = false, length = 64, unique = true)
   private String registrationKey;

   @Fetch(FetchMode.SUBSELECT)
   @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
   @JoinTable(name = "user_authorities")
   private final Set<Authority> authorities = new HashSet<Authority>();

   public boolean isAccountLocked()
   {
      return authorities.contains(new UserAccountLocked());
   }

   public boolean isEnabled()
   {
      return authorities.contains(new UserEnabled());
   }

   public boolean isVerified()
   {
      return authorities.contains(new UserVerified());
   }

   @Override
   public String toString()
   {
      return username;
   }

   public void accept(final Visitor<User> visitor)
   {
      visitor.visit(this);
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((username == null) ? 0 : username.hashCode());
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
      if (!(obj instanceof User))
      {
         return false;
      }
      User other = (User) obj;
      if (username == null)
      {
         if (other.username != null)
         {
            return false;
         }
      }
      else if (!username.equals(other.username))
      {
         return false;
      }
      return true;
   }

   public String getUsername()
   {
      return username;
   }

   public void setUsername(final String username)
   {
      this.username = username;
   }

   public String getPassword()
   {
      return password;
   }

   public void setPassword(final String password)
   {
      this.password = password;
   }

   public String getEmail()
   {
      return email;
   }

   public void setEmail(final String address)
   {
      email = address;
   }

   public Set<Authority> getAuthorities()
   {
      return authorities;
   }

   public UserProfile getProfile()
   {
      return profile;
   }

   public void setProfile(final UserProfile profile)
   {
      this.profile = profile;
   }

   public String getRegistrationKey()
   {
      return registrationKey;
   }

   public void setRegistrationKey(final String registrationKey)
   {
      this.registrationKey = registrationKey;
   }
}
