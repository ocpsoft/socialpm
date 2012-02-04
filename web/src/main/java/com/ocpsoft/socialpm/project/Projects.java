/**
 * This file is part of OCPsoft SocialPM: Agile Project Management Tools (SocialPM) 
 *
 * Copyright (c)2011 Lincoln Baxter, III <lincoln@ocpsoft.com> (OCPsoft)
 * Copyright (c)2011 OCPsoft.com (http://ocpsoft.com)
 * 
 * If you are developing and distributing open source applications under 
 * the GNU General Public License (GPL), then you are free to re-distribute SocialPM 
 * under the terms of the GPL, as follows:
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
 * For individuals or entities who wish to use SocialPM privately, or
 * internally, the following terms do not apply:
 *  
 * For OEMs, ISVs, and VARs who wish to distribute SocialPM with their 
 * products, or host their product online, OCPsoft provides flexible 
 * OEM commercial licenses.
 * 
 * Optionally, Customers may choose a Commercial License. For additional 
 * details, contact an OCPsoft representative (sales@ocpsoft.com)
 */
package com.ocpsoft.socialpm.project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.jboss.seam.international.status.Messages;

import com.ocpsoft.socialpm.cdi.Web;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.project.iteration.Iteration;
import com.ocpsoft.socialpm.model.project.story.Story;
import com.ocpsoft.socialpm.model.project.story.Task;
import com.ocpsoft.socialpm.model.user.Profile;
import com.ocpsoft.socialpm.security.Account;
import com.ocpsoft.socialpm.security.Profiles;
import com.ocpsoft.socialpm.services.project.ProjectService;
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
   private Account account;
   private Profiles profiles;
   private Stories stories;
   private ProjectService projectService;

   private Project current = new Project();

   public Projects()
   {}

   @Inject
   public Projects(final @Web EntityManager em, final ProjectService projectService, final Messages messages,
            final ParamsBean params,
            final Stories stories,
            final Profiles profiles,
            final Account account)
   {
      this.params = params;
      this.account = account;
      this.profiles = profiles;
      this.stories = stories;
      this.messages = messages;
      this.projectService = projectService;
      projectService.setEntityManager(em);
   }

   public String loadCurrent()
   {
      Project current = getCurrent();
      if (!current.isPersistent() && account.getLoggedIn().isPersistent())
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
      projectService.create(account.getLoggedIn(), current);
      return UrlConstants.PROJECT_VIEW + "&project=" + current.getSlug() + "&profile="
               + account.getLoggedIn().getUsername();
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

   public int getAssignedTaskCount(final Profile profile, final Project project)
   {
      // TODO this is super inefficient, figure this out
      int count = 0;
      for (Story s : project.getStories()) {
         count += stories.getAssignedTaskCount(profile, s);
      }
      return count;
   }

   public int getAssignedStoryCount(final Profile profile, final Project project)
   {
      int count = getAssignedStories(profile, project).size();
      return count;
   }

   public List<Story> getAssignedStories(final Profile profile, final Project project)
   {
      List<Story> result = new ArrayList<Story>();
      for (Story s : project.getStories()) {
         for (Task t : s.getTasks()) {
            if (profile.getUsername().equals(t.getAssignee().getUsername()))
            {
               result.add(s);
            }
         }
      }
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
   @Named("currentIteration")
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
