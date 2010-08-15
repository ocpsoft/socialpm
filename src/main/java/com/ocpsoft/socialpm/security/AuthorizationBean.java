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

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.international.status.Messages;

import com.ocpsoft.pretty.PrettyContext;
import com.ocpsoft.socialpm.security.events.LoginRedirectPage;
import com.ocpsoft.socialpm.web.constants.UrlConstants;

@Named
@RequestScoped
public class AuthorizationBean
{
   @Inject
   private LoginRedirectPage redirect;

   @Inject
   Messages messages;

   @Inject
   private LoggedInUserBean liub;

   public String assertLoggedIn()
   {
      if (!liub.isLoggedIn())
      {
         PrettyContext prettyContext = PrettyContext.getCurrentInstance();
         redirect.setPage(prettyContext.getContextPath() + prettyContext.getRequestURL() + "" + prettyContext.getRequestQueryString());
         messages.info("You requested a page that requires you to be logged in.");
         return UrlConstants.LOGIN;
      }
      return null;
   }
}
