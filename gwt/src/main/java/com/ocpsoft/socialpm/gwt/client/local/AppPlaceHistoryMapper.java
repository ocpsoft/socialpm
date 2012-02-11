package com.ocpsoft.socialpm.gwt.client.local;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.ocpsoft.socialpm.gwt.client.local.places.HomePlace;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public class AppPlaceHistoryMapper implements PlaceHistoryMapper
{
   private AppPlaceFactory factory = new AppPlaceFactory();

   String delimiter = "/";

   @Override
   public Place getPlace(String token)
   {
      String[] tokens = token.split(delimiter, 2);
      System.out.println("Mapping history to: " + token + " ["+tokens+"]");
      
      return factory.getHome();
   }

   @Override
   public String getToken(Place place)
   {
      if (place instanceof HomePlace)
      {
         return "";
      }
      return "";
   }
}