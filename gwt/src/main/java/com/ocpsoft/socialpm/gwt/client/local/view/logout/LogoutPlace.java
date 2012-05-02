package com.ocpsoft.socialpm.gwt.client.local.view.logout;

import com.google.gwt.place.shared.Place;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.gwt.client.local.places.TypedPlaceTokenizer;

public class LogoutPlace extends Place
{

   public LogoutPlace()
   {}

   public LogoutPlace(String token)
   {}

   public static class Tokenizer implements TypedPlaceTokenizer<LogoutPlace>
   {
      @Override
      public String getToken(LogoutPlace place)
      {
         return HistoryConstants.LOGOUT();
      }

      @Override
      public LogoutPlace getPlace(String token)
      {
         if (HistoryConstants.LOGOUT().equals(token))
            return new LogoutPlace(token);
         return null;
      }

      @Override
      public Class<LogoutPlace> getPlaceType()
      {
         return LogoutPlace.class;
      }
   }
}