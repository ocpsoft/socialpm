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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.NaturalId;

import com.ocpsoft.data.PersistentObject;
import com.ocpsoft.util.Strings;

@Entity
@Table(name = "authorities")
@NamedQueries( { @NamedQuery(name = "authority.byUserId", query = "from Authority a where a.user.id = ?") })
public class Authority extends PersistentObject<Authority> implements Comparable<Authority>
{
   private static final String DELIM = "_";

   private static final long serialVersionUID = 5317479113915801691L;

   @Index(name = "authUserIndex1")
   @NaturalId
   @ManyToOne
   @JoinColumn(nullable = false, updatable = false)
   private User user;

   @Index(name = "authAuthIndex1")
   @NaturalId
   @Column(nullable = false, updatable = false, length = 64)
   private String authority;

   public Authority()
   {
   }

   public Authority(final String authority)
   {
      this.authority = authority;
   }

   /**
    * Create a new Authority named from a set of strings -- does not assign a
    * User. E.g.: strings of "foo", "bar" would return an authority named
    * "foo_bar"
    * 
    * @param strings
    * @return
    */
   private static Authority fromStrings(final String... strings)
   {
      return new Authority(Strings.join(DELIM, strings));
   }

   public static Authority fromRole(final Role role)
   {
      return fromStrings("ROLE", role.toString());
   }

   public static Authority fromRole(final User user, final Role role)
   {
      Authority auth = fromStrings("ROLE", role.toString());
      auth.setUser(user);
      return auth;
   }

   /**
    * Disassemble an Authority name into its set of delimited strings. See
    * inverse {@link Authority.fromStrings}
    * 
    * @param authority
    * @return
    */
   public static String[] toStrings(final Authority authority)
   {
      return authority.getAuthority().split(DELIM);
   }

   @Override
   public int compareTo(final Authority rhs)
   {
      if (authority instanceof String)
      {
         return authority.compareTo(rhs.getAuthority());
      }
      else if (!(rhs.getAuthority() instanceof String))
      {
         return 0;
      }
      return -1;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = prime * (authority == null ? 0 : authority.hashCode());
      return result;
   }

   @Override
   public boolean equals(final Object obj)
   {
      if (this == obj)
      {
         return true;
      }
      if (this.getClass() != obj.getClass())
      {
         return false;
      }
      Authority other = (Authority) obj;
      if (authority == null)
      {
         if (other.authority != null)
         {
            return false;
         }
      }
      else if (!authority.equals(other.authority))
      {
         return false;
      }
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

   public User getUser()
   {
      return user;
   }

   public Authority setUser(final User user)
   {
      this.user = user;
      return this;
   }

   public String getAuthority()
   {
      return authority;
   }

   public Authority setAuthority(final String authority)
   {
      this.authority = authority;
      return this;
   }
}
