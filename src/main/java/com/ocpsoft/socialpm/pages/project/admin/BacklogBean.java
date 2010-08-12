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

package com.ocpsoft.socialpm.pages.project.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import com.ocpsoft.exceptions.NoSuchObjectException;
import com.ocpsoft.socialpm.constants.UrlConstants;
import com.ocpsoft.socialpm.domain.project.Feature;
import com.ocpsoft.socialpm.domain.project.Milestone;
import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.domain.project.iteration.Iteration;
import com.ocpsoft.socialpm.domain.project.stories.Story;
import com.ocpsoft.socialpm.domain.project.stories.StoryStatus;
import com.ocpsoft.socialpm.pages.PageBean;
import com.ocpsoft.util.StringValidations;

@Named
@RequestScoped
public class BacklogBean extends PageBean
{
   private static final long serialVersionUID = -5891198692871884933L;

   private static final String ALL = "All";

   private Project project;

   private Integer storyNumber;
   private String descriptionFilter;
   private String statusFilter;
   private String featureFilter;
   private String iterationFilter;
   private String impededFilter;
   private String milestoneFilter;

   public Boolean getStoryNumberSet()
   {
      return storyNumber != null;
   }

   public Boolean getDescriptionFilterSet()
   {
      return (descriptionFilter != null) && !descriptionFilter.isEmpty();
   }

   public Boolean getStatusFilterSet()
   {
      return !StoryStatus.OPEN.getValue().equals(statusFilter) && (statusFilter != null);
   }

   public Boolean getFeatureFilterSet()
   {
      return !ALL.equals(featureFilter) && (featureFilter != null);
   }

   public Boolean getIterationFilterSet()
   {
      return !ALL.equals(iterationFilter) && (iterationFilter != null);
   }

   public Boolean getImpededFilterSet()
   {
      return !ALL.equals(impededFilter) && (impededFilter != null);
   }

   public Boolean getMilestoneFilterSet()
   {
      return !ALL.equals(milestoneFilter) && (milestoneFilter != null);
   }

   public Boolean getAnyFilterSet()
   {
      return getStoryNumberSet() || getDescriptionFilterSet() || getStatusFilterSet() || getFeatureFilterSet() || getIterationFilterSet() || getImpededFilterSet() || getMilestoneFilterSet();
   }

   private DataModel<Story> stories;
   private List<String> storyStatusOptions = new ArrayList<String>();
   private ArrayList<String> storyIterationOptions = new ArrayList<String>();
   private ArrayList<String> storyFeatureOptions = new ArrayList<String>();
   private ArrayList<String> storyImpededOptions = new ArrayList<String>();
   private ArrayList<String> storyMilestoneOptions = new ArrayList<String>();

   /*
    * URL Handlers
    */
   public String load()
   {
      try
      {
         project = currentProjectBean.getProject();
         stories = new ListDataModel<Story>(ps.getStories(project.getId()));

         initFilter();
         initMilestones();

         updateFilter();
      }
      catch (NoSuchObjectException e)
      {
         facesUtils.addErrorMessage("That project does not exist. Create it?");
         return facesUtils.beautify(UrlConstants.CREATE_PROJECT);
      }

      return null;
   }

   public String clearFilter()
   {
      storyNumber = null;
      featureFilter = null;
      descriptionFilter = null;
      impededFilter = null;
      iterationFilter = null;
      milestoneFilter = null;
      statusFilter = null;
      return facesUtils.beautify(UrlConstants.BACKLOG);
   }

   public String doFilter()
   {
      storyNumber = null;
      return facesUtils.beautify(UrlConstants.BACKLOG);
   }

   private void initFilter()
   {
      storyStatusOptions = new ArrayList<String>();
      storyStatusOptions.add(ALL);
      for (StoryStatus status : StoryStatus.values())
      {
         storyStatusOptions.add(status.getValue());
      }

      if ((statusFilter == null) || statusFilter.isEmpty())
      {
         statusFilter = StoryStatus.OPEN.getValue();
      }

      storyIterationOptions = new ArrayList<String>();
      storyIterationOptions.add(ALL);
      for (Iteration i : project.getIterations())
      {
         storyIterationOptions.add(i.getTitle());
      }

      storyFeatureOptions = new ArrayList<String>();
      storyFeatureOptions.add(ALL);
      for (Feature f : project.getFeatures())
      {
         storyFeatureOptions.add(f.getName());
      }

      storyImpededOptions = new ArrayList<String>();
      storyImpededOptions.add(ALL);
      storyImpededOptions.add("Yes");
      storyImpededOptions.add("No");
   }

   private void initMilestones()
   {
      storyMilestoneOptions = new ArrayList<String>();
      storyMilestoneOptions.add(ALL);

      for (Milestone milestone : project.getMilestones())
      {
         storyMilestoneOptions.add(milestone.getName());
      }

      for (Story s : stories)
      {
         if (s.getMilestone() == null)
         {
            // this is required or ValueChangeListeners won't fire.
            // TODO file bug with JSF impl?
            s.setMilestone(new Milestone("---", null));
         }
      }
   }

   public String updateFilter()
   {
      List<Story> result = new ArrayList<Story>();
      for (Story story : stories)
      {
         Story s = story;
         result.add(s);
      }

      if (storyNumber != null)
      {
         for (Iterator<Story> iterator = result.iterator(); iterator.hasNext();)
         {
            Story story = iterator.next();
            if (story.getNumber() != storyNumber)
            {
               iterator.remove();
            }
         }
      }
      else
      {
         if ((descriptionFilter != null) && !descriptionFilter.isEmpty())
         {
            List<String> chunks = Arrays.asList(descriptionFilter.split("\\s+"));
            for (String chunk : chunks)
            {
               for (Iterator<Story> iterator = result.iterator(); iterator.hasNext();)
               {
                  Story story = iterator.next();
                  if (!story.getDescription().toLowerCase().contains(chunk.toLowerCase()))
                  {
                     iterator.remove();
                  }
               }
            }
         }

         if (!ALL.equals(statusFilter) && storyStatusOptions.contains(statusFilter))
         {
            for (Iterator<Story> iterator = result.iterator(); iterator.hasNext();)
            {
               Story story = iterator.next();
               if (!statusFilter.equals(story.getStatus().getValue()))
               {
                  iterator.remove();
               }
            }
         }

         if (!ALL.equals(iterationFilter) && storyIterationOptions.contains(iterationFilter))
         {
            for (Iterator<Story> iterator = result.iterator(); iterator.hasNext();)
            {
               Story story = iterator.next();
               if (!iterationFilter.equals(story.getIteration().getTitle()))
               {
                  iterator.remove();
               }
            }
         }

         if (!ALL.equals(featureFilter) && storyFeatureOptions.contains(featureFilter))
         {
            for (Iterator<Story> iterator = result.iterator(); iterator.hasNext();)
            {
               Story story = iterator.next();
               if (!featureFilter.equals(story.getFeature().getName()))
               {
                  iterator.remove();
               }
            }
         }

         if (!ALL.equals(milestoneFilter) && storyMilestoneOptions.contains(milestoneFilter))
         {
            for (Iterator<Story> iterator = result.iterator(); iterator.hasNext();)
            {
               Story story = iterator.next();
               if (!milestoneFilter.equals(story.getMilestone().getName()))
               {
                  iterator.remove();
               }
            }
         }
      }

      stories = new ListDataModel<Story>(result);
      return null;
   }

   public void updateIteration(final ValueChangeEvent event)
   {
      if (stories.isRowAvailable())
      {
         String newIteration = (String) event.getNewValue();
         Story story = stories.getRowData();
         Iteration to = null;

         to = project.getIteration(newIteration);
         ss.changeIteration(story, to);

         updateMessage(event, story, "iteration");
      }
   }

   public void updateMilestone(final ValueChangeEvent event)
   {
      if (stories.isRowAvailable())
      {
         String newMilestone = (String) event.getNewValue();
         Story story = stories.getRowData();

         if (newMilestone == null)
         {
            ss.removeStoryMilestone(story);
         }
         else
         {
            Milestone milestone = project.getMilestone(newMilestone);
            ss.setStoryMilestone(story, milestone);
         }
         updateMessage(event, story, "milestone");
      }
   }

   public void updatePriority(final ValueChangeEvent event)
   {
      if (stories.isRowAvailable())
      {
         Story story = stories.getRowData();
         Integer newPriority = (Integer) event.getNewValue();

         ss.changePriority(story.getId(), newPriority);
         load();

         updateMessage(event, story, "priority");
      }
   }

   private void updateMessage(final ValueChangeEvent event, final Story story, final String type)
   {
      String from = "none";
      if (event.getOldValue() != null)
      {
         from = event.getOldValue().toString();
      }
      String to = "none";
      if (event.getNewValue() != null)
      {
         to = event.getNewValue().toString();
      }
      facesUtils.addInfoMessage("Story " + story.getNumber() + ": Updated " + type + " from '" + from + "' to '" + to + "'.");
   }

   /*
    * Validators
    */
   public void validatePriority(final FacesContext context, final UIComponent component, final Object value) throws ValidatorException
   {
      String input = value.toString();

      if (!StringValidations.isWholeNumber(input))
      {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Prioirty must be a whole number.", null);
         throw new ValidatorException(msg);
      }
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

   public DataModel<Story> getStories()
   {
      return stories;
   }

   public void setStories(final DataModel<Story> stories)
   {
      this.stories = stories;
   }

   public String getStatusFilter()
   {
      return statusFilter;
   }

   public void setStatusFilter(final String statusFilter)
   {
      this.statusFilter = statusFilter;
   }

   public List<String> getStoryStatusOptions()
   {
      return storyStatusOptions;
   }

   public void setStoryStatusOptions(final List<String> storyStatusOptions)
   {
      this.storyStatusOptions = storyStatusOptions;
   }

   public String getFeatureFilter()
   {
      return featureFilter;
   }

   public void setFeatureFilter(final String featureFilter)
   {
      this.featureFilter = featureFilter;
   }

   public String getIterationFilter()
   {
      return iterationFilter;
   }

   public void setIterationFilter(final String iterationFilter)
   {
      this.iterationFilter = iterationFilter;
   }

   public String getMilestoneFilter()
   {
      return milestoneFilter;
   }

   public void setMilestoneFilter(final String milestoneFilter)
   {
      this.milestoneFilter = milestoneFilter;
   }

   public ArrayList<String> getStoryIterationOptions()
   {
      return storyIterationOptions;
   }

   public void setStoryIterationOptions(final ArrayList<String> storyIterationOptions)
   {
      this.storyIterationOptions = storyIterationOptions;
   }

   public ArrayList<String> getStoryFeatureOptions()
   {
      return storyFeatureOptions;
   }

   public void setStoryFeatureOptions(final ArrayList<String> storyFeatureOptions)
   {
      this.storyFeatureOptions = storyFeatureOptions;
   }

   public ArrayList<String> getStoryImpededOptions()
   {
      return storyImpededOptions;
   }

   public void setStoryImpededOptions(final ArrayList<String> storyImpededOptions)
   {
      this.storyImpededOptions = storyImpededOptions;
   }

   public ArrayList<String> getStoryMilestoneOptions()
   {
      return storyMilestoneOptions;
   }

   public void setStoryMilestoneOptions(final ArrayList<String> storyMilestoneOptions)
   {
      this.storyMilestoneOptions = storyMilestoneOptions;
   }

   public String getImpededFilter()
   {
      return impededFilter;
   }

   public void setImpededFilter(final String impededFilter)
   {
      this.impededFilter = impededFilter;
   }

   public String getDescriptionFilter()
   {
      return descriptionFilter;
   }

   public void setDescriptionFilter(final String descriptionFilter)
   {
      this.descriptionFilter = descriptionFilter;
   }

   public Integer getStoryNumber()
   {
      return storyNumber;
   }

   public void setStoryNumber(final Integer storyNumber)
   {
      this.storyNumber = storyNumber;
   }
}
