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
import javax.inject.Inject;
import javax.inject.Named;

import com.ocpsoft.socialpm.constants.UrlConstants;
import com.ocpsoft.socialpm.domain.project.stories.Story;
import com.ocpsoft.socialpm.domain.project.stories.StoryComment;
import com.ocpsoft.socialpm.domain.project.stories.StoryStatus;
import com.ocpsoft.socialpm.domain.project.stories.Task;
import com.ocpsoft.socialpm.domain.project.stories.TaskStatus;
import com.ocpsoft.socialpm.domain.project.stories.ValidationCriteria;
import com.ocpsoft.socialpm.domain.user.User;
import com.ocpsoft.socialpm.pages.PageBean;
import com.ocpsoft.socialpm.security.LoggedIn;

@Named
@RequestScoped
public class StoryController extends PageBean
{
   private static final long serialVersionUID = -5869982507765592161L;

   @Inject
   @LoggedIn
   User user;

   public String save(final Story story)
   {
      ss.save(story);
      return facesUtils.beautify(UrlConstants.REFRESH);
   }

   public String reopen(final Story story)
   {
      story.setStatus(StoryStatus.OPEN);
      ss.save(story);
      return facesUtils.beautify(UrlConstants.REFRESH);
   }

   public String close(final Story story)
   {
      story.setStatus(StoryStatus.CLOSED);
      ss.save(story);
      return facesUtils.beautify(UrlConstants.REFRESH);
   }

   public String addTask(final Story story)
   {
      Task t = new Task();
      t.setInitialHours(0);
      t.setText("New task " + (story.getTasks().size() + 1) + " (click to edit)");
      t.setStatus(TaskStatus.NOT_STARTED);
      ss.addTask(story, t);
      return null;
   }

   public String removeTask(final Task t)
   {
      ss.deleteTask(t);
      return null;
   }

   public String addValidation(final Story s)
   {
      ValidationCriteria validation = new ValidationCriteria();
      validation.setText("New validation criteria " + (s.getValidations().size() + 1) + " (click to edit)");
      ss.addValidation(s, validation);
      return null;
   }

   public String removeValidation(final ValidationCriteria v)
   {
      ss.removeValidation(v);
      return null;
   }

   public String addComment(final Story story, final StoryComment comment)
   {
      comment.setAuthor(user);
      ss.addComment(story, comment);
      return null;
   }

   public String removeComment(final StoryComment comment)
   {
      ss.removeComment(comment);
      return null;
   }

}
