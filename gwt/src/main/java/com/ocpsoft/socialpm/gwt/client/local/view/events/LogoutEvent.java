package com.ocpsoft.socialpm.gwt.client.local.view.events;

import com.ocpsoft.socialpm.model.user.Profile;

public class LogoutEvent
{
   private final Profile profile;

   public LogoutEvent(Profile profile)
   {
      this.profile = profile;
   }

   public Profile getProfile()
   {
      return profile;
   }
}
