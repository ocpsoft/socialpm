package com.ocpsoft.socialpm.gwt.client.local.view.login;

import com.google.gwt.place.shared.Place;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.gwt.client.local.places.TypedPlaceTokenizer;

public class LoginPlace extends Place
{

   public LoginPlace()
   {}

   public LoginPlace(String token)
   {}

   public static class Tokenizer implements TypedPlaceTokenizer<LoginPlace>
   {
      @Override
      public String getToken(LoginPlace place)
      {
         return HistoryConstants.LOGIN();
      }

      @Override
      public LoginPlace getPlace(String token)
      {
         if (HistoryConstants.LOGIN().equals(token))
            return new LoginPlace(token);
         return null;
      }

      @Override
      public Class<LoginPlace> getPlaceType()
      {
         return LoginPlace.class;
      }
   }
}