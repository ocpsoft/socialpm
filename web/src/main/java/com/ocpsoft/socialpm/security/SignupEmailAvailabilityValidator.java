package com.ocpsoft.socialpm.security;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.ocpsoft.socialpm.cdi.Current;
import com.ocpsoft.socialpm.domain.user.Profile;
import com.ocpsoft.socialpm.model.ProfileService;

@RequestScoped
@FacesValidator("signupEmailAvailabilityValidator")
public class SignupEmailAvailabilityValidator implements Validator
{
   @Inject
   private EntityManager em;

   @Inject
   private ProfileService ps;

   @Inject
   @Current
   private Profile profile;

   @Override
   public void validate(final FacesContext context, final UIComponent comp, final Object value)
            throws ValidatorException
   {
      if (value instanceof String && !value.equals(profile.getUsername())) {
         ps.setEntityManager(em);
         if (!ps.isEmailAddressAvailable((String) value))
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Already in use",
                     null));
      }
   }
}