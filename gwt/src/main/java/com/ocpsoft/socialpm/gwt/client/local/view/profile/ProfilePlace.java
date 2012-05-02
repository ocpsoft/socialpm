package com.ocpsoft.socialpm.gwt.client.local.view.profile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.place.shared.Place;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.gwt.client.local.places.TypedPlaceTokenizer;

public class ProfilePlace extends Place
{
   private final String username;

   public ProfilePlace(String username)
   {
      this.username = username;
   }

   public static class Tokenizer implements TypedPlaceTokenizer<ProfilePlace>
   {
      @Override
      public String getToken(ProfilePlace place)
      {
         return place.getUsername();
      }

      @Override
      public ProfilePlace getPlace(String token)
      {
         String[] tokens = token.split(HistoryConstants.DELIMETER(), -1);
         List<String> list = new ArrayList<String>(Arrays.asList(tokens));
         if (list.size() == 1)
         {
            return new ProfilePlace(token);
         }
         return null;
      }

      @Override
      public Class<ProfilePlace> getPlaceType()
      {
         return ProfilePlace.class;
      }
   }

   public String getUsername()
   {
      return username;
   }

   @Override
   public String toString()
   {
      return getClass().getName() + "[" + username + "]";
   }
}