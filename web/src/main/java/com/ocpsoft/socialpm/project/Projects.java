/*
 * Copyright 2011 <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ocpsoft.socialpm.project;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.jboss.seam.international.status.Messages;

import com.ocpsoft.socialpm.cdi.LoggedIn;
import com.ocpsoft.socialpm.cdi.Web;
import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.domain.project.iteration.Iteration;
import com.ocpsoft.socialpm.domain.user.Profile;
import com.ocpsoft.socialpm.model.project.ProjectService;
import com.ocpsoft.socialpm.security.Profiles;
import com.ocpsoft.socialpm.web.ParamsBean;
import com.ocpsoft.socialpm.web.constants.UrlConstants;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
@Named
@ConversationScoped
public class Projects implements Serializable
{
   private static final long serialVersionUID = -5792291552146633049L;

   private Messages messages;
   private ParamsBean params;
   private Profiles profiles;
   private ProjectService projectService;

   private Project current = new Project();

   public Projects()
   {}

   @Inject
   public Projects(final @Web EntityManager em, final ProjectService projectService, final Messages messages,
            final ParamsBean params,
            final Profiles profiles)
   {
      this.params = params;
      this.profiles = profiles;
      this.messages = messages;
      this.projectService = projectService;
      projectService.setEntityManager(em);
   }

   public String loadCurrent()
   {
      Project current = getCurrent();
      if (!current.isPersistent() && profiles.getLoggedIn().isPersistent())
      {
         messages.error("Oops! We couldn't find that project. Want to create one instead?");
         return "/pages/project/create";
      }
      else if (!current.isPersistent())
      {
         messages.error("Oops! We couldn't find the project you asked for.");
         return "/pages/404";
      }
      return null;
   }

   public String create()
   {
      projectService.create(profiles.getLoggedIn(), current);
      return UrlConstants.PROJECT_VIEW + "&project=" + current.getSlug() + "&profile="
               + profiles.getLoggedIn().getUsername();
   }

   public long getCount()
   {
      return projectService.getProjectCount();
   }

   public List<Project> getAll()
   {
      List<Project> result = projectService.findAll();
      return result;
   }

   @Produces
   @Named("project")
   @RequestScoped
   public Project getCurrent()
   {
      if ((current != null) && (params.getProjectSlug() != null))
      {
         try
         {
            Profile profile = profiles.getCurrent();
            if (profile.isPersistent())
            {
               Project found = projectService.findByProfileAndSlug(profile, params.getProjectSlug());
               if (found != null)
               {
                  current = found;
               }
            }
         }
         catch (NoResultException e)
         {}
      }
      return current;
   }

   @Produces
   @LoggedIn
   @Named("iteration")
   public Iteration getCurrentIteration()
   {
      Iteration iteration = getCurrent().getCurrentIteration();
      return iteration;
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
