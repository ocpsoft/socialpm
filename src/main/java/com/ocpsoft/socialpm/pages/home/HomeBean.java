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

package com.ocpsoft.socialpm.pages.home;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.faces.component.UIOutput;
import javax.inject.Inject;
import javax.inject.Named;

import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.pages.PageBean;
import com.ocpsoft.socialpm.security.LoggedIn;
import com.ocpsoft.socialpm.security.LoggedInUserBean;
import com.ocpsoft.socialpm.security.Pending;

@Named
@RequestScoped
public class HomeBean extends PageBean
{
   private static final long serialVersionUID = -4124609956493756178L;

   @Inject
   LoggedInUserBean liub;

   @Inject
   @Default
   @LoggedIn
   private List<Project> projects;

   @Inject
   @LoggedIn
   @Pending
   private List<Project> invitedProjects;

   private UIOutput notifications;

   public void load()
   {
      if (liub.isLoggedIn())
      {
         projects = liub.getProjects();
         invitedProjects = liub.getInvitedProjects();
      }
   }

   public String getView()
   {
      if (liub.isLoggedIn())
      {
         return "/faces/pages/home/home.xhtml";
      }
      return "/faces/pages/home/loggedOffHome.xhtml";
   }

   public List<Project> getProjects()
   {
      return projects;
   }

   public void setProjects(final List<Project> projects)
   {
      this.projects = projects;
   }

   public UIOutput getNotifications()
   {
      return notifications;
   }

   public void setNotifications(final UIOutput notifications)
   {
      this.notifications = notifications;
   }

   public List<Project> getInvitedProjects()
   {
      return invitedProjects;
   }

   public void setInvitedProjects(final List<Project> invitedProjects)
   {
      this.invitedProjects = invitedProjects;
   }
}
