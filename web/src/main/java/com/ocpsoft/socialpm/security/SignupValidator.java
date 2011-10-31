/*
 * Copyright 2011 <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ocpsoft.socialpm.security;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

import org.jboss.seam.faces.validation.InputElement;

@RequestScoped
@FacesValidator("signupValidator")
public class SignupValidator implements Validator
{
   @Inject
   private InputElement<String> password;
   @Inject
   private InputElement<String> passwordConfirm;

   @Override
   public void validate(final FacesContext context, final UIComponent comp, final Object values)
            throws ValidatorException
   {
      if (((passwordConfirm.getValue() == null)
               || ("".equals(passwordConfirm.getValue())))
               && !(password.getValue() == null || !"".equals(password.getValue())))
      {
         passwordConfirm.getComponent().setValid(false);
         passwordConfirm.getComponent().setValidatorMessage("Confirm your password");
         throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please confirm your password.",
                  null));
      }
   }
}