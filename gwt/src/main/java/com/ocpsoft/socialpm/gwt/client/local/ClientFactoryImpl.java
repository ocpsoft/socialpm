package com.ocpsoft.socialpm.gwt.client.local;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.ocpsoft.socialpm.gwt.client.local.view.HomeView;

@ApplicationScoped
public class ClientFactoryImpl implements ClientFactory
{
   private final EventBus eventBus = new SimpleEventBus();
   private final PlaceController placeController = new PlaceController(eventBus);
   
   @Inject
   private final HomeView homeView = new HomeView();

   @Override
   public PlaceController getPlaceController()
   {
      return placeController;
   }

   @Override
   public HomeView getHomeView()
   {
      return homeView;
   }

   @Override
   public EventBus getEventBus()
   {
      return eventBus;
   }

}
