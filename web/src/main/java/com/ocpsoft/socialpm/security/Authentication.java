/*
 * Copyright 2011 <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ocpsoft.socialpm.security;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.jboss.seam.international.status.Messages;
import org.jboss.seam.security.Authenticator.AuthenticationStatus;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.events.DeferredAuthenticationEvent;
import org.jboss.seam.security.events.LoggedInEvent;
import org.jboss.seam.security.events.LoginFailedEvent;
import org.jboss.seam.security.external.openid.OpenIdAuthenticator;
import org.jboss.seam.security.management.IdmAuthenticator;
import org.picketlink.idm.api.User;

import com.ocpsoft.logging.Logger;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
@Named
@RequestScoped
public class Authentication
{
   Logger logger = Logger.getLogger(Authentication.class);

   @Inject
   private HttpSession session;

   @Inject
   private FacesContext context;

   @Inject
   private Identity identity;

   @Inject
   private Messages messages;

   @Inject
   private IdmAuthenticator auth;

   @Inject
   private OpenIdAuthenticator openAuth;

   public void loginSuccess(@Observes final LoggedInEvent event, final NavigationHandler navigation)
   {
      User user = event.getUser();
      logger.info("User logged in [{}, {}]", user.getId(), user.getKey());

      String result = "/pages/home";

      String viewId = context.getViewRoot().getViewId();
      if (!"/pages/signup.xhtml".equals(viewId))
         result = viewId;
      else
         result = "/pages/home";

      navigation.handleNavigation(context, null, result + "?faces-redirect=true");
   }

   /*
    * This is called outside of the JSF lifecycle.
    */
   public void openLoginSuccess(@Observes final DeferredAuthenticationEvent event, final NavigationHandler navigation)
   {
      if (event.isSuccess())
      {
         logger.info("User logged in with OpenID");
      }
      else
      {
         logger.info("User failed to login via OpenID, potentially due to cancellation");
      }
   }

   public void loginFailed(@Observes final LoginFailedEvent event, final NavigationHandler navigation)
            throws InterruptedException
   {
      if (!(OpenIdAuthenticator.class.equals(identity.getAuthenticatorClass())
      && AuthenticationStatus.DEFERRED.equals(openAuth.getStatus())))
      {
         Exception exception = event.getLoginException();
         if (exception != null)
         {
            logger.error(
                     "Login failed due to exception" + identity.getAuthenticatorName() + ", "
                              + identity.getAuthenticatorClass()
                              + ", " + identity); // TODO , exception );
            messages.warn("Whoops! Something went wrong with your login. Care to try again? We'll try to figure out what went wrong.");
         }
         else
         {
            messages.warn("Whoops! We don't recognize that username or password. Care to try again?");
         }
         Thread.sleep(500);

         navigation.handleNavigation(context, null, "/pages/login?faces-redirect=true");
      }
   }

   public void login() throws InterruptedException
   {
      identity.setAuthenticatorClass(IdmAuthenticator.class);
      try {
         identity.login();
      }
      catch (Exception e) {
         identity.login();
      }
   }

   public String logout()
   {
      identity.setAuthenticatorClass(IdmAuthenticator.class);
      identity.logout();

      session.invalidate();

      return "/pages/home?faces-redirect=true";
   }
}
