package com.ocpsoft.socialpm.gwt.client.local.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ProjectPlace extends Place
{
   private final String username;
   private final String slug;

   public ProjectPlace(String username, String slug)
   {
      System.out.println("Created ProjectPlace with username [" + username + "] / slug [" + slug + "]");
      this.username = username;
      this.slug = slug;
   }

   public static class Tokenizer implements PlaceTokenizer<ProjectPlace>
   {
      @Override
      public String getToken(ProjectPlace place)
      {
         return place.getUsername() + "/" + place.getSlug();
      }

      @Override
      public ProjectPlace getPlace(String token)
      {
         String[] split = token.split("/");
         return new ProjectPlace(split[0], split[1]);
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
      return "ProjectPlace[" + username + "/" + slug + "]";
   }
}