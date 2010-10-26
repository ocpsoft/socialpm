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
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import com.ocpsoft.socialpm.domain.PersistenceUtil;
import com.ocpsoft.socialpm.domain.feed.UserRegistered;
import com.ocpsoft.socialpm.domain.user.Authority;
import com.ocpsoft.socialpm.domain.user.User;
import com.ocpsoft.socialpm.domain.user.UserPasswordMatchTester;
import com.ocpsoft.socialpm.domain.user.UserProfile;
import com.ocpsoft.socialpm.domain.user.UserSetCredentialsVisitor;
import com.ocpsoft.socialpm.domain.user.auth.Role;
import com.ocpsoft.socialpm.domain.user.auth.UserEnabled;
import com.ocpsoft.socialpm.domain.user.auth.UserRole;
import com.ocpsoft.socialpm.domain.user.auth.UserVerified;
import com.ocpsoft.socialpm.util.Assert;
import com.ocpsoft.socialpm.util.RandomGenerator;
import com.ocpsoft.socialpm.util.StringValidations;

@Stateful
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
   }

   public User getUserById(final long id)
   {
      return findById(User.class, id);
   }

   public User getUserByName(final String name)
   {
      return findUniqueByNamedQuery("user.byName", name);
   }

   public User getUserByEmail(final String email)
   {
      return findUniqueByNamedQuery("user.byEmail", email);
   }

   /**
    * Take a user object with populated username and plaintext password. Register that user, and return the pending
    * registration key with which the user must be verified.
    */
   public String registerUser(final User user)
   {
      Assert.isTrue(StringValidations.isAlphanumeric(user.getUsername())
               && StringValidations.minLength(4, user.getUsername())
               && StringValidations.maxLength(15, user.getUsername()));
      Assert.isTrue(StringValidations.isPassword(user.getPassword()));
      Assert.isTrue(StringValidations.isEmailAddress(user.getEmail()));

      user.accept(new UserSetCredentialsVisitor(user.getUsername(), user.getPassword()));
      user.getAuthorities().add(new UserRole(Role.GUEST));
      user.getAuthorities().add(new UserEnabled());

      UserProfile profile = new UserProfile();
      user.setProfile(profile);
      profile.setUser(user);

      user.setRegistrationKey(RandomGenerator.makeString());

      create(user);

      fs.addEvent(new UserRegistered(user));
      return user.getRegistrationKey();
   }

   public void enableAccount(final User user, final String password)
   {
      if (!user.isEnabled() && passwordIs(user, password))
      {
         user.getAuthorities().add(new UserEnabled());
      }
      save(user);
   }

   public User verifyUser(final String key)
   {
      User user = findUniqueByNamedQuery("user.byRegKey", key);
      user.getAuthorities().add(new UserVerified());
      save(user);

      return user;
   }

   public void disableAccount(final User user)
   {
      user.getAuthorities().remove(new UserEnabled());
      save(user);
   }

   public void addAuthority(final User user, final Authority authority)
   {
      user.getAuthorities().add(authority);
      save(user);
   }

   public boolean passwordIs(final User user, final String password)
   {
      refresh(user);
      return new UserPasswordMatchTester().passwordMatches(user, password);
   }

   public boolean updatePassword(final User user, final String oldPassword, final String newPassword)
   {
      if (new UserPasswordMatchTester().passwordMatches(user, oldPassword))
      {
         user.accept(new UserSetCredentialsVisitor(user.getUsername(), newPassword));
         save(user);
         return true;
      }
      return false;
   }

}
