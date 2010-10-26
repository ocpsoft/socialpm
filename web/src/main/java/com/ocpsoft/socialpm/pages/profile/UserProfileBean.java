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

package com.ocpsoft.socialpm.pages.profile;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.international.status.Messages;

import com.ocpsoft.socialpm.domain.NoSuchObjectException;
import com.ocpsoft.socialpm.domain.user.User;
import com.ocpsoft.socialpm.model.UserService;
import com.ocpsoft.socialpm.pages.params.Current;
import com.ocpsoft.socialpm.security.LoggedIn;
import com.ocpsoft.socialpm.web.constants.ApplicationConfig;
import com.ocpsoft.socialpm.web.constants.UrlConstants;

@Named
@RequestScoped
public class UserProfileBean
{
   private static final long serialVersionUID = 884744038366427415L;

   @Inject
   @Current
   private User user;

   @Inject
   @LoggedIn
   private User loggedInUser;

   @Inject
   private UserService us;

   @Inject
   private Messages messages;

   private boolean editMode = false;

   @PostConstruct
   public String load()
   {
      try
      {
         if (ApplicationConfig.GUEST_ACCOUNT_NAME.equals(user.getUsername()))
         {
            throw new NoSuchObjectException();
         }

         if (user.equals(loggedInUser))
         {
            editMode = true;
         }
      }
      catch (NoSuchObjectException e)
      {
         // TODO this needs to redirect to the invite users screen
         messages.warn("That user does not exist.");
      }
      return null;

   }

   public String save()
   {
      us.save(user);
      messages.info("Your changes have been saved.");
      return UrlConstants.REFRESH;
   }

   public boolean isEditMode()
   {
      return editMode;
   }

   public void setEditMode(final boolean editMode)
   {
      this.editMode = editMode;
   }

   public User getUser()
   {
      return user;
   }

   public void setUser(final User user)
   {
      this.user = user;
   }
}
