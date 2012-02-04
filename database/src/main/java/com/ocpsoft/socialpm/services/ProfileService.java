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

package com.ocpsoft.socialpm.services;

import java.util.List;
import java.util.Random;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.ocpsoft.socialpm.model.user.Profile;
import com.ocpsoft.socialpm.util.Strings;

public class ProfileService extends PersistenceUtil
{
   private static final long serialVersionUID = 2988513095024795683L;

   @Inject
   private FeedService fs;

   @Override
   public void setEntityManager(EntityManager em)
   {
      fs.setEntityManager(em);
      this.em = em;
   }

   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public void create(final Profile profile)
   {
      profile.setShowBootcamp(true);
      super.create(profile);
   }

   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public void save(final Profile user)
   {
      super.save(user);
      em.flush();
   }

   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public String getRandomUsername(String seed)
   {
      String username = Strings.canonicalize(seed);
      while (!isUsernameAvailable(username))
      {
         username += new Random(System.currentTimeMillis()).nextInt() % 100;
      }
      return username;
   }

   public long getProfileCount()
   {
      return count(Profile.class);
   }

   public List<Profile> getProfiles(final int limit, final int offset)
   {
      return findAll(Profile.class);
   }

   public Profile getProfileByUsername(final String username) throws NoResultException
   {
      TypedQuery<Profile> query = em.createQuery("SELECT p FROM Profile p WHERE p.username = :username", Profile.class);
      query.setParameter("username", username);

      Profile result = query.getSingleResult();
      return result;
   }

   public Profile getProfileByEmail(final String email) throws NoResultException
   {
      TypedQuery<Profile> query = em.createQuery("SELECT p FROM Profile p WHERE p.email = :email", Profile.class);
      query.setParameter("email", email);

      Profile result = query.getSingleResult();
      return result;
   }

   public boolean hasProfileByIdentityKey(final String key) throws NoResultException
   {
      try {
         getProfileByIdentityKey(key);
         return true;
      }
      catch (NoResultException e) {
         return false;
      }
   }

   public Profile getProfileByIdentityKey(final String key) throws NoResultException
   {
      TypedQuery<Profile> query = em.createQuery(
               "SELECT p FROM Profile p JOIN p.identityKeys k WHERE k = :identityKey", Profile.class);
      query.setParameter("identityKey", key);

      Profile result = query.getSingleResult();
      return result;
   }

   public Profile getProfileById(final Long id)
   {
      return findById(Profile.class, id);
   }

   public boolean isUsernameAvailable(String username)
   {
      try {
         getProfileByUsername(username);
         return false;
      }
      catch (NoResultException e) {
         return true;
      }
   }

   public boolean isEmailAddressAvailable(String email)
   {
      try {
         getProfileByEmail(email);
         return false;
      }
      catch (NoResultException e) {
         return true;
      }
   }

}
