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
import com.ocpsoft.socialpm.gwt.client.local.places.ProfilePlace;
import com.ocpsoft.socialpm.gwt.client.local.places.SignupPlace;

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
      Place result = null;
      token = token.replaceFirst("[^/]+://[^/]+/", "");

      if (HistoryConstants.HOME().equals(token))
      {
         result = new HomePlace(token);
      }
      else
      {
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
               result = new LoginPlace();
            }
            else if ("signup".equals(place))
            {
               result = new SignupPlace();
            }

            else
            {
               result = new ProfilePlace(place);
            }
         }
      }

      System.out.println("Mapped token [" + token + "] to place [" + result + "]");
      return result;
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