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

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Inject;
import javax.inject.Named;

import com.ocpsoft.socialpm.constants.UrlConstants;
import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.domain.project.stories.Story;
import com.ocpsoft.socialpm.domain.project.stories.StoryBurner;
import com.ocpsoft.socialpm.pages.PageBean;
import com.ocpsoft.socialpm.pages.params.Current;

@Named
@RequestScoped
public class BackBurnerBean extends PageBean
{
   private static final long serialVersionUID = -1561950994433987193L;

   @Inject
   @Current
   private Project project;

   private List<Story> backShelfStories;

   private HtmlDataTable storyTable;

   private static final String STORY_PARAM = "storyId";

   public String load()
   {
      backShelfStories = project.getCurrentIteration().getBackShelfStories();
      return null;
   }

   public String moveToFrontburner()
   {
      Story story = (Story) storyTable.getRowData();
      story.setBurner(StoryBurner.FRONT);

      ss.saveStoryDetails(story);
      backShelfStories = project.getCurrentIteration().getBackShelfStories();

      facesUtils.addInfoMessage("Story " + story.getNumber() + " moved to front burner.");
      return facesUtils.beautify(UrlConstants.REFRESH);
   }

   public Project getProject()
   {
      return project;
   }

   public void setProject(final Project project)
   {
      this.project = project;
   }

   public List<Story> getBackShelfStories()
   {
      return backShelfStories;
   }

   public void setBackShelfStories(final List<Story> backShelfStories)
   {
      this.backShelfStories = backShelfStories;
   }

   public String getSTORY_PARAM()
   {
      return STORY_PARAM;
   }

   public HtmlDataTable getStoryTable()
   {
      return storyTable;
   }

   public void setStoryTable(final HtmlDataTable storyTable)
   {
      this.storyTable = storyTable;
   }
}
