package com.ocpsoft.socialpm.gwt.client.local.view.events;

import com.ocpsoft.socialpm.model.user.Profile;

public class LoginEvent
{
   private final Profile profile;

   public LoginEvent(Profile profile)
   {
      this.profile = profile;
   }

   public Profile getProfile()
   {
      return profile;
   }
}
