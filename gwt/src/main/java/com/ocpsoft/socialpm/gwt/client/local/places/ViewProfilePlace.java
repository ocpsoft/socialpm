package com.ocpsoft.socialpm.gwt.client.local.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ViewProfilePlace extends Place
{
   private final String username;

   public ViewProfilePlace(String username)
   {
      this.username = username;
   }

   public static class Tokenizer implements PlaceTokenizer<ViewProfilePlace>
   {
      @Override
      public String getToken(ViewProfilePlace place)
      {
         return place.getUsername();
      }

      @Override
      public ViewProfilePlace getPlace(String token)
      {
         return new ViewProfilePlace(token);
      }
   }

   public String getUsername()
   {
      return username;
   }
}