package com.ocpsoft.socialpm.gwt.client.local.history;

import com.ocpsoft.rewrite.gwt.client.history.HistoryStateImpl;
import com.ocpsoft.socialpm.model.user.Profile;

public class HistoryConstants
{
   public static String DELIMETER()
   {
      return "/";
   }

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

   public static String LOGOUT()
   {
      return "logout";
   }

   public static final String NEW_PROJECT()
   {
      return "new-project";
   }

   public static final String PROFILE(Profile profile)
   {
      return profile.getUsername();
   }

}
