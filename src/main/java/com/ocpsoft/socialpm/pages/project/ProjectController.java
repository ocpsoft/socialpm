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

package com.ocpsoft.socialpm.pages.project;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.ocpsoft.socialpm.constants.UrlConstants;
import com.ocpsoft.socialpm.domain.project.Membership;
import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.domain.user.User;
import com.ocpsoft.socialpm.pages.params.PageAware;

@Named
@RequestScoped
public class ProjectController extends PageAware
{
   private static final long serialVersionUID = 5224914273795497524L;

   public Membership getMembership(final User u, final Project p)
   {
      return p.getMembership(u);
   }

   public String requestMembership(final User u, final Project p)
   {
      ps.requestMembership(p, u);
      return facesUtils.beautify(UrlConstants.REFRESH);
   }

   public String leaveProject(final User u, final Project p)
   {
      ps.leaveProject(p, u);
      return facesUtils.beautify(UrlConstants.REFRESH);
   }

   public List<Project> projectsForUser(final User user)
   {
      return us.getUserProjects(user.getId());
   }
}
