package com.ocpsoft.socialpm.gwt.client.local;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;

@ApplicationScoped
public class ClientFactoryImpl implements ClientFactory
{
   private final EventBus eventBus = new SimpleEventBus();
   private final PlaceController placeController = new PlaceController(eventBus);

   @Inject
   private EventsFactory eventFactory;

   @Inject
   private ServiceFactory serviceFactory;

   public ClientFactoryImpl()
   {}

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

   @Override
   public EventsFactory getEventFactory()
   {
      return eventFactory;
   }

   @Override
   public ServiceFactory getServiceFactory()
   {
      return serviceFactory;
   }
}
