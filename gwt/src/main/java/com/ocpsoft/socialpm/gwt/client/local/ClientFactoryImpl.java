package com.ocpsoft.socialpm.gwt.client.local;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.ocpsoft.socialpm.gwt.client.local.view.HomeView;
import com.ocpsoft.socialpm.gwt.client.local.view.LoginView;

@ApplicationScoped
public class ClientFactoryImpl implements ClientFactory
{
   private final EventBus eventBus = new SimpleEventBus();
   private final PlaceController placeController = new PlaceController(eventBus);

   @Inject
   private HomeView homeView;

   @Inject
   private LoginView loginView;

   @Override
   public PlaceController getPlaceController()
   {
      return placeController;
   }

   @Override
   public EventBus getEventBus()
   {
      return eventBus;
   }

   /*
    * Views
    */
   @Override
   public HomeView getHomeView()
   {
      return homeView;
   }

   @Override
   public LoginView getLoginView()
   {
      return loginView;
   }

}
