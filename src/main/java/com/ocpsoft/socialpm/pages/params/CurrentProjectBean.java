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

package com.ocpsoft.socialpm.pages.params;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.ocpsoft.exceptions.NoSuchObjectException;
import com.ocpsoft.socialpm.constants.UrlConstants;
import com.ocpsoft.socialpm.domain.project.MemberRole;
import com.ocpsoft.socialpm.domain.project.Membership;
import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.domain.user.User;
import com.ocpsoft.socialpm.security.LoggedIn;
import com.ocpsoft.util.Strings;

@Named
@Stateful
@ConversationScoped
public class CurrentProjectBean extends PageAware
{
   private static final long serialVersionUID = 1069537783421568711L;

   private Project project;

   @Inject
   @LoggedIn
   private User user;

   public boolean isValued()
   {
      return params.getProjectName() != null;
   }

   @Produces
   @Current
   public Project getProject() throws NoSuchObjectException
   {
      if ((project == null) && (params.getProjectName() != null))
      {
         project = ps.getByName(Strings.canonicalize(params.getProjectName()));
      }
      return project;
   }

   public Project getExceptionSafeProject()
   {
      try
      {
         return getProject();
      }
      catch (NoSuchObjectException e)
      {
         return new Project();
      }
   }

   public String requestMembership()
   {
      ps.requestMembership(getProject(), user);
      return facesUtils.beautify(UrlConstants.VIEW_PROJECT);
   }

   public Membership getMembership()
   {
      return getProject().getMembership(user);
   }

   public String acceptMembershipInvitation()
   {
      ps.setMemberRole(getProject(), user, MemberRole.MEMBER);
      return facesUtils.beautify(UrlConstants.REFRESH);
   }
}
