package com.ocpsoft.socialpm.gwt.client.local.view.component;

import com.ocpsoft.socialpm.model.user.Profile;

public class ProfileLink extends NavLink
{
   public ProfileLink()
   {}

   public ProfileLink(Profile profile)
   {
      super(profile.getUsername());
      setProfile(profile);
   }

   public ProfileLink setProfile(Profile profile)
   {
      setTargetHistoryToken(profile.getUsername());
      setText(profile.getUsername());
      return this;
   }
}
