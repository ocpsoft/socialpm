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

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import org.jboss.seam.security.Identity;

import com.ocpsoft.socialpm.cdi.Current;
import com.ocpsoft.socialpm.domain.user.Profile;
import com.ocpsoft.socialpm.model.ProfileService;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
@RequestScoped
public class CurrentProfile implements Serializable
{
   private static final long serialVersionUID = 8474539305281711165L;

   @Inject
   private Identity identity;

   @Inject
   private ProfileService ps;

   @Produces
   @Current
   @Named("profile")
   @RequestScoped
   public Profile current()
   {
      Profile current = new Profile();
      try {
         if (identity.isLoggedIn())
         {
            current = ps.getProfileByIdentityKey(identity.getUser().getKey());
         }
         else if (!identity.isLoggedIn())
         {}
      }
      catch (NoResultException e) {
         e.printStackTrace();
      }
      return current;
   }
}
