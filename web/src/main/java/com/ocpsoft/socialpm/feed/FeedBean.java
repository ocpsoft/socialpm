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
package com.ocpsoft.socialpm.feed;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.ocpsoft.pretty.PrettyContext;
import com.ocpsoft.pretty.faces.util.PrettyURLBuilder;
import com.ocpsoft.pretty.time.PrettyTime;
import com.ocpsoft.socialpm.domain.feed.FeedEvent;
import com.ocpsoft.socialpm.domain.feed.UserLoggedIn;
import com.ocpsoft.socialpm.domain.feed.UserRegistered;
import com.ocpsoft.socialpm.domain.user.User;
import com.ocpsoft.socialpm.model.FeedService;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
@Named
@RequestScoped
public class FeedBean
{
   @Inject
   private FeedService fs;

   public List<String> getStreamFor(final User u)
   {
      List<String> result = new ArrayList<String>();

      for (FeedEvent e : fs.list(10, 0))
      {
         PrettyContext context = PrettyContext.getCurrentInstance();
         String contextRoot = context.getContextPath();
         String temp = "<a href=\"" + contextRoot + new PrettyURLBuilder().build(
                  context.
                           getConfig().getMappingById("userProfile"),
                           e.getUser().getUsername()) + "\">"
                           + e.getUser().getUsername() + "</a> ";

         if (e instanceof UserRegistered)
         {
            temp += "signed up ";
         }
         else if (e instanceof UserLoggedIn)
         {
            temp += "logged in ";
         }
         else if (e instanceof FeedEvent)
         {
            temp += "did something eventful ";
         }

         temp += "about " + new PrettyTime().format(e.getCreatedOn()) + ".";

         result.add(temp);
      }

      return result;
   }
}
