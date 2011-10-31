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

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.international.status.Messages;
import org.jboss.seam.security.Authenticator.AuthenticationStatus;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.events.UserCreatedEvent;
import org.jboss.seam.security.external.openid.OpenIdAuthenticator;
import org.jboss.seam.security.management.IdmAuthenticator;
import org.picketlink.idm.api.AttributesManager;
import org.picketlink.idm.api.IdentitySession;
import org.picketlink.idm.api.PersistenceManager;
import org.picketlink.idm.api.User;
import org.picketlink.idm.common.exception.IdentityException;
import org.picketlink.idm.impl.api.PasswordCredential;

import com.ocpsoft.logging.Logger;
import com.ocpsoft.socialpm.cdi.Current;
import com.ocpsoft.socialpm.domain.user.Profile;
import com.ocpsoft.socialpm.model.ProfileService;

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
   private EntityManager em;

   private Messages msg;
   private Identity identity;
   private Credentials credentials;
   private IdentitySession security;

   private Profile profile;
   private ProfileService ps;

   @Inject
   private OpenIdAuthenticator oidAuth;

   @Inject
   private IdmAuthenticator idmAuth;

   public Registration()
   {}

   @Inject
   public Registration(@Current Profile profile, ProfileService profileService, IdentitySession security,
            Credentials credentials,
            Identity identity, Messages msg)
   {
      this.msg = msg;
      this.identity = identity;
      this.credentials = credentials;
      this.security = security;
      this.profile = profile;
      this.ps = profileService;
   }

   @PostConstruct
   public void init()
   {
      ps.setEntityManager(em);
   }

   private String username;
   private String password;
   private String passwordConfirm;
   private String email;

   public void register() throws IdentityException
   {
      oidAuth.setStatus(AuthenticationStatus.FAILURE);
      identity.setAuthenticatorClass(IdmAuthenticator.class);

      // TODO validate username, email address, and user existence
      PersistenceManager identityManager = security.getPersistenceManager();
      User user = identityManager.createUser(username);

      AttributesManager attributesManager = security.getAttributesManager();
      attributesManager.updatePassword(user, password);
      attributesManager.addAttribute(user, "email", email);

      credentials.setUsername(username);
      credentials.setCredential(new PasswordCredential(password));

      /*
       * Try twice to work around some state bug in Seam Security
       * TODO file issue in seam security
       */
      String result = identity.login();
      if (Identity.RESPONSE_LOGIN_EXCEPTION.equals(result)) {
         result = identity.login();
      }

      // TODO figure out a good pattern for this...
      Profile p = new Profile();
      p.setEmail(email);
      p.setUsername(username);
      p.getKeys().add(identity.getUser().getKey());
      p.setUsernameConfirmed(true);
      ps.create(p);
   }

   public void openRegister() throws IdentityException
   {
      idmAuth.setStatus(AuthenticationStatus.FAILURE);
      identity.setAuthenticatorClass(OpenIdAuthenticator.class);

      /*
       * Try twice to work around some state bug in Seam Security
       * TODO file issue in seam security
       */
      String result = identity.login();
      if (Identity.RESPONSE_LOGIN_EXCEPTION.equals(result)) {
         result = identity.login();
      }
   }

   public String confirmUsername()
   {
      Profile p = ps.getProfileById(profile.getId());
      p.setUsername(username);
      p.setUsernameConfirmed(true);
      ps.save(p);
      msg.info("Congrats! Your username is, and forever will be, \"" + p.getUsername() + "\".");
      return "/pages/home?faces-redirect=true";
   }

   /*
    * This is called outside of the JSF lifecycle.
    */
   public void registerSuccess(@Observes final UserCreatedEvent event)
   {
      Object user = event.getUser();
      log.info("User registered [{}]", user);
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
