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

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import com.ocpsoft.socialpm.domain.NoSuchObjectException;
import com.ocpsoft.socialpm.domain.PersistenceUtil;
import com.ocpsoft.socialpm.domain.feed.UserRegistered;
import com.ocpsoft.socialpm.domain.user.User;
import com.ocpsoft.socialpm.domain.user.UserProfile;
import com.ocpsoft.socialpm.util.Assert;
import com.ocpsoft.socialpm.util.RandomGenerator;
import com.ocpsoft.socialpm.util.StringValidations;

@Stateful
@ConversationScoped
public class UserService extends PersistenceUtil implements Serializable
{
   private static final long serialVersionUID = 2988513095024795683L;

   @PersistenceContext(type = PersistenceContextType.EXTENDED)
   private EntityManager em;

   @Inject
   private FeedService fs;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public List<User> getUsers(final int limit, final int offset)
   {
      return findAll(User.class);
   }

   public void save(final User user)
   {
      super.save(user);
      em.flush();
   }

   public User getUserById(final long id) throws NoSuchObjectException
   {
      return findById(User.class, id);
   }

   public User getUserByName(final String name) throws NoSuchObjectException
   {
      return findUniqueByNamedQuery("user.byName", name);
   }

   public User getUserByEmail(final String email) throws NoSuchObjectException
   {
      return findUniqueByNamedQuery("user.byEmail", email);
   }

   /**
    * Take a user object with populated username and plaintext password. Register that user, and return the pending
    * registration key with which the user must be verified.
    */
   public String registerUser(final User user, final String username, final String password)
   {
      Assert.isTrue(StringValidations.isAlphanumeric(username)
               && StringValidations.minLength(4, username)
               && StringValidations.maxLength(15, username));
      Assert.isTrue(StringValidations.isPassword(username));
      Assert.isTrue(StringValidations.isEmailAddress(user.getEmail()));

      UserProfile profile = new UserProfile();
      user.setProfile(profile);
      profile.setUser(user);

      user.setRegistrationKey(RandomGenerator.makeString());

      create(user);

      fs.addEvent(new UserRegistered(user));
      return user.getRegistrationKey();
   }

}
