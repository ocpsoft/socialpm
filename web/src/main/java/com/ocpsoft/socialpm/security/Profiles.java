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

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.jboss.seam.security.Identity;

import com.ocpsoft.socialpm.cdi.LoggedIn;
import com.ocpsoft.socialpm.domain.user.Profile;
import com.ocpsoft.socialpm.model.ProfileService;
import com.ocpsoft.socialpm.web.ParamsBean;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
@Named("profiles")
@RequestScoped
public class Profiles implements Serializable
{
   private static final long serialVersionUID = 8474539305281711165L;

   @Inject
   private EntityManager em;

   @Inject
   private Identity identity;

   @Inject
   private ProfileService ps;

   @Inject
   private ParamsBean params;

   @PostConstruct
   public void init()
   {
      ps.setEntityManager(em);
   }

   @Produces
   @Named("profile")
   @RequestScoped
   public Profile getCurrent()
   {
      Profile result = new Profile();
      try {
         result = ps.getProfileByUsername(params.getProfileUsername());
      }
      catch (NoResultException e) {
         // not an error
      }
      return result;
   }

   @Produces
   @LoggedIn
   @Named("userProfile")
   @RequestScoped
   public Profile getLoggedIn()
   {
      Profile loggedIn = new Profile();
      try {
         if (identity.isLoggedIn())
         {
            loggedIn = ps.getProfileByIdentityKey(identity.getUser().getKey());
         }
         else if (!identity.isLoggedIn())
         {}
      }
      catch (NoResultException e) {
         // TODO log error
      }
      return loggedIn;
   }

   public String dismissBootcamp()
   {
      Profile current = getLoggedIn();
      current.setShowBootcamp(false);
      ps.save(current);
      return "/pages/home?faces-redirect=true";
   }

   public void setEntityManager(final EntityManager em)
   {
      this.em = em;
   }
}
