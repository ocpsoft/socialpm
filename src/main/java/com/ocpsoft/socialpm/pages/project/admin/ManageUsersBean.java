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

package com.ocpsoft.socialpm.pages.project.admin;

import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.ocpsoft.exceptions.NoSuchObjectException;
import com.ocpsoft.socialpm.constants.UrlConstants;
import com.ocpsoft.socialpm.domain.project.MemberRole;
import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.domain.user.User;
import com.ocpsoft.socialpm.pages.PageBean;
import com.ocpsoft.socialpm.pages.params.Current;

@Named
@RequestScoped
public class ManageUsersBean extends PageBean
{
   private static final long serialVersionUID = -4390926645764778896L;

   @Inject
   @Current
   private Project project;

   private List<MemberRole> allRoles;
   private List<MemberRole> activeRoles;

   private String invitee;

   public String load()
   {
      try
      {
         allRoles = Arrays.asList(MemberRole.values());
         activeRoles = MemberRole.getActiveMemberRoles();
      }
      catch (NoSuchObjectException e)
      {
         facesUtils.addErrorMessage("That project does not exist. Create it?");
         return facesUtils.beautify(UrlConstants.CREATE_PROJECT);
      }

      return null;
   }

   public String inviteByUsername()
   {
      try
      {
         User user = us.getUserByName(invitee);
         inviteMember(user);
      }
      catch (Exception e)
      {
         facesUtils.addWarningMessage("Could not find user with email addess: " + invitee);
      }
      return facesUtils.beautify(UrlConstants.REFRESH);
   }

   public String makeMember(final User u)
   {
      ps.setMemberRole(project, u, MemberRole.MEMBER);
      return facesUtils.beautify(UrlConstants.REFRESH);
   }

   public String makeAdmin(final User u)
   {
      ps.setMemberRole(project, u, MemberRole.ADMIN);
      return facesUtils.beautify(UrlConstants.REFRESH);
   }

   public String makeOwner(final User u)
   {
      ps.setMemberRole(project, u, MemberRole.OWNER);
      return facesUtils.beautify(UrlConstants.REFRESH);
   }

   public String inviteMember(final User u)
   {
      ps.inviteMember(project.getId(), u.getId());
      return facesUtils.beautify(UrlConstants.REFRESH);
   }

   public String removeMember(final User u)
   {
      ps.leaveProject(project, u);
      return facesUtils.beautify(UrlConstants.REFRESH);
   }

   public String acceptAllMembershipRequests()
   {
      for (User u : project.getMembersByRoles(MemberRole.REQUESTED))
      {
         makeMember(u);
      }
      return facesUtils.beautify(UrlConstants.REFRESH);
   }

   public String removeAllMembershipRequests()
   {
      for (User u : project.getPendingMembers())
      {
         removeMember(u);
      }
      return facesUtils.beautify(UrlConstants.REFRESH);
   }

   public Project getProject()
   {
      return project;
   }

   public void setProject(final Project project)
   {
      this.project = project;
   }

   public List<MemberRole> getAllRoles()
   {
      return allRoles;
   }

   public void setAllRoles(final List<MemberRole> allRoles)
   {
      this.allRoles = allRoles;
   }

   public List<MemberRole> getActiveRoles()
   {
      return activeRoles;
   }

   public void setActiveRoles(final List<MemberRole> activeRoles)
   {
      this.activeRoles = activeRoles;
   }

   public String getInvitee()
   {
      return invitee;
   }

   public void setInvitee(final String inviteAddress)
   {
      invitee = inviteAddress;
   }
}
