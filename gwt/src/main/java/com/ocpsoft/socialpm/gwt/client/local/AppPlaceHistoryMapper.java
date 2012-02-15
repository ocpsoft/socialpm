package com.ocpsoft.socialpm.gwt.client.local;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryStateImpl;
import com.ocpsoft.socialpm.gwt.client.local.places.HomePlace;
import com.ocpsoft.socialpm.gwt.client.local.places.LoginPlace;
import com.ocpsoft.socialpm.gwt.client.local.places.SignupPlace;
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
      token = token.replaceFirst("[^/]+://[^/]+/", "");

      if (HistoryConstants.HOME().equals(token))
      {
         return new HomePlace(token);
      }

      String contextPath = HistoryStateImpl.getContextPath();
      if (token.startsWith(contextPath))
      {
         token = token.substring(contextPath.length());
      }

      String[] tokens = token.split(delimiter, -1);
      List<String> list = new ArrayList<String>(Arrays.asList(tokens));

      if (!list.isEmpty())
      {
         String place = list.remove(0);

         if ("login".equals(place))
         {
            return new LoginPlace();
         }
         else if ("signup".equals(place))
         {
            return new SignupPlace();
         }

         if (!list.isEmpty())
         {
            place = list.remove(0);

            if (list.isEmpty())
            {
               return new ViewProfilePlace(place);
            }
         }
      }

      return null;
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