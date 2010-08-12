/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
