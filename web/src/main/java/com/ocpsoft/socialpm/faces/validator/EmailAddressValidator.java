package com.ocpsoft.socialpm.faces.validator;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@RequestScoped
@FacesValidator("emailValidator")
public class EmailAddressValidator implements Validator
{
   @Override
   public void validate(final FacesContext context, final UIComponent comp, final Object value)
            throws ValidatorException
   {
      String address = value.toString();
      if (!address.matches(".+@.+\\\\.[a-z]+"))
      {
         FacesMessage message = new FacesMessage();
         message.setDetail("Email not valid");
         message.setSummary("Email not valid");
         message.setSeverity(FacesMessage.SEVERITY_ERROR);
         throw new ValidatorException(message);
      }
   }
}