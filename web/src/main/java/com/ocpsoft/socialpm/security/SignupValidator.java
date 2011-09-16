package com.ocpsoft.socialpm.security;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.inject.Inject;

import org.jboss.seam.faces.validation.InputElement;
import org.jboss.seam.faces.validation.ValidatorException;

@RequestScoped
@FacesValidator("signupValidator")
public class SignupValidator implements Validator
{
   @Inject
   private InputElement<String> passwordConfirm;

   @Override
   public void validate(final FacesContext context, final UIComponent comp, final Object values)
            throws ValidatorException
   {
      if ((passwordConfirm.getValue() == null) || "".equals(passwordConfirm.getValue()))
      {
         passwordConfirm.getComponent().setValid(false);
         throw new ValidatorException("Please confirm your password.");
      }
   }
}