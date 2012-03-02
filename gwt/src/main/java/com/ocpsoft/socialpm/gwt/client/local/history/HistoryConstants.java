package com.ocpsoft.socialpm.gwt.client.local.history;

import com.ocpsoft.rewrite.gwt.client.history.HistoryStateImpl;
import com.ocpsoft.socialpm.model.user.Profile;

public class HistoryConstants
{
   public static final String HOME()
   {
      return HistoryStateImpl.getContextPath();
   }

   public static final String SIGNUP()
   {
      return "signup";
   }

   public static final String LOGIN()
   {
      return "login";
   }

   public static final String PROFILE(Profile profile)
   {
      return profile.getUsername();
   }
}
