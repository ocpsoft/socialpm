/**
 * This file is part of OCPsoft SocialPM: Agile Project Management Tools (SocialPM) 
 *
 * Copyright (c)2011 Lincoln Baxter, III <lincoln@ocpsoft.com> (OCPsoft)
 * Copyright (c)2011 OCPsoft.com (http://ocpsoft.com)
 * 
 * If you are developing and distributing open source applications under 
 * the GNU General Public License (GPL), then you are free to re-distribute SocialPM 
 * under the terms of the GPL, as follows:
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
 * For individuals or entities who wish to use SocialPM privately, or
 * internally, the following terms do not apply:
 *  
 * For OEMs, ISVs, and VARs who wish to distribute SocialPM with their 
 * products, or host their product online, OCPsoft provides flexible 
 * OEM commercial licenses.
 * 
 * Optionally, Customers may choose a Commercial License. For additional 
 * details, contact an OCPsoft representative (sales@ocpsoft.com)
 */
package com.ocpsoft.socialpm.security;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.TransactionAttribute;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.jboss.seam.security.Identity;

import com.ocpsoft.socialpm.cdi.LoggedIn;
import com.ocpsoft.socialpm.cdi.Web;
import com.ocpsoft.socialpm.model.user.Profile;
import com.ocpsoft.socialpm.services.ProfileService;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
@Named("account")
@RequestScoped
public class Account implements Serializable
{
   private static final long serialVersionUID = 8474539305281711165L;

   @Inject
   @Web
   private EntityManager em;

   @Inject
   private Identity identity;

   @Inject
   private ProfileService ps;

   @PostConstruct
   public void init()
   {
      ps.setEntityManager(em);
   }

   Profile loggedIn = new Profile();

   @Produces
   @LoggedIn
   @RequestScoped
   @Named("userProfile")
   public Profile getLoggedIn()
   {
      if (identity.isLoggedIn() && !loggedIn.isPersistent())
      {
         try {
            loggedIn = ps.getProfileByIdentityKey(identity.getUser().getKey());
         }
         catch (NoResultException e) {
            throw e;
         }
      }
      else if (!identity.isLoggedIn())
      {}
      return loggedIn;
   }

   @TransactionAttribute
   public void saveAjax()
   {
      Profile current = getLoggedIn();
      ps.save(current);
   }

   @TransactionAttribute
   public void displayBootcampAjax()
   {
      Profile current = getLoggedIn();
      current.setShowBootcamp(true);
      ps.save(current);
   }

   @TransactionAttribute
   public void dismissBootcampAjax()
   {
      Profile current = getLoggedIn();
      current.setShowBootcamp(false);
      ps.save(current);
   }

   public void setEntityManager(final EntityManager em)
   {
      this.em = em;
      ps.setEntityManager(em);
   }

}
