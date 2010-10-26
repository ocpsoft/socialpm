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

package com.ocpsoft.socialpm.pages.login;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.ocpsoft.socialpm.security.events.LoginEvent;
import com.ocpsoft.socialpm.security.events.LogoutEvent;
import com.ocpsoft.socialpm.web.constants.UrlConstants;

@Named
@RequestScoped
public class LoginBean
{
   private static final long serialVersionUID = 5708843885887988131L;
   private String username = "";
   private String password = "";
   private boolean rememberMe = false;

   @Inject
   private Event<LoginEvent> login;
   @Inject
   private Event<LogoutEvent> logout;

   public void doLogout() throws IOException
   {
      LogoutEvent logoutEvent = new LogoutEvent();
      logout.fire(logoutEvent);
   }

   public String doCancel() throws IOException
   {
      return UrlConstants.HOME;
   }

   public void doLogin()
   {
      String redirect = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("redirect");
      LoginEvent loginEvent = new LoginEvent();
      loginEvent.setUsername(username);
      loginEvent.setPassword(password);
      if (redirect != null)
      {
         loginEvent.setRedirect(true);
      }
      login.fire(loginEvent);
   }

   public String getUsername()
   {
      return username;
   }

   public void setUsername(final String username)
   {
      this.username = username;
   }

   public String getPassword()
   {
      return password;
   }

   public void setPassword(final String password)
   {
      this.password = password;
   }

   public boolean getRememberMe()
   {
      return rememberMe;
   }

   public void setRememberMe(final boolean rememberMe)
   {
      this.rememberMe = rememberMe;
   }

}
