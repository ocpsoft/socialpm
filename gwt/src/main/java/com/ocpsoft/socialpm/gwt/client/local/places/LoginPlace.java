package com.ocpsoft.socialpm.gwt.client.local.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class LoginPlace extends Place
{

   public LoginPlace()
   {}

   public LoginPlace(String token)
   {}

   public static class Tokenizer implements PlaceTokenizer<LoginPlace>
   {
      @Override
      public String getToken(LoginPlace place)
      {
         return "login";
      }

      @Override
      public LoginPlace getPlace(String token)
      {
         return new LoginPlace(token);
      }
   }
}