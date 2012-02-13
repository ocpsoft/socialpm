package com.ocpsoft.socialpm.gwt.client.local;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.jboss.errai.ioc.client.api.Caller;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.ocpsoft.socialpm.gwt.client.local.activity.HomeActivity;
import com.ocpsoft.socialpm.gwt.client.local.activity.LoginActivity;
import com.ocpsoft.socialpm.gwt.client.local.activity.ViewProfileActivity;
import com.ocpsoft.socialpm.gwt.client.local.places.HomePlace;
import com.ocpsoft.socialpm.gwt.client.local.places.LoginPlace;
import com.ocpsoft.socialpm.gwt.client.local.places.ViewProfilePlace;
import com.ocpsoft.socialpm.gwt.client.shared.HelloMessage;
import com.ocpsoft.socialpm.gwt.client.shared.rpc.AuthenticationService;
import com.ocpsoft.socialpm.gwt.client.shared.rpc.ProfileService;

@ApplicationScoped
public class AppActivityMapper implements ActivityMapper
{
   private final ClientFactory clientFactory;
   private final Caller<AuthenticationService> authService;
   private final Caller<ProfileService> profileService;
   private final Event<HelloMessage> messageEvent;

   @Inject
   @SuppressWarnings("cdi-ambiguous-dependency")
   public AppActivityMapper(ClientFactory clientFactory, Caller<AuthenticationService> authService,
            Caller<ProfileService> profileService, Event<HelloMessage> messageEvent)
   {
      this.clientFactory = clientFactory;
      this.authService = authService;
      this.profileService = profileService;
      this.messageEvent = messageEvent;
   }

   @Override
   public Activity getActivity(Place place)
   {
      if (place instanceof HomePlace)
         return new HomeActivity((HomePlace) place, clientFactory, authService, messageEvent);
      if (place instanceof LoginPlace)
         return new LoginActivity((LoginPlace) place, clientFactory, authService);
      if (place instanceof ViewProfilePlace)
         return new ViewProfileActivity((ViewProfilePlace) place, clientFactory, authService, profileService);
      return null;
   }
}