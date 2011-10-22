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

package com.ocpsoft.socialpm.domain.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.ocpsoft.socialpm.domain.PersistentObject;
import com.ocpsoft.socialpm.domain.user.Profile;

@Entity
@Table(name = "project_memberships")
@NamedQueries({
         @NamedQuery(name = "membership.projectsByUserId", query = "select m.project from Membership m where m.user.id=?"),
         @NamedQuery(name = "membership.usersByProject", query = "select m.user from Membership m where m.project=?"),
         @NamedQuery(name = "membership.usersByProjectAndRole", query = "select m.user from Membership m where m.project=? and m.role=?") })
public class Membership extends PersistentObject<Membership>
{
   private static final long serialVersionUID = 2779206239693394734L;

   @Column(nullable = false, length = 32)
   @Enumerated(EnumType.STRING)
   private MemberRole role;

   @ManyToOne(optional = false)
   @Index(name = "membershipProjectIndex")
   @OnDelete(action = OnDeleteAction.CASCADE)
   private Project project;

   @Index(name = "membershipUserIndex")
   @ManyToOne(optional = false)
   private Profile user;

   private boolean preferred;

   public Membership()
   {
   }

   public Membership(final Project project, final Profile user, final MemberRole role)
   {
      this.project = project;
      this.user = user;
      this.role = role;
   }

   public boolean isRequested()
   {
      return MemberRole.REQUESTED.equals(role);
   }

   public boolean isInvited()
   {
      return MemberRole.INVITED.equals(role);
   }

   public boolean isActive()
   {
      return MemberRole.isActiveMemberRole(role);
   }

   @Override
   public String toString()
   {
      return "member:[" + project + ", " + user + ", " + role + "]";
   }

   public MemberRole getRole()
   {
      return role;
   }

   public void setRole(final MemberRole role)
   {
      this.role = role;
   }

   public Project getProject()
   {
      return project;
   }

   public void setProject(final Project project)
   {
      this.project = project;
   }

   public Profile getUser()
   {
      return user;
   }

   public void setUser(final Profile user)
   {
      this.user = user;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((project == null) ? 0 : project.hashCode());
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
      if (!(obj instanceof Membership))
      {
         return false;
      }
      Membership other = (Membership) obj;
      if (project == null)
      {
         if (other.project != null)
         {
            return false;
         }
      }
      else if (!project.equals(other.project))
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

   public boolean isPreferred()
   {
      return preferred;
   }

   public void setPreferred(final boolean preferred)
   {
      this.preferred = preferred;
   }
}
