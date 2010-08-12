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

import javax.enterprise.context.RequestScoped;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import com.ocpsoft.exceptions.NoSuchObjectException;
import com.ocpsoft.socialpm.constants.UrlConstants;
import com.ocpsoft.socialpm.converter.ConverterChain;
import com.ocpsoft.socialpm.converter.HtmlEscapeConverter;
import com.ocpsoft.socialpm.converter.StoryLinkConverter;
import com.ocpsoft.socialpm.converter.TextToHtmlFormattingConverter;
import com.ocpsoft.socialpm.converter.WebLinkConverter;
import com.ocpsoft.socialpm.domain.project.stories.Story;
import com.ocpsoft.socialpm.domain.project.stories.StoryComment;
import com.ocpsoft.socialpm.domain.project.stories.Task;
import com.ocpsoft.socialpm.domain.user.User;
import com.ocpsoft.socialpm.pages.PageBean;

@Named
@RequestScoped
public class ViewStoryBean extends PageBean
{
   private static final long serialVersionUID = 3430459190558538455L;
   private static Converter linkConverter = new ConverterChain(new HtmlEscapeConverter(), new WebLinkConverter(),
            new StoryLinkConverter(), new TextToHtmlFormattingConverter());

   @Inject
   EditStoryBean esb;

   private Story story;
   private Task currentTask = new Task();
   private StoryComment comment = new StoryComment();

   public String load()
   {
      try
      {
         esb.load();
         currentTask = new Task();
         currentTask.setAssignee(new User());
         story = currentStoryBean.getStory();
      }
      catch (NoSuchObjectException e)
      {
         facesUtils.addErrorMessage("No story with ID: " + params.getStoryNumber());
         return facesUtils.beautify(UrlConstants.VIEW_PROJECT);
      }

      return null;
   }

   /*
    * Getters & Setters
    */
   public Story getStory()
   {
      return story;
   }

   public void setStory(final Story story)
   {
      this.story = story;
   }

   public Task getCurrentTask()
   {
      return currentTask;
   }

   public void setCurrentTask(final Task currentTask)
   {
      this.currentTask = currentTask;
   }

   public Converter getLinkConverter()
   {
      return linkConverter;
   }

   public StoryComment getComment()
   {
      return comment;
   }

   public void setComment(final StoryComment comment)
   {
      this.comment = comment;
   }

}