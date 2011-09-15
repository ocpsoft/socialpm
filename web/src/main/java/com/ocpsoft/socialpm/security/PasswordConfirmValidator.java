package com.ocpsoft.socialpm.security;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.inject.Inject;

import org.jboss.seam.faces.validation.InputElement;
import org.jboss.seam.faces.validation.ValidatorException;

@FacesValidator("passwordConfirm")
public class PasswordConfirmValidator implements Validator
{
   @Inject
   private InputElement<String> password;

   @Inject
   private InputElement<String> passwordConfirm;

   @Override
   public void validate(final FacesContext context, final UIComponent comp, final Object values)
            throws ValidatorException
   {
      if ((password.getValue() != null) && !password.getValue().equals(passwordConfirm.getValue()))
      {
         throw new ValidatorException("Passwords do not match.");
      }
   }
}