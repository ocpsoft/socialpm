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

package com.ocpsoft.socialpm.pages.search;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.pages.params.PageAware;

@Named
@RequestScoped
public class BrowseProjectsBean extends PageAware
{
   private static final long serialVersionUID = 6353872720276620127L;

   private List<Project> projects;
   private List<Integer> pages = new ArrayList<Integer>();
   private int currentPage = 1;

   public void load()
   {
      int pageSize = 15;

      int count = ps.getProjectCount();
      if (count == 0)
      {
         count++;
      }
      int pages = count / pageSize;
      if (count % pageSize > 0)
      {
         pages++;
      }
      for (int i = 0; i < pages; i++)
      {
         this.pages.add(i + 1);
      }

      if (currentPage > pages)
      {
         currentPage = pages;
      }
      projects = ps.list(pageSize, (currentPage - 1) * pageSize);
   }

   public List<Project> getProjects()
   {
      return projects;
   }

   public void setProjects(final List<Project> projects)
   {
      this.projects = projects;
   }

   public List<Integer> getPages()
   {
      return pages;
   }

   public void setPages(final List<Integer> pages)
   {
      this.pages = pages;
   }

   public int getCurrentPage()
   {
      return currentPage;
   }

   public void setCurrentPage(final int currentPage)
   {
      this.currentPage = currentPage;
   }
}
