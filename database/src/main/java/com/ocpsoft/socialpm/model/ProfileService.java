/**
 * This file is part of SocialPM: Agile Project Management Tools (SocialPM)
 * Copyright (c)2010 Lincoln Baxter, III <lincoln@ocpsoft.com> (OcpSoft) If you
 * are developing and distributing open source applications under the GPL
 * Licence, then you are free to use SocialPM under the GPL License: SocialPM is
 * free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * SocialPM is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with
 * SocialPM. If not, see <http://www.gnu.org/licenses/>. For OEMs, ISVs, and
 * VARs who distribute SocialPM with their products, host their product online,
 * OcpSoft provides flexible OEM commercial Licences. Optionally, customers may
 * choose a Commercial License. For additional details, contact OcpSoft
 * (http://ocpsoft.com)
 */

package com.ocpsoft.socialpm.model;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.jboss.seam.transaction.TransactionPropagation;
import org.jboss.seam.transaction.Transactional;

import com.ocpsoft.socialpm.domain.PersistenceUtil;
import com.ocpsoft.socialpm.domain.user.Profile;

@TransactionAttribute
@Transactional(TransactionPropagation.REQUIRED)
public class ProfileService extends PersistenceUtil
{
   private static final long serialVersionUID = 2988513095024795683L;

   @Inject
   private EntityManager em;

   @Inject
   private FeedService fs;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public long getProfileCount()
   {
      return count(Profile.class);
   }

   public List<Profile> getProfiles(final int limit, final int offset)
   {
      return findAll(Profile.class);
   }

   public void create(final Profile profile)
   {
      profile.setShowBootcamp(true);
      super.create(profile);
   }

   public void save(final Profile user)
   {
      super.save(user);
      em.flush();
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
               "SELECT p FROM Profile p JOIN p.keys k WHERE k = :key", Profile.class);
      query.setParameter("key", key);

      Profile result = query.getSingleResult();
      return result;
   }

   public Profile getProfileById(final Long id)
   {
      return findById(Profile.class, id);
   }

}
