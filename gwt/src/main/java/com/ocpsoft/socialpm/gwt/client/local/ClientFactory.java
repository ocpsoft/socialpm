package com.ocpsoft.socialpm.gwt.client.local;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.ocpsoft.socialpm.gwt.client.local.view.HomeView;
import com.ocpsoft.socialpm.gwt.client.local.view.LoginView;
import com.ocpsoft.socialpm.gwt.client.local.view.ProfileView;

public interface ClientFactory
{
   EventBus getEventBus();

   PlaceController getPlaceController();

   HomeView getHomeView();

   LoginView getLoginView();

   ProfileView getViewProfileView();

   EventsFactory getEventFactory();

   ServiceFactory getServiceFactory();
}