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

package com.ocpsoft.socialpm.pages.admin;

import javax.ejb.Stateful;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.faces.event.PhaseEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.faces.event.qualifier.Before;
import org.jboss.seam.faces.event.qualifier.RestoreView;
import org.jboss.seam.international.status.Messages;

import com.ocpsoft.exceptions.NoSuchObjectException;
import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.domain.user.Authority;
import com.ocpsoft.socialpm.domain.user.User;
import com.ocpsoft.socialpm.model.ProjectService;
import com.ocpsoft.socialpm.model.UserService;
import com.ocpsoft.socialpm.security.Role;

@Named
@Stateful
@ApplicationScoped
public class AdminBean
{
   private static final long serialVersionUID = -3048415131574106297L;

   private boolean initialized = false;

   @Inject
   private ProjectService ps;

   @Inject
   private UserService us;

   @Inject
   private Messages messages;

   private final String path;

   public AdminBean()
   {
      String userHome = "user.home";
      path = System.getProperty(userHome);
   }

   public String getPath()
   {
      return path;
   }

   public boolean isDatabaseInitialized()
   {
      if (!initialized)
      {
         try
         {
            us.getUserByName("guest");
            initialized = true;
         }
         catch (NoSuchObjectException e)
         {
         }
      }
      return initialized;
   }

   public void setupApp(@Observes @Before @RestoreView PhaseEvent event)
   {
      if (!isDatabaseInitialized())
      {
         User guest = new User();
         guest.setUsername("guest");
         guest.setPassword("#io349(&$$@");
         guest.setEmail("guest@ocpsoft.com");
         guest = us.registerUser(guest);
         us.addAuthority(guest, new Authority(Role.GUEST.value()));
         us.enableUser(guest);

         User lb = new User();
         lb.setUsername("lincoln");
         lb.setPassword("lincoln");
         lb.setEmail("lincoln@ocpsoft.com");
         lb = us.registerUser(lb);
         us.addAuthority(lb, new Authority(Role.ADMIN.value()));
         us.enableUser(lb);

         Project project = new Project();
         project.setName("Demo Project");
         project.setVision("Be visionary");
         ps.createProject(lb, project);

         // Story s = new Story();
         // s.setDescription("This is a sample story.");
         // s.setFeature(project.getFeature("Unclassified"));
         // s.setStoryPoints(Points.S8);
         // s.setBurner(StoryBurner.FRONT);
         // ps.addStory(project, project.getDefaultIteration(), s);
         // ss.addValidation(s, new
         // ValidationCriteria("Sample validation criteria"));

         User user = new User();
         user.setEmail("demo@ocpsoft.com");
         user.setUsername("demo");
         user.setPassword("demo");
         user = us.registerUser(user);
         us.addAuthority(user, new Authority(Role.MEMBER.value()));
         us.enableUser(user);
         messages.info("Database Successfully Initialized.");
      }
   }
}
