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

package com.ocpsoft.socialpm.pages.params;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class ParamsBean
{
   private Long id;
   private String feature;
   private String projectName;
   private String releaseName;
   private Integer storyNumber;
   private String searchQuery;
   private String userName;
   private String iterationName;
   private String fromMappingId;

   public String getProjectName()
   {
      return projectName;
   }

   public void setProjectName(final String projectName)
   {
      this.projectName = projectName;
   }

   public String getSearchQuery()
   {
      return searchQuery;
   }

   public void setSearchQuery(final String searchQuery)
   {
      this.searchQuery = searchQuery;
   }

   public String getReleaseName()
   {
      return releaseName;
   }

   public void setReleaseName(final String releaseName)
   {
      this.releaseName = releaseName;
   }

   public String getFeature()
   {
      return feature;
   }

   public void setFeature(final String feature)
   {
      this.feature = feature;
   }

   public Long getId()
   {
      return id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   public String getUserName()
   {
      return userName;
   }

   public void setUserName(final String userName)
   {
      this.userName = userName;
   }

   public String getIterationName()
   {
      return iterationName;
   }

   public void setIterationName(final String iterationName)
   {
      this.iterationName = iterationName;
   }

   public String getFromMappingId()
   {
      return fromMappingId;
   }

   public void setFromMappingId(final String fromMappingId)
   {
      this.fromMappingId = fromMappingId;
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
