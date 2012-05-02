package com.ocpsoft.socialpm.gwt.client.local.view.project.create;

import com.google.gwt.place.shared.Place;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.gwt.client.local.places.TypedPlaceTokenizer;

public class NewProjectPlace extends Place
{
   private final String token;

   public NewProjectPlace(String token)
   {
      this.token = token;
   }

   public static class Tokenizer implements TypedPlaceTokenizer<NewProjectPlace>
   {
      @Override
      public String getToken(NewProjectPlace place)
      {
         return HistoryConstants.NEW_PROJECT();
      }

      @Override
      public NewProjectPlace getPlace(String token)
      {
         if (HistoryConstants.NEW_PROJECT().equals(token))
            return new NewProjectPlace(token);
         return null;
      }

      @Override
      public Class<NewProjectPlace> getPlaceType()
      {
         return NewProjectPlace.class;
      }
   }

   @Override
   public String toString()
   {
      return token;
   }
}