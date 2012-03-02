package com.ocpsoft.socialpm.gwt.client.local;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.ocpsoft.rewrite.gwt.client.history.HistoryStateImpl;
import com.ocpsoft.socialpm.gwt.client.local.activity.HomeActivity;
import com.ocpsoft.socialpm.gwt.client.local.activity.LoginActivity;
import com.ocpsoft.socialpm.gwt.client.local.activity.ProfileActivity;
import com.ocpsoft.socialpm.gwt.client.local.activity.ProjectActivity;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.gwt.client.local.places.HomePlace;
import com.ocpsoft.socialpm.gwt.client.local.places.LoginPlace;
import com.ocpsoft.socialpm.gwt.client.local.places.ProfilePlace;
import com.ocpsoft.socialpm.gwt.client.local.places.ProjectPlace;
import com.ocpsoft.socialpm.gwt.client.local.places.SignupPlace;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
@ApplicationScoped
public class AppPlaceHistoryMapper implements PlaceHistoryMapper, ActivityMapper
{
   String delimiter = "/";

   private final ClientFactory clientFactory;

   @Inject
   public AppPlaceHistoryMapper(ClientFactory clientFactory)
   {
      this.clientFactory = clientFactory;
   }

   @Override
   public Place getPlace(String token)
   {
      Place result = null;
      token = token.replaceFirst("[^/]+://[^/]+/", "");
      String contextPath = HistoryStateImpl.getContextPath();

      if (HistoryConstants.HOME().equals(token))
      {
         result = new HomePlace(token);
      }
      else
      {
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
            else if (!list.isEmpty())
            {
               String sub = list.remove(0);
               if (list.isEmpty())
               {
                  result = new ProjectPlace(place, sub);
               }
            }
            else
            {
               result = new ProfilePlace(place);
            }
         }
      }

      if (result == null)
      {
         throw new RuntimeException("Could not map token [" + token + "] to place");
      }

      System.out.println("Mapped token [" + token + "] to place [" + result + "]");
      return result;
   }

   @Override
   public String getToken(Place place)
   {
      String result = null;
      if (place instanceof HomePlace)
      {
         result = new HomePlace.Tokenizer().getToken((HomePlace) place);
      }
      else if (place instanceof LoginPlace)
      {
         result = new LoginPlace.Tokenizer().getToken((LoginPlace) place);
      }
      else if (place instanceof ProfilePlace)
      {
         result = new ProfilePlace.Tokenizer().getToken((ProfilePlace) place);
      }
      else if (place instanceof ProjectPlace)
      {
         result = new ProjectPlace.Tokenizer().getToken((ProjectPlace) place);
      }

      if (result == null)
      {
         throw new RuntimeException("Failed to get token from Place [" + place + "]");
      }

      return result;
   }

   @Override
   public Activity getActivity(Place place)
   {
      Activity result = null;
      if (place instanceof HomePlace)
         result = new HomeActivity((HomePlace) place, clientFactory);
      if (place instanceof LoginPlace)
         result = new LoginActivity((LoginPlace) place, clientFactory);
      if (place instanceof ProfilePlace)
         result = new ProfileActivity((ProfilePlace) place, clientFactory);
      if (place instanceof ProjectPlace)
         result = new ProjectActivity((ProjectPlace) place, clientFactory);

      if (result == null)
      {
         throw new RuntimeException("Failed to get Activity from Place [" + place + "]");
      }
      return result;
   }
}