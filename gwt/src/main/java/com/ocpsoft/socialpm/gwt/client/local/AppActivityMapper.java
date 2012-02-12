package com.ocpsoft.socialpm.gwt.client.local;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.ocpsoft.socialpm.gwt.client.local.activity.HomeActivity;
import com.ocpsoft.socialpm.gwt.client.local.activity.LoginActivity;
import com.ocpsoft.socialpm.gwt.client.local.activity.ViewProfileActivity;
import com.ocpsoft.socialpm.gwt.client.local.places.HomePlace;
import com.ocpsoft.socialpm.gwt.client.local.places.LoginPlace;
import com.ocpsoft.socialpm.gwt.client.local.places.ViewProfilePlace;

public class AppActivityMapper implements ActivityMapper
{
   private final ClientFactory clientFactory;

   public AppActivityMapper(ClientFactory clientFactory)
   {
      super();
      this.clientFactory = clientFactory;
   }

   @Override
   public Activity getActivity(Place place)
   {
      if (place instanceof HomePlace)
         return new HomeActivity((HomePlace) place, clientFactory);
      if (place instanceof LoginPlace)
         return new LoginActivity((LoginPlace) place, clientFactory);
      if (place instanceof ViewProfilePlace)
         return new ViewProfileActivity((ViewProfilePlace) place, clientFactory);
      return null;
   }
}