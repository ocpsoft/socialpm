package com.ocpsoft.socialpm.gwt.client.local.view.project.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.place.shared.Place;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.gwt.client.local.places.TypedPlaceTokenizer;

public class ProjectPlace extends Place
{
   private final String username;
   private final String slug;

   public ProjectPlace(String username, String slug)
   {
      this.username = username;
      this.slug = slug;
   }

   public static class Tokenizer implements TypedPlaceTokenizer<ProjectPlace>
   {
      @Override
      public String getToken(ProjectPlace place)
      {
         return place.getUsername() + "/" + place.getSlug();
      }

      @Override
      public ProjectPlace getPlace(String token)
      {
         String[] tokens = token.split(HistoryConstants.DELIMETER(), -1);
         List<String> list = new ArrayList<String>(Arrays.asList(tokens));
         if (list.size() == 2)
         {
            return new ProjectPlace(list.get(0), list.get(1));
         }
         return null;
      }

      @Override
      public Class<ProjectPlace> getPlaceType()
      {
         return ProjectPlace.class;
      }
   }

   public String getUsername()
   {
      return username;
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