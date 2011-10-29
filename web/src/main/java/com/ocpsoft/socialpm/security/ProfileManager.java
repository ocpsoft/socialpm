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

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.jboss.seam.security.Identity;
import org.jboss.seam.security.external.openid.api.OpenIdPrincipal;
import org.jboss.seam.security.management.picketlink.IdentitySessionProducer;

import com.ocpsoft.socialpm.domain.user.Profile;
import com.ocpsoft.socialpm.model.ProfileService;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
@Named
@RequestScoped
public class ProfileManager
{
   @PersistenceContext(type = PersistenceContextType.EXTENDED)
   private EntityManager em;

   @Inject
   private Identity identity;

   @Inject
   private ProfileService profileService;

   @PostConstruct
   public void init()
   {
      profileService.setEntityManager(em);
   }

   public void attachProfile(final OpenIdPrincipal principal)
   {
      try
      {
         Map<String, Object> sessionOptions = new HashMap<String, Object>();
         sessionOptions.put(IdentitySessionProducer.SESSION_OPTION_ENTITY_MANAGER, em);

         if (!profileService.hasProfileByIdentityKey(identity.getUser().getKey()))
         {
            Profile p = new Profile();
            p.getKeys().add(identity.getUser().getKey());
            p.setEmail(principal.getAttribute("email"));
            p.setFullName(principal.getAttribute("firstName") + " " + principal.getAttribute("lastName"));
            p.setUsername(profileService.getRandomUsername(p.getFullName()));
            profileService.create(p);
         }
      }
      catch (Exception e) {
         try {
            identity.logout();
         }
         catch (Exception e2) {}
         throw new RuntimeException(e);
      }
   }
}
