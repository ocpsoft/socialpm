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
      String passwordValue = password.getValue();
      String passwordConfirmValue = passwordConfirm.getValue();

      boolean ignore = false;
      if ((passwordValue == null) || "".equals(passwordValue))
      {
         password.getComponent().setValid(false);
         ignore = true;
      }

      if ((passwordConfirmValue == null) || ("".equals(passwordConfirmValue)))
      {
         passwordConfirm.getComponent().setValid(false);
         ignore = true;
      }

      if (!ignore && !passwordValue.equals(passwordConfirmValue))
      {
         throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords do not match.",
                  null));
      }
   }
}