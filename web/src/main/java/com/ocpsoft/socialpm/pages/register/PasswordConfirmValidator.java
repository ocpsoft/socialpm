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

package com.ocpsoft.socialpm.pages.register;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

import org.jboss.seam.faces.validation.InputField;
import org.jboss.seam.international.status.Messages;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com>Lincoln Baxter, III</a>
 * 
 */
@FacesValidator("passwordConfirm")
public class PasswordConfirmValidator implements Validator
{
   @Inject
   Messages messages;

   @Inject
   @InputField
   String password1;

   @Inject
   @InputField
   String password2;

   @Override
   @SuppressWarnings("unchecked")
   public void validate(final FacesContext context, final UIComponent comp, final Object value)
   {
      Map<String, UIComponent> fields = (Map<String, UIComponent>) value;
      if ((password1 != null) && (password2 != null))
      {
         if (!password1.equals(password2))
         {
            for (UIComponent c : fields.values())
            {
               messages.error("Passwords must match").targets(c.getClientId());
            }
            throw new ValidatorException(new FacesMessage("Please correct the errors below."));
         }
      }
   }

}
