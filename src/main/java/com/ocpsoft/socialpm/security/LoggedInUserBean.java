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

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.jboss.seam.international.status.Messages;

import com.ocpsoft.pretty.PrettyContext;
import com.ocpsoft.socialpm.domain.NoSuchObjectException;
import com.ocpsoft.socialpm.domain.user.User;
import com.ocpsoft.socialpm.domain.user.UserPasswordMatchTester;
import com.ocpsoft.socialpm.domain.user.UserProfile;
import com.ocpsoft.socialpm.model.UserService;
import com.ocpsoft.socialpm.security.events.LoginEvent;
import com.ocpsoft.socialpm.security.events.LoginFailedEvent;
import com.ocpsoft.socialpm.security.events.LoginRedirectPage;
import com.ocpsoft.socialpm.security.events.LoginSuccessfulEvent;
import com.ocpsoft.socialpm.security.events.LogoutEvent;
import com.ocpsoft.socialpm.web.constants.UrlConstants;

@Named
@RequestScoped
public class LoggedInUserBean implements Serializable
{
   private static final long serialVersionUID = -1373930544088844699L;

   private static final String GUEST_USERNAME = "guest";
   private UserProfile profile;

   @Inject
   private UserService us;
   @Inject
   private Messages messages;

   @Inject
   private Event<LoginFailedEvent> loginFailed;
   @Inject
   private Event<LoginSuccessfulEvent> loginSuccess;
   @Inject
   private LoginRedirectPage redirect;
   @Inject
   private LoggedInUser loggedInUser;
   @Inject
   private NavigationHandler navHandler;

   private final User GUEST = new User();

   public LoggedInUserBean()
   {
      GUEST.setUsername(GUEST_USERNAME);
   }

   @PostConstruct
   public void init()
   {
      if (getLoggedInUser() == null)
      {
         try
         {
            setUser(us.getUserByName(GUEST_USERNAME));
         }
         catch (NoSuchObjectException e)
         {
            messages.error("Database not initialized.");
         }
      }
   }

   public void login(@Observes final LoginEvent event)
   {
      if (event.isRedirect())
      {
         PrettyContext prettyContext = PrettyContext.getCurrentInstance();
         redirect.setPage(prettyContext.getContextPath() + prettyContext.getRequestURL() + "" + prettyContext.getRequestQueryString());
      }
      try
      {
         User user = us.getUserByName(event.getUsername());

         UserPasswordMatchTester tester = new UserPasswordMatchTester();
         if (tester.passwordMatches(user, event.getPassword()))
         {
            setUser(user);
            loginSuccess.fire(new LoginSuccessfulEvent(user, event.isRedirect()));
         }
         else
         {
            loginFailed.fire(new LoginFailedEvent(event.getUsername()));
         }
      }
      catch (NoSuchObjectException e)
      {
         loginFailed.fire(new LoginFailedEvent(event.getUsername()));
      }
   }

   public void loginFailed(@Observes final LoginFailedEvent event)
   {
      messages.error("Sorry to bug you, but the username or password was incorrect; would you try again?");
      FacesContext context = FacesContext.getCurrentInstance();
      navHandler.handleNavigation(context, PrettyContext.getCurrentInstance().getCurrentViewId(), UrlConstants.LOGIN);
   }

   public void loginSucceeded(@Observes final LoginSuccessfulEvent event)
   {
      messages.info("Hey {0}, great to see you again!").textParams(event.getUser().getUsername());
      FacesContext context = FacesContext.getCurrentInstance();
      if (redirect.getPage() != null)
      {
         try
         {
            context.getExternalContext().redirect(redirect.getPage());
            context.responseComplete();
         }
         catch (IOException e)
         {
            throw new RuntimeException(e);
         }
      }
      else
      {
         navHandler.handleNavigation(context, PrettyContext.getCurrentInstance().getCurrentViewId(), UrlConstants.HOME);
      }
   }

   public void logout(@Observes final LogoutEvent event)
   {
      FacesContext context = FacesContext.getCurrentInstance();
      setUser(null);
      ((HttpSession) context.getExternalContext().getSession(true)).invalidate();
      navHandler.handleNavigation(context, PrettyContext.getCurrentInstance().getCurrentViewId(), UrlConstants.HOME);
   }

   public boolean isLoggedIn()
   {
      return (getLoggedInUser() != null) && !GUEST.equals(getLoggedInUser());
   }

   @Produces
   @LoggedIn
   public User getUser()
   {
      return getLoggedInUser();
   }

   @Produces
   @LoggedIn
   public UserProfile getProfile()
   {
      if (profile == null)
      {
         profile = getLoggedInUser().getProfile();
      }
      return profile;
   }

   public void setUser(final User user)
   {
      loggedInUser.setUser(user);
   }

   private User getLoggedInUser()
   {
      return loggedInUser.getUser();
   }
}
