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

package com.ocpsoft.socialpm.security;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.ocpsoft.socialpm.domain.project.MemberRole;
import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.domain.user.Authority;
import com.ocpsoft.socialpm.domain.user.User;
import com.ocpsoft.socialpm.pages.PageBean;

@Named
@RequestScoped
public class AuthorizationServiceImpl extends PageBean implements AuthorizationService
{
   private static final long serialVersionUID = 3967943516009840856L;

   @Inject
   @LoggedIn
   User user;

   @Override
   public boolean isAdmin()
   {
      boolean isOwner = isOwner();
      boolean hasAdminRole = isUserInRole(MemberRole.ADMIN);
      return isOwner || hasAdminRole;
   }

   @Override
   public boolean isMember()
   {
      boolean isAdmin = isAdmin();
      boolean hasMemberRole = isUserInRole(MemberRole.MEMBER);
      return isAdmin || hasMemberRole;
   }

   @Override
   public boolean isOwner()
   {
      boolean hasOwnerRole = isUserInRole(MemberRole.OWNER);
      return hasOwnerRole;
   }

   private boolean isUserInRole(final MemberRole role)
   {
      Project project = getProject();
      return project.hasMemberInRoles(user, role);
   }

   private Project getProject()
   {
      return currentProjectBean.getExceptionSafeProject();
   }

   @Override
   public boolean isInRole(final String role)
   {
      List<Authority> authorities = us.getAuthorities(user.getId());
      return authorities.contains(role);
   }
}
