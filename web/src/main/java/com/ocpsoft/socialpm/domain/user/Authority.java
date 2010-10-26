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
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.ocpsoft.socialpm.domain.PersistentObject;

@Entity
@Table(name = "authorities")
@DiscriminatorValue("basic")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING, length = 32)
public class Authority extends PersistentObject<Authority>
{
   private static final long serialVersionUID = 5317479113915801691L;

   @Column(name = "custom_auth_key", updatable = false, length = 64)
   private String key;

   public Authority()
   {
   }

   public Authority(final String key)
   {
      this.key = key;
   }

   public String getAuthority()
   {
      return key;
   }

   public void setAuthority(final String authority)
   {
      this.key = authority;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = prime * (key == null ? 0 : key.hashCode());
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
      if (key == null)
      {
         if (other.key != null)
         {
            return false;
         }
      }
      else if (!key.equals(other.key))
      {
         return false;
      }
      return true;
   }
}
