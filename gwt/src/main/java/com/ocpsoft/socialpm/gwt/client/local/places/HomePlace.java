package com.ocpsoft.socialpm.gwt.client.local.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class HomePlace extends Place
{
   private final String token;

   public HomePlace(String token)
   {
      this.token = token;
   }

   public static class Tokenizer implements PlaceTokenizer<HomePlace>
   {
      @Override
      public String getToken(HomePlace place)
      {
         return place.getToken();
      }

      @Override
      public HomePlace getPlace(String token)
      {
         return new HomePlace(token);
      }
   }

   public String getToken()
   {
      return token;
   }
}