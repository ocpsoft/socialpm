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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.api.Run;
import org.jboss.arquillian.api.RunModeType;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ocpsoft.socialpm.DeploymentRoot;
import com.ocpsoft.socialpm.domain.feed.FeedEvent;
import com.ocpsoft.socialpm.domain.user.User;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
@RunWith(Arquillian.class)
@Run(RunModeType.IN_CONTAINER)
public class UserServiceTest
{

   @Deployment
   public static JavaArchive createTestArchive()
   {
      return ShrinkWrap.create(JavaArchive.class, "test.jar")
               .addPackages(true, DeploymentRoot.class.getPackage())
               .addManifestResource(new ByteArrayAsset("".getBytes()), ArchivePaths.create("beans.xml"))
               .addManifestResource("test-persistence.xml", ArchivePaths.create("persistence.xml"));
   }

   @EJB
   private UserService us;
   @EJB
   private FeedService fs;

   private static final String PASSWORD = "testpass";
   private static int count = 0;
   private User user;

   @Before
   public void incrementUser()
   {
      this.user = new User();
      user.setUsername("test" + count);
      user.setPassword(PASSWORD);
      user.setEmail("test" + count + "@ocpsoft.com");
      count++;
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
   }

   @Test
   public void testCanVerifyNewUser() throws Exception
   {
      String key = us.registerUser(user);
      assertFalse(user.isVerified());

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

   @Test
   public void testReEnablingUser() throws Exception
   {
      us.registerUser(user);
      us.disableAccount(user);
      assertFalse(user.isEnabled());

      us.enableAccount(user, PASSWORD);
      User u = us.getUserByName(user.getUsername());
      assertTrue(u.isEnabled());
   }

   @Test
   public void testPasswordIs() throws Exception
   {
      us.registerUser(user);
      assertTrue(us.passwordIs(user, PASSWORD));
      assertFalse(us.passwordIs(user, "nottherightpass"));
   }

   @Test
   public void testRegisterUserFiresFeedEvent() throws Exception
   {
      us.registerUser(user);
      List<FeedEvent> events = fs.listByUser(user, 0, 0);
      assertEquals(1, events.size());
      assertEquals(user, events.get(0).getUser());
   }
}
