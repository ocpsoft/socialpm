package com.ocpsoft.socialpm.gwt.client.local.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ProfilePlace extends Place
{
   private final String username;

   public ProfilePlace(String username)
   {
      System.out.println("Created ProfilePlace with username [" + username + "]");
      this.username = username;
   }

   public static class Tokenizer implements PlaceTokenizer<ProfilePlace>
   {
      @Override
      public String getToken(ProfilePlace place)
      {
         return place.getUsername();
      }

      @Override
      public ProfilePlace getPlace(String token)
      {
         return new ProfilePlace(token);
      }
   }

   public String getUsername()
   {
      return username;
   }
}