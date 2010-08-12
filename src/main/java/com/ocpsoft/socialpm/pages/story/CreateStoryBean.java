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

package com.ocpsoft.socialpm.pages.story;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.validation.constraints.Size;

import org.jboss.seam.faces.context.conversation.Begin;
import org.jboss.seam.faces.context.conversation.End;

import com.ocpsoft.socialpm.constants.UrlConstants;
import com.ocpsoft.socialpm.domain.project.Points;
import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.domain.project.iteration.Iteration;
import com.ocpsoft.socialpm.domain.project.iteration.IterationStartDateComparator;
import com.ocpsoft.socialpm.domain.project.stories.Story;
import com.ocpsoft.socialpm.domain.project.stories.StoryBurner;
import com.ocpsoft.socialpm.domain.project.stories.ValidationCriteria;
import com.ocpsoft.socialpm.pages.PageBean;

@Named
@Stateful
@ConversationScoped
public class CreateStoryBean extends PageBean
{
   private static final long serialVersionUID = 7485201034459428239L;

   private Project project;

   private List<Iteration> iterations = new ArrayList<Iteration>();
   private List<Points> sizeOptions = Arrays.asList(Points.values());

   private boolean frontBurner;
   private String feature;
   private String iteration;

   private Story story = new Story();

   @Size(max = 255)
   private String validation;

   @Begin
   public void load()
   {
      project = currentProjectBean.getProject();
      iteration = project.getCurrentIteration().getTitle();
      iterations = new ArrayList<Iteration>(project.getAvailableIterations());
      Collections.sort(iterations, new IterationStartDateComparator());
   }

   /*
    * Action methods
    */
   public void addValidation()
   {
      ValidationCriteria v = new ValidationCriteria(validation);
      if (!validation.isEmpty() && !story.getValidations().contains(v))
      {
         story.getValidations().add(v);
         v.setStory(story);
      }
      validation = "";
   }

   public void removeValidation(final ValidationCriteria validation)
   {
      story.getValidations().remove(validation);
      validation.setStory(null);
   }

   @End
   public String doCreate()
   {
      addValidation();
      story.setFeature(project.getFeature(feature));
      if (frontBurner)
      {
         story.setBurner(StoryBurner.FRONT);
      }
      else
      {
         story.setBurner(StoryBurner.BACK);
      }

      ps.addStory(project, project.getIteration(iteration), story);
      params.setStoryNumber(story.getNumber());
      return facesUtils.beautify(UrlConstants.VIEW_STORY);
   }

   @End
   public String doCancel()
   {
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

   public List<Points> getSizeOptions()
   {
      return sizeOptions;
   }

   public void setSizeOptions(final List<Points> sizeOptions)
   {
      this.sizeOptions = sizeOptions;
   }

   public List<Iteration> getIterations()
   {
      return iterations;
   }

   public void setIterations(final List<Iteration> iterations)
   {
      this.iterations = iterations;
   }

   public Story getStory()
   {
      return story;
   }

   public void setStory(final Story story)
   {
      this.story = story;
   }

   public String getFeature()
   {
      return feature;
   }

   public void setFeature(final String feature)
   {
      this.feature = feature;
   }

   public String getIteration()
   {
      return iteration;
   }

   public void setIteration(final String iteration)
   {
      this.iteration = iteration;
   }

   public boolean isFrontBurner()
   {
      return frontBurner;
   }

   public void setFrontBurner(final boolean frontBurner)
   {
      this.frontBurner = frontBurner;
   }

   public String getValidation()
   {
      return validation;
   }

   public void setValidation(final String validation)
   {
      this.validation = validation;
   }
}
