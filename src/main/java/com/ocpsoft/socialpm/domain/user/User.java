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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Index;

import com.ocpsoft.data.PersistentObject;
import com.ocpsoft.pattern.Visitor;

@Entity
@Table(name = "users")
@NamedQueries({ @NamedQuery(name = "user.byEmail", query = "from User where email = ?"), @NamedQuery(name = "user.byName", query = "from User where username = ?") })
public class User extends PersistentObject<User>
{
   @Transient
   private static final long serialVersionUID = 7655987424212407525L;

   @Column(nullable = false, unique = true, length = 36)
   @Index(name = "userNameIndex")
   private String username;

   @Column(nullable = false, length = 64)
   private String password;

   @Column(nullable = false, length = 128, unique = true)
   @Index(name = "userEmailIndex")
   private String email;

   @Column(nullable = false)
   private boolean enabled = false;

   @Column(nullable = false)
   private boolean accountLocked = true;

   @Column(nullable = false)
   private boolean accountExpired = true;

   @Column(nullable = false)
   private boolean credentialsExpired = true;

   @OneToOne(cascade = CascadeType.ALL)
   private UserProfile profile;

   @Fetch(FetchMode.SUBSELECT)
   @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
   private final Set<Authority> authorities = new HashSet<Authority>();

   @Override
   public String toString()
   {
      return username;
   }

   public String getUsername()
   {
      return username;
   }

   public User setUsername(final String username)
   {
      this.username = username;
      return this;
   }

   public String getPassword()
   {
      return password;
   }

   public User setPassword(final String password)
   {
      this.password = password;
      return this;
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

   public String getEmail()
   {
      return email;
   }

   public User setEmail(final String address)
   {
      email = address;
      return this;
   }

   public boolean isEnabled()
   {
      return enabled;
   }

   public User setEnabled(final boolean enabled)
   {
      this.enabled = enabled;
      return this;
   }

   public boolean isAccountLocked()
   {
      return accountLocked;
   }

   public User setAccountLocked(final boolean accountLocked)
   {
      this.accountLocked = accountLocked;
      return this;
   }

   public boolean isAccountExpired()
   {
      return accountExpired;
   }

   public User setAccountExpired(final boolean accountExpired)
   {
      this.accountExpired = accountExpired;
      return this;
   }

   public boolean isCredentialsExpired()
   {
      return credentialsExpired;
   }

   public User setCredentialsExpired(final boolean credentialsExpired)
   {
      this.credentialsExpired = credentialsExpired;
      return this;
   }

   public Set<Authority> getAuthorities()
   {
      return authorities;
   }

   public UserProfile getProfile()
   {
      return profile;
   }

   public void setProfile(UserProfile profile)
   {
      this.profile = profile;
   }
}
