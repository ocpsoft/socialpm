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

package com.ocpsoft.socialpm.pages.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.inject.Inject;
import javax.inject.Named;

import com.ocpsoft.exceptions.NoSuchObjectException;
import com.ocpsoft.socialpm.constants.UrlConstants;
import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.domain.project.iteration.Iteration;
import com.ocpsoft.socialpm.domain.project.stories.Story;
import com.ocpsoft.socialpm.domain.project.stories.compare.StoryNumberComparator;
import com.ocpsoft.socialpm.domain.project.stories.compare.StoryPriorityComparator;
import com.ocpsoft.socialpm.domain.project.stories.compare.StoryStatusComparator;
import com.ocpsoft.socialpm.pages.PageBean;

@Named
@RequestScoped
public class ViewProjectBean extends PageBean
{
   private static final long serialVersionUID = 7289391351462282414L;

   private Project project;
   private Iteration iteration;
   private List<Story> frontShelfStories;

   @Inject
   private Conversation conversation;

   @Inject
   private RequestedProject requested;

   private ActionListener projectChangeListener = new ActionListener()
   {
      @Override
      public void processAction(final ActionEvent event) throws AbortProcessingException
      {
         ps.saveProjectDetails(project.getId(), project);
      }
   };

   public String load()
   {
      try
      {
         project = currentProjectBean.getProject();
         iteration = currentIterationBean.getIteration();

         try
         {
            if (currentIterationBean.getIteration().isDefault())
            {
               frontShelfStories = new ArrayList<Story>();
               for (Story s : ps.getStories(project.getId()))
               {
                  if (s.isOpen())
                  {
                     frontShelfStories.add(s);
                  }
               }
            }
            else
            {
               frontShelfStories = currentIterationBean.getFrontBurnerStories(iteration);
            }
            Collections.sort(frontShelfStories, new StoryNumberComparator());
            Collections.sort(frontShelfStories, new StoryPriorityComparator());
            Collections.sort(frontShelfStories, new StoryStatusComparator());
         }
         catch (NoSuchObjectException e)
         {
            facesUtils.addErrorMessage("No such iteration: " + params.getIterationName());
            return facesUtils.beautify(UrlConstants.VIEW_PROJECT);
         }
      }
      catch (NoSuchObjectException e)
      {
         facesUtils.addErrorMessage("Sorry, I couldn't find a project with that name. Would you like to create it?");
         requested.setName(params.getProjectName());
         conversation.begin("newproject");
         return facesUtils.beautify(UrlConstants.CREATE_PROJECT);
      }

      return null;
   }

   public String commitIteration()
   {
      facesUtils.addInfoMessage("Iteration Committed");
      ps.commitIteration(currentProjectBean.getProject().getId(), iteration.getId());

      return facesUtils.beautify(UrlConstants.VIEW_PROJECT);
   }

   /*
    * Getters and setters
    */
   public Project getProject()
   {
      return project;
   }

   public void setProject(final Project project)
   {
      this.project = project;
   }

   public List<Story> getFrontShelfStories()
   {
      return frontShelfStories;
   }

   public void setFrontShelfStories(final List<Story> frontShelfStories)
   {
      this.frontShelfStories = frontShelfStories;
   }

   public ActionListener getProjectChangeListener()
   {
      return projectChangeListener;
   }

   public void setProjectChangeListener(final ActionListener projectChangeListener)
   {
      this.projectChangeListener = projectChangeListener;
   }

   public Iteration getIteration()
   {
      return iteration;
   }

   public void setIteration(final Iteration iteration)
   {
      this.iteration = iteration;
   }
}
