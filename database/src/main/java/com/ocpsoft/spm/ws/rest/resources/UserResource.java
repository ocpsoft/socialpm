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

package com.ocpsoft.spm.ws.rest.resources;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.ocpsoft.socialpm.domain.PersistenceUtil;
import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.domain.project.stories.Story;
import com.ocpsoft.socialpm.domain.project.stories.TaskStatus;
import com.ocpsoft.socialpm.domain.user.User;
import com.ocpsoft.socialpm.domain.user.UserProfile;

@Path("/users")
@Stateful
public class UserResource extends PersistenceUtil
{
   @PersistenceContext(type = PersistenceContextType.EXTENDED)
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   @GET
   @Produces("application/xml")
   public List<User> getUsers(@QueryParam("limit") final int limit, @QueryParam("offset") final int offset)
   {
      return findAll(User.class);
   }

   @GET
   @Path("/{id}")
   @Produces("application/xml")
   public User getUserById(@PathParam("id") final long id)
   {
      return findById(User.class, id);
   }

   @GET
   @Path("/name/{name}")
   @Produces("application/xml")
   public User getUserByName(@PathParam("name") final String name)
   {
      return findUniqueByNamedQuery("user.byName", name);
   }

   @GET
   @Path("/email/{email}")
   @Produces("application/xml")
   public User getUserByEmail(@PathParam("email") final String email)
   {
      return findUniqueByNamedQuery("user.byEmail", email);
   }

   @GET
   @Path("/{id}/projects")
   @Produces("application/xml")
   public List<Project> getUserProjects(@PathParam("id") final long id, @QueryParam("limit") final int limit,
            @QueryParam("offset") final int offset)
   {
      return findByNamedQuery("membership.projectsByUser", getUserById(id));
   }

   @GET
   @Path("/{id}/stories")
   @Produces("application/xml")
   public List<Story> getStoriesWithOpenTasksFor(@PathParam("id") final long userId)
   {
      User user = findById(User.class, userId);
      return findByNamedQuery("Story.withTasksFor", user, TaskStatus.DONE);
   }

   @PUT
   @Path("/{id}/enable")
   @Produces("application/xml")
   public User enableUser(@PathParam("id") final long id)
   {
      User user = getUserById(id);
      save(user);
      return user;
   }

   @GET
   @Path("/{uid}/profile")
   @Produces("application/xml")
   public UserProfile getProfile(@PathParam("uid") final long userId)
   {
      UserProfile p = findUniqueByNamedQuery("userProfile.byUserId", userId);
      return p;
   }

   @PUT
   @Path("/{uid}/profile")
   @Consumes("application/xml")
   public void saveProfile(@PathParam("uid") final long id, final UserProfile profile)
   {
      UserProfile p = findUniqueByNamedQuery("userProfile.byUserId", id);

      p.setBio(profile.getBio());
      p.setEmailSecret(profile.isEmailSecret());
      p.setFullName(profile.getFullName());
      p.setEmployer(profile.getEmployer());
      p.setScreenName(profile.getScreenName());

      save(p);
   }

   @GET
   @Path("/{id}/password")
   @Consumes("text/plain")
   @Produces("text/plain")
   public Boolean passwordIs(final @PathParam("id") long id, @QueryParam("p") final String password)
   {
      User user = getUserById(id);
      // return passwordTester.passwordMatches(user, password);
      return false;
   }

   @PUT
   @Path("/{id}/password")
   @Consumes("text/plain")
   @Produces("text/plain")
   public Boolean updatePassword(final @PathParam("id") long id, @QueryParam("op") final String oldPassword,
            @QueryParam("p") final String newPassword)
   {
      // User user = getUserById(id);
      // if (passwordTester.passwordMatches(user, oldPassword))
      // {
      // credentialsVisitor.setUsername(user.getUsername());
      // credentialsVisitor.setPassword(newPassword);
      // user.accept(credentialsVisitor);
      // save(user);
      // return true;
      // }
      return false;
   }

}
