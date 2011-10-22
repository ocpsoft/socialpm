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
import java.util.Date;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.ocpsoft.socialpm.domain.PersistenceUtil;
import com.ocpsoft.socialpm.domain.feed.FeedEvent;
import com.ocpsoft.socialpm.domain.user.Profile;

@Path("/feeds")
@Stateful
public class FeedService extends PersistenceUtil implements Serializable
{
   private static final long serialVersionUID = 5716926734835352145L;

   @PersistenceContext(type = PersistenceContextType.EXTENDED)
   private EntityManager entityManager;

   @Override
   protected EntityManager getEntityManager()
   {
      return entityManager;
   }

   public void addEvent(@Observes final FeedEvent event)
   {
      event.setCreatedOn(new Date());
      create(event);
   }

   @GET
   @Produces("application/xml")
   public List<FeedEvent> list(@QueryParam("limit") final int limit, @QueryParam("offset") final int offset)
   {
      TypedQuery<FeedEvent> query = entityManager.createNamedQuery("feedEvent.all", FeedEvent.class)
               .setFirstResult(offset);

      if (limit > 0)
      {
         query.setMaxResults(limit);
      }

      return query.getResultList();
   }

   @GET
   @Path("/users/{id}")
   @Produces("application/xml")
   public List<FeedEvent> listByUser(@PathParam("id") final Long id, @QueryParam("limit") final int limit,
            @QueryParam("offset") final int offset)
   {
      Profile user = findById(Profile.class, id);
      TypedQuery<FeedEvent> query = entityManager.createNamedQuery("feedEvent.byUser", FeedEvent.class)
               .setParameter("user", user)
               .setFirstResult(offset);

      if (limit > 0)
      {
         query.setMaxResults(limit);
      }

      return query.getResultList();
   }

   @GET
   @Path("/projects/{id}")
   @Produces("application/xml")
   public List<FeedEvent> listByProject(@PathParam("id") final Long id, @QueryParam("limit") final int limit,
            @QueryParam("offset") final int offset)
   {
      return findByNamedQuery("feedItem.byProject", id);
   }
}
