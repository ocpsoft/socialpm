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
import static org.junit.Assert.assertTrue;

import java.util.List;

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
import com.ocpsoft.socialpm.domain.feed.FeedEvent;
import com.ocpsoft.socialpm.domain.feed.UserLoggedIn;
import com.ocpsoft.socialpm.domain.feed.UserRegistered;
import com.ocpsoft.socialpm.domain.user.User;
import com.ocpsoft.socialpm.util.UtilRoot;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
@RunWith(Arquillian.class)
@Run(RunModeType.IN_CONTAINER)
public class FeedServiceTest
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
   @Inject
   private FeedService fs;

   MockUserGenerator gen = new MockUserGenerator();

   private User user;

   @Before
   public void incrementUser()
   {
      this.user = gen.generateUser();
   }

   @Before
   public void setup()
   {
      if (user == null)
      {
         user = new User();
         user.setUsername("fstestuser");
         user.setPassword("fstestpass");
         user.setEmail("fstest@email.com");
      }
   }

   @Test
   public void testCanPersistFeedEvents() throws Exception
   {
      us.registerUser(user);
      FeedEvent e = new FeedEvent(user);
      assertFalse(e.isPersistent());
      fs.addEvent(e);
      assertTrue(e.isPersistent());
   }

   @Test
   public void testCanRetrieveUsersEvents() throws Exception
   {
      us.registerUser(user);
      FeedEvent fe = new FeedEvent(user);
      fs.addEvent(fe);
      UserLoggedIn ule = new UserLoggedIn(user);
      fs.addEvent(ule);

      List<FeedEvent> list = fs.listByUser(user, 0, 0);
      assertEquals(3, list.size());
      for (FeedEvent feedEvent : list)
      {
         assertEquals(user, feedEvent.getUser());
      }
      assertTrue(list.contains(new UserRegistered(user)));
      assertTrue(list.contains(new FeedEvent(user)));
      assertTrue(list.contains(new UserLoggedIn(user)));
   }
}
