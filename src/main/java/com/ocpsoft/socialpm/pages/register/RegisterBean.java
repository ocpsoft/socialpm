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

package com.ocpsoft.socialpm.pages.register;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.international.status.Messages;

import com.ocpsoft.socialpm.domain.user.Authority;
import com.ocpsoft.socialpm.domain.user.User;
import com.ocpsoft.socialpm.domain.user.auth.Role;
import com.ocpsoft.socialpm.model.UserService;
import com.ocpsoft.socialpm.security.events.LoginEvent;

@Named
@RequestScoped
public class RegisterBean
{
   private static final long serialVersionUID = 7676741843727916104L;

   private String username = "";
   private String password = "";
   private String passwordConfirm = "";
   private String email = "";
   private Boolean legal = false;

   @Inject
   private Messages messages;

   @Inject
   private UserService us;

   @Inject
   private Event<LoginEvent> event;

   public void doRegister() throws IOException
   {
      User u = new User();
      u.setUsername(username);
      u.setPassword(password);
      u.setEmail(email);
      String registrationKey = us.registerUser(u);
      us.addAuthority(u, new Authority(Role.MEMBER.value()));
      us.verifyUser(registrationKey);
      us.enableAccount(u, password);
      messages.info("Thank you for registering! Please enjoy!");

      LoginEvent login = new LoginEvent();
      login.setUsername(username);
      login.setPassword(password);
      event.fire(login);
   }

   public String getUsername()
   {
      return username;
   }

   public String getPassword()
   {
      return password;
   }

   public String getPasswordConfirm()
   {
      return passwordConfirm;
   }

   public String getEmail()
   {
      return email;
   }

   public void setUsername(final String username)
   {
      this.username = username;
   }

   public void setPassword(final String password)
   {
      this.password = password;
   }

   public void setPasswordConfirm(final String passwordConfirm)
   {
      this.passwordConfirm = passwordConfirm;
   }

   public void setEmail(final String email)
   {
      this.email = email;
   }

   public Boolean getLegal()
   {
      return legal;
   }

   public void setLegal(final Boolean legal)
   {
      this.legal = legal;
   }
}
