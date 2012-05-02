package com.ocpsoft.socialpm.gwt.client.local.view.home;

import com.google.gwt.place.shared.Place;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.gwt.client.local.places.TypedPlaceTokenizer;

public class HomePlace extends Place
{
   private final String token;
   
   public HomePlace()
   {
      this(HistoryConstants.HOME());
   }

   public HomePlace(String token)
   {
      this.token = token;
   }

   public static class Tokenizer implements TypedPlaceTokenizer<HomePlace>
   {
      @Override
      public String getToken(HomePlace place)
      {
         return place.getToken();
      }

      @Override
      public HomePlace getPlace(String token)
      {
         if ("".equals(token) || "/".equals(token) || HistoryConstants.HOME().equals(token))
         {
            return new HomePlace(token);
         }
         return null;
      }

      @Override
      public Class<HomePlace> getPlaceType()
      {
         return HomePlace.class;
      }
   }

   public String getToken()
   {
      return token;
   }
}