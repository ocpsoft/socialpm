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

import com.ocpsoft.pretty.PrettyContext;
import com.ocpsoft.socialpm.constants.UrlConstants;
import com.ocpsoft.socialpm.faces.FacesUtils;
import com.ocpsoft.socialpm.security.events.LoginRedirectPage;

@Named
@RequestScoped
public class AuthorizationBean
{
   private static final String ACCESS_DENIED_MEMBER = "Access denied: You must be an member of this project.";
   private static final String ACCESS_DENIED_ADMIN = "Access denied: You must be an administrator of this project.";
   private static final String ACCESS_DENIED_OWNER = "Access denied: You must be an owner of this project.";

   @Inject
   private AuthorizationService authService;

   @Inject
   private LoginRedirectPage redirect;

   @Inject
   private FacesUtils facesUtils;

   @Inject
   private LoggedInUserBean liub;

   public String assertLoggedIn()
   {
      if (!liub.isLoggedIn())
      {
         PrettyContext prettyContext = PrettyContext.getCurrentInstance();
         redirect.setPage(prettyContext.getContextPath() + prettyContext.getRequestURL() + ""
                  + prettyContext.getRequestQueryString());
         facesUtils.addInfoMessage("You requested a page that requires you to be logged in.");
         return facesUtils.beautify(UrlConstants.LOGIN);
      }
      return null;
   }

   /**
    * PrettyFaces action method for security permission checks at the page level
    * 
    * @return null if permission granted, PrettyFaces navigation string if permission denied
    */
   public String assertProjectMember()
   {
      String result = assertLoggedIn();
      if (result == null)
      {
         if (!getIsMember())
         {
            facesUtils.addErrorMessage(ACCESS_DENIED_MEMBER);
            result = facesUtils.beautify(UrlConstants.VIEW_PROJECT);
         }
      }
      return result;
   }

   /**
    * PrettyFaces action method for security permission checks at the page level
    * 
    * @return null if permission granted, PrettyFaces navigation string if permission denied
    */
   public String assertProjectOwner()
   {
      String result = assertLoggedIn();
      if (result == null)
      {
         if (!getIsOwner())
         {
            facesUtils.addErrorMessage(ACCESS_DENIED_OWNER);
            result = facesUtils.beautify(UrlConstants.VIEW_PROJECT);
         }
      }
      return result;
   }

   /**
    * PrettyFaces action method for security permission checks at the page level
    * 
    * @return null if permission granted, PrettyFaces navigation string if permission denied
    */
   public String assertProjectAdmin()
   {
      String result = assertLoggedIn();
      if (result == null)
      {
         if (!getIsAdmin())
         {
            facesUtils.addErrorMessage(ACCESS_DENIED_ADMIN);
            result = facesUtils.beautify(UrlConstants.VIEW_PROJECT);
         }
      }
      return result;
   }

   public boolean getIsAdmin()
   {
      return authService.isAdmin();
   }

   public boolean getIsMember()
   {
      return authService.isMember();
   }

   public boolean getIsOwner()
   {
      return authService.isOwner();
   }

   public boolean isInRole(final String role)
   {
      return authService.isInRole(role);
   }
}
