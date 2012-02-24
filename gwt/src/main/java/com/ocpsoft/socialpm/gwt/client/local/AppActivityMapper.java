package com.ocpsoft.socialpm.gwt.client.local;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.ocpsoft.socialpm.gwt.client.local.activity.HomeActivity;
import com.ocpsoft.socialpm.gwt.client.local.activity.LoginActivity;
import com.ocpsoft.socialpm.gwt.client.local.activity.ProfileActivity;
import com.ocpsoft.socialpm.gwt.client.local.activity.ProjectActivity;
import com.ocpsoft.socialpm.gwt.client.local.places.HomePlace;
import com.ocpsoft.socialpm.gwt.client.local.places.LoginPlace;
import com.ocpsoft.socialpm.gwt.client.local.places.ProfilePlace;
import com.ocpsoft.socialpm.gwt.client.local.places.ProjectPlace;

@ApplicationScoped
public class AppActivityMapper implements ActivityMapper
{
   private final ClientFactory clientFactory;

   @Inject
   public AppActivityMapper(ClientFactory clientFactory)
   {
      this.clientFactory = clientFactory;
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

      System.out.println("Returning Activity " + result);
      return result;
   }
}