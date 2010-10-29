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

package com.ocpsoft.socialpm.faces.validator;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

import com.ocpsoft.pretty.PrettyContext;
import com.ocpsoft.pretty.faces.config.mapping.UrlMapping;
import com.ocpsoft.socialpm.domain.NoSuchObjectException;
import com.ocpsoft.socialpm.model.UserService;

@FacesValidator("usernameAvailabilityValidator")
public class UsernameAvailabilityValidator implements Validator
{
   @Inject
   UserService us;

   @Override
   public void validate(final FacesContext context, final UIComponent component, final Object value)
            throws ValidatorException
   {
      FacesMessage msg = new FacesMessage("Sorry, that username is unavailable.");
      String field = value.toString();

      // TODO put a design pattern around this validator - support multiple providers for bad name checks
      List<UrlMapping> mappings = PrettyContext.getCurrentInstance().getConfig().getMappings();
      for (UrlMapping m : mappings)
      {
         if (m.getPattern().equals("/" + field))
         {
            throw new ValidatorException(msg);
         }
      }

      try
      {
         us.getUserByName(field);
         throw new ValidatorException(msg);
      }
      catch (NoSuchObjectException e)
      {
      }
   }
}
