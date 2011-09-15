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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;

import com.ocpsoft.socialpm.domain.PersistentObject;
import com.ocpsoft.socialpm.domain.security.IdentityObject;

@Entity
@Table(name = "users")
@NamedQueries({
         @NamedQuery(name = "user.byEmail", query = "from User where email = ?"),
         @NamedQuery(name = "user.byRegKey", query = "from User where registrationKey = ?"),
         @NamedQuery(name = "user.byName", query = "from User where username = ?"),
         @NamedQuery(name = "user.byCanonicalName", query = "from User where canonicalUsername = ?") })
public class User extends PersistentObject<User>
{
   @Transient
   private static final long serialVersionUID = 7655987424212407525L;

   @Index(name = "userEmailIndex")
   @Column(nullable = false, length = 128, unique = true)
   private String email;

   @OneToOne(optional = false)
   private IdentityObject identity;

   @OneToOne(cascade = CascadeType.ALL)
   private UserProfile profile;

   @Index(name = "userRegKeyIndex")
   @Column(nullable = false, length = 64, unique = true)
   private String registrationKey;

   public String getEmail()
   {
      return email;
   }

   public void setEmail(final String address)
   {
      email = address;
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

   public IdentityObject getIdentity()
   {
      return identity;
   }

   public void setIdentity(final IdentityObject identity)
   {
      this.identity = identity;
   }
}
