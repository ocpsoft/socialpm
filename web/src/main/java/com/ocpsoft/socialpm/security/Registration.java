/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.ocpsoft.socialpm.security;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.international.status.Messages;
import org.jboss.seam.security.Authenticator.AuthenticationStatus;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.external.openid.OpenIdAuthenticator;
import org.jboss.seam.security.management.IdmAuthenticator;
import org.picketlink.idm.api.AttributesManager;
import org.picketlink.idm.api.IdentitySession;
import org.picketlink.idm.api.PersistenceManager;
import org.picketlink.idm.api.User;
import org.picketlink.idm.common.exception.IdentityException;
import org.picketlink.idm.impl.api.PasswordCredential;

import com.ocpsoft.logging.Logger;
import com.ocpsoft.spm.ws.rest.resources.UserResource;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
@Named
@RequestScoped
public class Registration
{
   private static Logger log = Logger.getLogger(Registration.class);

   @Inject
   private Messages msg;

   @Inject
   private UserResource ur;

   @Inject
   private Identity identity;

   @Inject
   private Credentials credentials;

   @Inject
   private IdentitySession security;

   @Inject
   private IdmAuthenticator idm;

   private String username;
   private String password;
   private String passwordConfirm;
   private String email;

   public String register() throws IdentityException
   {
      // TODO validate username, email address, and user existence
      PersistenceManager identityManager = security.getPersistenceManager();
      User user = identityManager.createUser(username);

      AttributesManager attributesManager = security.getAttributesManager();
      attributesManager.updatePassword(user, password);
      attributesManager.addAttribute(user, "email", email);

      identity.setAuthenticatorClass(IdmAuthenticator.class);
      credentials.setUsername(username);
      credentials.setCredential(new PasswordCredential(password));

      String result = identity.login();
      AuthenticationStatus status = idm.getStatus();

      return getResult(result, status);
   }

   @Inject
   private OpenIdAuthenticator openId;

   public String openRegister() throws IdentityException
   {
      identity.setAuthenticatorClass(OpenIdAuthenticator.class);
      String result = identity.login();

      /*
       * Try again to work around some state bug in Seam Security
       * TODO file issue in seam security
       */
      if (Identity.RESPONSE_LOGIN_EXCEPTION.equals(result)) {
         result = identity.login();
      }

      AuthenticationStatus status = openId.getStatus();

      return getResult(result, status);
   }

   public String getResult(String result, AuthenticationStatus status)
   {
      if (status == null)
      {
         status = AuthenticationStatus.FAILURE;
      }

      switch (status)
      {
      case FAILURE:
         result = "/pages/signup?faces-redirect=true";
         msg.error("Oops! We couldn't register you right now. Try again?");
         break;
      case SUCCESS:
         result = "/pages/home?faces-redirect=true";
         msg.info("Welcome! Now that you're here, why not look around?");
         break;

      default:
         break;
      }
      return result;
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

   public String getEmail()
   {
      return email;
   }

   public void setEmail(final String email)
   {
      this.email = email;
   }

   public String getPasswordConfirm()
   {
      return passwordConfirm;
   }

   public void setPasswordConfirm(final String passwordConfirm)
   {
      this.passwordConfirm = passwordConfirm;
   }
}