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
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.ocpsoft.exceptions.NoSuchObjectException;
import com.ocpsoft.socialpm.constants.UrlConstants;
import com.ocpsoft.socialpm.domain.project.Feature;
import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.pages.PageBean;
import com.ocpsoft.socialpm.pages.params.Current;

@Named
@RequestScoped
public class RemoveFeatureBean extends PageBean
{
   private static final long serialVersionUID = -4470665813122279769L;

   @Inject
   @Current
   private Project project;

   private final List<Feature> features = new ArrayList<Feature>();
   private String newName;
   private Feature oldFeature;

   public String load()
   {
      try
      {
         oldFeature = project.getFeature(params.getFeature());
         features.addAll(project.getFeatures());
      }
      catch (NoSuchObjectException e)
      {
         return facesUtils.beautify(UrlConstants.MANAGE_FEATURES);
      }
      return null;
   }

   public String rename()
   {
      String oldName = oldFeature.getName();
      oldFeature.setName(newName);
      ps.save(oldFeature);
      facesUtils.addInfoMessage("Renamed feature from: " + oldName + " to: " + newName);
      return facesUtils.beautify(UrlConstants.MANAGE_FEATURES);
   }

   public String cancel()
   {
      return facesUtils.beautify(UrlConstants.MANAGE_FEATURES);
   }

   public String reassign()
   {
      Feature from = oldFeature;
      Feature to = project.getFeature(newName);
      int count = ps.mergeFeatures(from, to);
      facesUtils.addInfoMessage("Moved " + count + " stories from: " + from.getName() + " to: " + to.getName());
      return facesUtils.beautify(UrlConstants.MANAGE_FEATURES);
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

   public List<Feature> getFeatures()
   {
      return features;
   }

   public Feature getOldFeature()
   {
      return oldFeature;
   }

   public void setOldFeature(final Feature oldFeature)
   {
      this.oldFeature = oldFeature;
   }

   public String getNewName()
   {
      return newName;
   }

   public void setNewName(final String newName)
   {
      this.newName = newName;
   }

}
