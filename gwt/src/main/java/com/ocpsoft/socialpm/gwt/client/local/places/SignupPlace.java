package com.ocpsoft.socialpm.gwt.client.local.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class SignupPlace extends Place
{

   public SignupPlace()
   {}

   public SignupPlace(String token)
   {}

   public static class Tokenizer implements PlaceTokenizer<SignupPlace>
   {
      @Override
      public String getToken(SignupPlace place)
      {
         return "signup";
      }

      @Override
      public SignupPlace getPlace(String token)
      {
         return new SignupPlace(token);
      }
   }
}