/**
 * This file is part of SocialPM: Agile Project Management Tools (SocialPM)
 * Copyright (c)2010 Lincoln Baxter, III <lincoln@ocpsoft.com> (OcpSoft) If you
 * are developing and distributing open source applications under the GPL
 * Licence, then you are free to use SocialPM under the GPL License: SocialPM is
 * free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * SocialPM is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with
 * SocialPM. If not, see <http://www.gnu.org/licenses/>. For OEMs, ISVs, and
 * VARs who distribute SocialPM with their products, host their product online,
 * OcpSoft provides flexible OEM commercial Licences. Optionally, customers may
 * choose a Commercial License. For additional details, contact OcpSoft
 * (http://ocpsoft.com)
 */

package com.ocpsoft.socialpm.pages.profile;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.international.status.Messages;

import com.ocpsoft.socialpm.domain.user.User;
import com.ocpsoft.socialpm.domain.user.UserProfile;
import com.ocpsoft.socialpm.faces.validator.EmailAvailabilityValidator;
import com.ocpsoft.socialpm.model.UserService;
import com.ocpsoft.socialpm.pages.params.ParamsBean;
import com.ocpsoft.socialpm.security.Authentication;
import com.ocpsoft.socialpm.util.StringValidations;
import com.ocpsoft.socialpm.web.constants.UrlConstants;

@Named
@RequestScoped
public class UserAccountBean
{
   private static final long serialVersionUID = 884744038366427415L;

   @Inject
   private Authentication auth;

   @Inject
   private Messages messages;
   @Inject
   private ParamsBean params;

   @Inject
   private UserService us;

   private String oldPassword;
   private String newPassword;

   public String save()
   {
      User user = auth.getUser();
      us.save(user);
      if (StringValidations.isPassword(newPassword))
      {
         us.updatePassword(user, oldPassword, newPassword);
      }
      messages.info("Your changes have been saved.");
      params.setUserName(user.getUsername());
      return UrlConstants.USER_PROFILE;
   }

   public String cancel()
   {
      messages.info("Changes to your private stuff have been discarded.");
      params.setUserName(auth.getUser().getUsername());
      return UrlConstants.USER_PROFILE;
   }

   public void validateNewEmail(final FacesContext context, final UIComponent component, final Object value)
            throws ValidatorException
   {
      if (!auth.getUser().getEmail().equals(value))
      {
         new EmailAvailabilityValidator().validate(context, component, value);
      }
   }

   public UserProfile getProfile()
   {
      return auth.getProfile();
   }

   public void setProfile(final UserProfile profile)
   {
      auth.getUser().setProfile(profile);
   }

   public String getNewPassword()
   {
      return newPassword;
   }

   public void setNewPassword(final String newPassword)
   {
      this.newPassword = newPassword;
   }

   public User getUser()
   {
      return auth.getUser();
   }

   public void setUser(final User user)
   {
      this.auth.setUser(user);
   }

   public String getOldPassword()
   {
      return oldPassword;
   }

   public void setOldPassword(final String oldPassword)
   {
      this.oldPassword = oldPassword;
   }
}
