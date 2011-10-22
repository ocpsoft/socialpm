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
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.jboss.seam.international.status.Messages;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.management.IdmAuthenticator;

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
   private FacesContext context;

   @Inject
   private Identity identity;

   @Inject
   private Messages messages;

   private boolean loginFailed;

   public String login() throws InterruptedException
   {
      String result = Identity.RESPONSE_LOGIN_FAILED;
      identity.setAuthenticatorClass(IdmAuthenticator.class);
      try {
         result = identity.login();
      }
      catch (Exception e) {
         result = identity.login();
      }

      if (Identity.RESPONSE_LOGIN_SUCCESS.equals(result)) {
         String viewId = context.getViewRoot().getViewId();
         if (!"/pages/signup.xhtml".equals(viewId))
            result = viewId;
         else
            result = "/pages/home";
      }
      else if (Identity.RESPONSE_LOGIN_FAILED.equals(result)) {
         result = "/pages/login";
         Thread.sleep(500);
         loginFailed = true;
         messages.warn("Whoops! We didn't recognize that username or password. Care to try again?");
      }
      else if (Identity.RESPONSE_LOGIN_EXCEPTION.equals(result)) {
         loginFailed = true;
         result = "/pages/login";
         logger.error("Login failure. " + identity.getAuthenticatorName() + ", " + identity.getAuthenticatorClass()
                  + ", " + identity);
         messages.warn("Whoops! Something went wrong with your login. Care to try again? While we figure out what went wrong?");
      }

      return result + "?faces-redirect=true";
   }

   @Inject
   private HttpSession session;

   public String logout()
   {
      identity.setAuthenticatorClass(IdmAuthenticator.class);
      identity.logout();

      session.invalidate();

      return "/pages/home?faces-redirect=true";
   }

   public boolean isLoginFailed()
   {
      return loginFailed;
   }

   public void setLoginFailed(final boolean loginFailed)
   {
      this.loginFailed = loginFailed;
   }
}
