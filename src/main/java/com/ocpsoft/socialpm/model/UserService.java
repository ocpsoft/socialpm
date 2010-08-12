/**
 * This file is part of SocialPM: Agile Project Management Tools (SocialPM) 
 *
 * Copyright (c)2010 Lincoln Baxter, III <lincoln@ocpsoft.com> (OcpSoft)
 * 
 * If you are developing and distributing open source applications under 
 * the GPL Licence, then you are free to use SocialPM under the GPL 
 * License:
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
 * For OEMs, ISVs, and VARs who distribute SocialPM with their products, 
 * host their product online, OcpSoft provides flexible OEM commercial 
 * Licences. 
 * 
 * Optionally, customers may choose a Commercial License. For additional 
 * details, contact OcpSoft (http://ocpsoft.com)
 */

package com.ocpsoft.socialpm.model;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import com.ocpsoft.data.PersistenceUtil;
import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.domain.project.stories.Story;
import com.ocpsoft.socialpm.domain.project.stories.TaskStatus;
import com.ocpsoft.socialpm.domain.user.Authority;
import com.ocpsoft.socialpm.domain.user.Role;
import com.ocpsoft.socialpm.domain.user.User;
import com.ocpsoft.socialpm.domain.user.UserPasswordMatchTester;
import com.ocpsoft.socialpm.domain.user.UserProfile;
import com.ocpsoft.socialpm.domain.user.UserSetCredentialsVisitor;
import com.ocpsoft.util.Assert;
import com.ocpsoft.util.StringValidations;

@Stateful
public class UserService extends PersistenceUtil implements Serializable
{
   private static final long serialVersionUID = 2988513095024795683L;

   @PersistenceContext(type = PersistenceContextType.EXTENDED)
   private EntityManager entityManager;

   @Override
   protected EntityManager getEntityManager()
   {
      return entityManager;
   }

   @Inject
   private UserPasswordMatchTester passwordTester;

   @Inject
   private UserSetCredentialsVisitor credentialsVisitor;

   public List<User> getUsers(final int limit, final int offset)
   {
      return findAll(User.class);
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

   public List<Project> getUserProjects(final long id)
   {
      return findByNamedQuery("membership.projectsByUserId", id);
   }

   public List<Story> getStoriesWithOpenTasksFor(final User user)
   {
      return findByNamedQuery("Story.withTasksFor", user, TaskStatus.DONE);
   }

   public User registerUser(final User user)
   {
      String username = user.getUsername();
      String password = user.getPassword();
      String email = user.getEmail();

      Assert.isTrue(StringValidations.isAlphanumeric(username) && StringValidations.minLength(4, username) && StringValidations.maxLength(15, username));
      Assert.isTrue(StringValidations.isPassword(password));
      Assert.isTrue(StringValidations.isEmailAddress(email));

      User newUser = new User();

      credentialsVisitor.setUsername(username);
      credentialsVisitor.setPassword(password);
      newUser.accept(credentialsVisitor);
      newUser.setEmail(email);
      Authority role = Authority.fromRole(newUser, Role.GUEST);
      newUser.getAuthorities().add(role);

      UserProfile profile = new UserProfile();

      create(newUser);

      profile.setUser(newUser);
      create(profile);

      return newUser;
   }

   public User enableUser(final User user)
   {
      user.setAccountExpired(false);
      user.setAccountLocked(false);
      user.setCredentialsExpired(false);
      user.setEnabled(true);
      save(user);
      return user;
   }

   public List<Authority> getAuthorities(final long id)
   {
      return findByNamedQuery("authority.byUserId", id);
   }

   public void addAuthority(final User user, final Authority authority)
   {
      Authority auth = new Authority();
      auth.setUser(user).setAuthority(authority.getAuthority());
      user.getAuthorities().add(auth);
      save(user);
   }

   public UserProfile getProfile(final long userId)
   {
      UserProfile p = findUniqueByNamedQuery("userProfile.byUserId", userId);
      return p;
   }

   public void saveProfile(final UserProfile profile)
   {
      save(profile);
   }

   public boolean passwordIs(final User user, final String password)
   {
      refresh(user);
      return passwordTester.passwordMatches(user, password);
   }

   public boolean updatePassword(User user, final String oldPassword, final String newPassword)
   {
      if (passwordTester.passwordMatches(user, oldPassword))
      {
         credentialsVisitor.setUsername(user.getUsername());
         credentialsVisitor.setPassword(newPassword);
         user.accept(credentialsVisitor);
         save(user);
         return true;
      }
      return false;
   }

}
