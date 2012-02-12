package com.ocpsoft.socialpm.gwt.client.local;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.ocpsoft.socialpm.gwt.client.local.places.HomePlace;
import com.ocpsoft.socialpm.gwt.client.local.places.LoginPlace;
import com.ocpsoft.socialpm.gwt.client.local.places.ViewProfilePlace;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
@ApplicationScoped
public class AppPlaceHistoryMapper implements PlaceHistoryMapper
{
   String delimiter = "/";

   @Override
   public Place getPlace(String token)
   {
      System.out.println("Parsing place token " + token);
      if (!token.contains("/"))
      {
         token = "prefix/" + token;
      }
      if (token.startsWith("/"))
      {
         token = token.substring(1);
      }
      if (token.endsWith("/"))
      {
         token = token.substring(0, token.length() - 1);
      }
      String[] tokens = token.split(delimiter, -1);

      List<String> list = new ArrayList<String>(Arrays.asList(tokens));

      if (!list.isEmpty())
      {
         System.out.println(list);
         list.remove(0);
         for (String t : list) {
            System.out.println("Token: " + t);
         }

         if (!list.isEmpty())
         {
            String place = list.remove(0);
            if ("".equals(place))
            {
               System.out.println("Going home: " + place);
               return new HomePlace();
            }

            if ("login".equals(place))
            {
               System.out.println("Going login: " + place);
               return new LoginPlace();
            }

            if (list.isEmpty())
            {
               System.out.println("Going view profile: " + place);
               return new ViewProfilePlace(place);
            }
         }
      }

      System.out.println("Going default");
      return new HomePlace();
   }

   @Override
   public String getToken(Place place)
   {
      if (place instanceof HomePlace)
      {
         return new HomePlace.Tokenizer().getToken((HomePlace) place);
      }
      else if (place instanceof LoginPlace)
      {
         return new LoginPlace.Tokenizer().getToken((LoginPlace) place);
      }
      return "";
   }
}