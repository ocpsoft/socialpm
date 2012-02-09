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
package com.ocpsoft.socialpm.security.validator;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.ocpsoft.socialpm.util.StringValidations;

@RequestScoped
@FacesValidator("usernameValidator")
public class UsernameValidator implements Validator
{
   @Override
   public void validate(final FacesContext context, final UIComponent comp, final Object value)
            throws ValidatorException
   {
      if (value instanceof String) {
         String username = (String) value;
         if (username.length() > 30)
            throw new ValidatorException(
                     new FacesMessage(FacesMessage.SEVERITY_ERROR,
                              "Too long (maximum is 30 characters), may only " +
                                       "contain alphanumeric characters or dashes, and " +
                                       "cannot begin with a dash", null));

         if (!StringValidations.isAlphanumericDash(username))
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                     "May only contain alphanumeric characters or dashes and cannot begin with a dash", null));

         if (username.startsWith("-"))
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Must not start with a dash",
                     null));

      }
   }
}