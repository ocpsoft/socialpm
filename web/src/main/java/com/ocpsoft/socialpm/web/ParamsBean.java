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
package com.ocpsoft.socialpm.web;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
@Named("params")
@RequestScoped
public class ParamsBean
{
   private int featureNumber;
   private int iterationNumber;
   private String projectSlug;
   private String profileUsername;
   private int storyNumber;

   public String getProjectSlug()
   {
      return projectSlug;
   }

   public void setProjectSlug(final String projectSlug)
   {
      this.projectSlug = projectSlug;
   }

   public String getProfileUsername()
   {
      return profileUsername;
   }

   public void setProfileUsername(final String profileUsername)
   {
      this.profileUsername = profileUsername;
   }

   public int getStoryNumber()
   {
      return storyNumber;
   }

   public void setStoryNumber(final int storyNumber)
   {
      this.storyNumber = storyNumber;
   }

   public int getIterationNumber()
   {
      return iterationNumber;
   }

   public void setIterationNumber(final int iterationNumber)
   {
      this.iterationNumber = iterationNumber;
   }

   public int getFeatureNumber()
   {
      return featureNumber;
   }

   public void setFeatureNumber(final int featureNumber)
   {
      this.featureNumber = featureNumber;
   }
}
