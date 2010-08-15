/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
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

package com.ocpsoft.socialpm.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.api.Run;
import org.jboss.arquillian.api.RunModeType;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ocpsoft.socialpm.domain.DomainRoot;
import com.ocpsoft.socialpm.domain.user.User;
import com.ocpsoft.socialpm.util.UtilRoot;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
@RunWith(Arquillian.class)
@Run(RunModeType.IN_CONTAINER)
public class UserServiceTest
{
   @Deployment
   public static JavaArchive createTestArchive()
   {
      return ShrinkWrap.create(JavaArchive.class, "test.jar")
            .addPackages(true, DomainRoot.class.getPackage())
            .addPackages(true, UtilRoot.class.getPackage())
            .addPackages(true, ModelRoot.class.getPackage())
            .addManifestResource("test-beans.xml", ArchivePaths.create("beans.xml"))
            .addManifestResource("test-persistence.xml", ArchivePaths.create("persistence.xml"));
   }

   @Inject
   private UserService us;

   private static int count = 0;
   private User user;

   @Before
   public void incrementUser()
   {
      this.user = new User();
      user.setUsername("test" + count);
      user.setPassword("testpass");
      user.setEmail("test" + count + "@ocpsoft.com");
      count++;
      System.out.println("New username:" + user);
   }

   @Test
   public void testCanCreateNewUser() throws Exception
   {
      String key = us.registerUser(user);

      assertTrue(user.getProfile().isPersistent());
      assertTrue(user.isEnabled());
      assertFalse(user.isVerified());
      assertFalse(user.isAccountLocked());
      assertNotNull(user.getRegistrationKey());
      assertNotNull(key);

      User verified = us.verifyUser(key);
      assertTrue(verified.isVerified());
   }

   @Test
   public void testDisablingUser() throws Exception
   {
      us.registerUser(user);
      us.disableAccount(user);

      User u = us.getUserByName(user.getUsername());
      assertFalse(u.isEnabled());
   }
}
