package com.ocpsoft.socialpm.gwt.client.local.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class HomePlace extends Place
{

   public HomePlace()
   {}

   public HomePlace(String token)
   {}

   public static class Tokenizer implements PlaceTokenizer<HomePlace>
   {
      @Override
      public String getToken(HomePlace place)
      {
         return "";
      }

      @Override
      public HomePlace getPlace(String token)
      {
         return new HomePlace(token);
      }
   }
}