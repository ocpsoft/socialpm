package com.ocpsoft.socialpm.gwt.client.local.view.signup;

import com.google.gwt.place.shared.Place;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.gwt.client.local.places.TypedPlaceTokenizer;

public class SignupPlace extends Place
{

   public SignupPlace()
   {}

   public SignupPlace(String token)
   {}

   public static class Tokenizer implements TypedPlaceTokenizer<SignupPlace>
   {
      @Override
      public String getToken(SignupPlace place)
      {
         return HistoryConstants.SIGNUP();
      }

      @Override
      public SignupPlace getPlace(String token)
      {
         if (HistoryConstants.SIGNUP().equals(token))
            return new SignupPlace(token);
         return null;
      }

      @Override
      public Class<SignupPlace> getPlaceType()
      {
         return SignupPlace.class;
      }
   }
}