package com.ocpsoft.socialpm.security;

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