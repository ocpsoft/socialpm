package com.ocpsoft.socialpm.gwt.client.local;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.ocpsoft.socialpm.gwt.client.local.activity.HomeActivity;
import com.ocpsoft.socialpm.gwt.client.local.activity.LoginActivity;
import com.ocpsoft.socialpm.gwt.client.local.activity.ProfileActivity;
import com.ocpsoft.socialpm.gwt.client.local.places.HomePlace;
import com.ocpsoft.socialpm.gwt.client.local.places.LoginPlace;
import com.ocpsoft.socialpm.gwt.client.local.places.ViewProfilePlace;

@ApplicationScoped
public class AppActivityMapper implements ActivityMapper
{
   private final ClientFactory clientFactory;

   @Inject
   @SuppressWarnings("cdi-ambiguous-dependency")
   public AppActivityMapper(ClientFactory clientFactory)
   {
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
         return new ProfileActivity((ViewProfilePlace) place, clientFactory);
      return null;
   }
}