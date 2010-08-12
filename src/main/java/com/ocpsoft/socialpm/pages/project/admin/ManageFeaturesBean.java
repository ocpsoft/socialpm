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

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import com.ocpsoft.exceptions.NoSuchObjectException;
import com.ocpsoft.socialpm.constants.UrlConstants;
import com.ocpsoft.socialpm.domain.project.Feature;
import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.pages.PageBean;
import com.ocpsoft.util.StringValidations;

@Named
@RequestScoped
public class ManageFeaturesBean extends PageBean
{
   private static final long serialVersionUID = -5334349862759081475L;

   private Project project;
   private DataModel<Feature> features;

   private String name = "";

   public void load()
   {
      project = currentProjectBean.getProject();
      features = new ListDataModel<Feature>(project.getFeatures());
   }

   private Validator nameValidator = new Validator()
   {
      public void validate(final FacesContext context, final UIComponent component, final Object value)
               throws ValidatorException
      {
         if (!StringValidations.isAlphanumericSpaceUnderscore((String) value))
         {
            throw new ValidatorException(new FacesMessage("Name must contain only [A-Z], [0-9], [ _]"));
         }
         try
         {
            currentProjectBean.getProject().getFeature((String) value);
            throw new ValidatorException(new FacesMessage("Feature '" + value + "' exists"));
         }
         catch (NoSuchObjectException e)
         {
            // valid feature
         }
      };
   };

   /*
    * Action methods
    */
   public String create()
   {
      Feature feature = new Feature();
      feature.setName(name);
      project.getFeatures().add(feature);
      ps.addFeature(project, feature);

      return facesUtils.beautify(UrlConstants.REFRESH);
   }

   public String remove(final Feature feature)
   {
      if (ps.getStoriesByFeature(project.getId(), feature.getId()).isEmpty())
      {
         ps.removeFeature(project, feature);
         facesUtils.addInfoMessage("Removed feature: " + feature.getName());
      }
      else
      {
         facesUtils
                  .addWarningMessage("This feature contains stories. Before it can be deleted, you must move those stories to another feature.");
         params.setFeature(feature.getName());
         return facesUtils.beautify(UrlConstants.REMOVE_FEATURE);
      }

      return facesUtils.beautify(UrlConstants.REFRESH);
   }

   /*
    * Getters and setters
    */
   public String getName()
   {
      return name;
   }

   public void setName(final String name)
   {
      this.name = name;
   }

   public Project getProject()
   {
      return project;
   }

   public void setProject(final Project project)
   {
      this.project = project;
   }

   public DataModel<Feature> getFeatures()
   {
      return features;
   }

   public void setFeatures(final DataModel<Feature> features)
   {
      this.features = features;
   }

   public Validator getNameValidator()
   {
      return nameValidator;
   }

   public void setNameValidator(final Validator nameValidator)
   {
      this.nameValidator = nameValidator;
   }
}
