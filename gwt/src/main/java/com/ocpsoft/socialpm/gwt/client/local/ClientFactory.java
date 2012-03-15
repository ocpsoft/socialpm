package com.ocpsoft.socialpm.gwt.client.local;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;

public interface ClientFactory
{
   EventBus getEventBus();

   PlaceController getPlaceController();

   /*
    * Factories
    */
   EventsFactory getEventFactory();

   ServiceFactory getServiceFactory();



}