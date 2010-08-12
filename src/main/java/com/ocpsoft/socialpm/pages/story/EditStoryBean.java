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
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import com.ocpsoft.socialpm.constants.UrlConstants;
import com.ocpsoft.socialpm.domain.project.Feature;
import com.ocpsoft.socialpm.domain.project.Points;
import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.domain.project.iteration.Iteration;
import com.ocpsoft.socialpm.domain.project.stories.Story;
import com.ocpsoft.socialpm.domain.project.stories.StoryBurner;
import com.ocpsoft.socialpm.pages.PageBean;
import com.ocpsoft.socialpm.pages.params.Current;

@Named
@RequestScoped
public class EditStoryBean extends PageBean
{
   private static final long serialVersionUID = 9112698728022572745L;

   private List<Iteration> iterations = new ArrayList<Iteration>();
   private List<Feature> features = new ArrayList<Feature>();
   private List<Points> pointsOptions = Arrays.asList(Points.values());
   private List<StoryBurner> burners = Arrays.asList(StoryBurner.values());

   @Inject
   @Current
   private Project project;

   @Inject
   @Current
   private Story story;

   private String iteration;
   private String feature;

   public void load()
   {
      feature = story.getFeature().getName();
      iteration = story.getIteration().getTitle();

      features = new ArrayList<Feature>(project.getFeatures());
      iterations = new ArrayList<Iteration>(project.getAvailableIterations());
   }

   /*
    * Action methods
    */
   public String doSave()
   {
      story.setStoryPoints(story.getStoryPoints());
      story.setFeature(project.getFeature(feature));

      ss.saveStoryDetails(story);
      if (story.getMilestone().getId() == -1)
      {
         ss.removeStoryMilestone(story);
      }
      else
      {
         ss.setStoryMilestone(story, story.getMilestone());
      }
      ss.changeIteration(story, story.getIteration());

      return facesUtils.beautify(UrlConstants.VIEW_STORY);
   }

   public String doCancel()
   {
      return facesUtils.beautify(UrlConstants.VIEW_STORY);
   }

   private Converter burnerConverter = new Converter()

   {
      @Override
      public Object getAsObject(final FacesContext context, final UIComponent component, final String value)
      {
         if (value == "true")
         {
            return StoryBurner.FRONT;
         }
         return StoryBurner.BACK;
      }

      @Override
      public String getAsString(final FacesContext context, final UIComponent component, final Object value)
      {
         if (StoryBurner.FRONT.equals(value))
         {
            return "true";
         }
         return "false";
      }
   };

   /*
    * Getters and setters
    */
   public List<Feature> getFeatures()
   {
      return features;
   }

   public void setFeatures(final List<Feature> features)
   {
      this.features = features;
   }

   public List<Points> getPointsOptions()
   {
      return pointsOptions;
   }

   public void setPointsOptions(final List<Points> sizeOptions)
   {
      pointsOptions = sizeOptions;
   }

   public Project getProject()
   {
      return project;
   }

   public void setProject(final Project project)
   {
      this.project = project;
   }

   public Story getStory()
   {
      return story;
   }

   public void setStory(final Story story)
   {
      this.story = story;
   }

   public List<Iteration> getIterations()
   {
      return iterations;
   }

   public void setIterations(final List<Iteration> iterations)
   {
      this.iterations = iterations;
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

   public Converter getBurnerConverter()
   {
      return burnerConverter;
   }

   public void setBurnerConverter(final Converter burnerConverter)
   {
      this.burnerConverter = burnerConverter;
   }

   public List<StoryBurner> getBurners()
   {
      return burners;
   }

   public void setBurners(final List<StoryBurner> burners)
   {
      this.burners = burners;
   }
}