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
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import org.jboss.seam.international.status.Messages;

import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.model.project.ProjectService;
import com.ocpsoft.socialpm.security.CurrentProfile;
import com.ocpsoft.socialpm.web.constants.UrlConstants;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
@Named
@RequestScoped
public class Projects implements Serializable
{
   private static final long serialVersionUID = -5792291552146633049L;

   @Inject
   private Messages messages;

   @Inject
   private Instance<ProjectService> ps;

   private Project current = new Project();

   @Inject
   private CurrentProfile profile;

   public String loadCurrent()
   {
      Project current = getCurrent();
      if (!current.isPersistent() && profile.current().isPersistent())
      {
         messages.error("Oops! We couldn't find that project. Want to create one instead?");
         return "/pages/project/create";
      }
      else if (!current.isPersistent())
      {
         return "/pages/404";
      }
      return null;
   }

   public String create()
   {
      current.setOwner(profile.current());
      ps.get().create(current);
      return UrlConstants.PROJECT_VIEW + "&project=" + current.getSlug();
   }

   public List<Project> getAll()
   {
      return ps.get().findAll();
   }

   public Project getCurrent()
   {
      if ((current != null) && (current.getSlug() != null))
      {
         try
         {
            Project found = ps.get().findBySlug(current.getSlug());
            if (found != null)
            {
               current = found;
            }
         }
         catch (NoResultException e)
         {}
      }
      return current;
   }

   public void setCurrent(final Project current)
   {
      this.current = current;
   }

   public boolean isActive()
   {
      return this.current.getId() != null;
   }

}
