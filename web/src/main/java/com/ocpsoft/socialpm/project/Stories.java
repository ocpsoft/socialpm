/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc., and individual contributors
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
package com.ocpsoft.socialpm.project;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.jboss.seam.international.status.Messages;

import com.ocpsoft.socialpm.domain.project.Story;
import com.ocpsoft.socialpm.domain.project.StoryService;
import com.ocpsoft.socialpm.web.constants.UrlConstants;

/**
 * @author <a href="mailto:bleathem@gmail.com">Brian Leathem</a>
 * 
 */
@Named
@ConversationScoped
public class Stories implements Serializable
{
   private static final long serialVersionUID = -6828711689148386870L;

   @Inject
   private Messages messages;

   @Inject
   private Projects projects;

   @Inject
   private StoryService ss;

   @Inject
   private transient Logger log;

   private Story current = new Story();

   public String loadCurrent()
   {
      if ((current != null) && (current.getId() != null))
      {
         current = ss.findById(current.getId());
         return null;
      }
      else
      {
         messages.error("Oops! We couldn't find that Story.");
         return UrlConstants.HOME;
      }
   }

   public String create()
   {
      log.info("Creating a Story");
      projects.getCurrent().getStories().add(current);
      current.setProject(projects.getCurrent());
      ss.create(current);
      return UrlConstants.PROJECT_VIEW + "&project=" + projects.getCurrent().getName();
   }

   public Story getCurrent()
   {
      return current;
   }

   public void setCurrent(final Story current)
   {
      this.current = current;
   }
}
