package com.ocpsoft.socialpm.gwt.client.local.view.story.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.place.shared.Place;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.gwt.client.local.places.TypedPlaceTokenizer;

public class StoryViewPlace extends Place
{
   private final String username;
   private final String slug;
   private final int storyNumber;

   public StoryViewPlace(String username, String slug, int storyNumber)
   {
      this.username = username;
      this.slug = slug;
      this.storyNumber = storyNumber;
   }

   public static class Tokenizer implements TypedPlaceTokenizer<StoryViewPlace>
   {
      @Override
      public String getToken(StoryViewPlace place)
      {
         return place.getUsername() + "/" + place.getSlug() + "/" + place.getStoryNumber();
      }

      @Override
      public StoryViewPlace getPlace(String token)
      {
         String[] tokens = token.split(HistoryConstants.DELIMETER(), -1);
         List<String> list = new ArrayList<String>(Arrays.asList(tokens));
         if (list.size() == 3)
         {
            String string = list.get(2);
            try {
               Integer number = Integer.valueOf(string);
               return new StoryViewPlace(list.get(0), list.get(1), number);
            }
            catch (NumberFormatException e) {}
         }
         return null;
      }

      @Override
      public Class<StoryViewPlace> getPlaceType()
      {
         return StoryViewPlace.class;
      }
   }

   public String getUsername()
   {
      return username;
   }

   public int getStoryNumber()
   {
      return storyNumber;
   }

   public String getSlug()
   {
      return slug;
   }

   @Override
   public String toString()
   {
      return getClass().getName() + "[" + username + "/" + slug + "]";
   }
}