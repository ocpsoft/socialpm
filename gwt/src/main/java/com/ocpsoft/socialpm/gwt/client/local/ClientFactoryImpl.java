package com.ocpsoft.socialpm.gwt.client.local;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.ocpsoft.socialpm.gwt.client.local.view.HomeView;
import com.ocpsoft.socialpm.gwt.client.local.view.LoginView;
import com.ocpsoft.socialpm.gwt.client.local.view.NewProjectView;
import com.ocpsoft.socialpm.gwt.client.local.view.ProfileView;
import com.ocpsoft.socialpm.gwt.client.local.view.ProjectView;

@ApplicationScoped
public class ClientFactoryImpl implements ClientFactory
{
   private final EventBus eventBus = new SimpleEventBus();
   private final PlaceController placeController = new PlaceController(eventBus);

   @Inject
   private EventsFactory eventFactory;

   @Inject
   private ServiceFactory serviceFactory;

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

   /*
    * Views
    */

   @Inject
   private HomeView homeView;

   @Inject
   private LoginView loginView;

   @Inject
   private ProfileView profileView;

   @Inject
   private ProjectView projectView;

   @Inject
   private NewProjectView newProjectView;

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

   @Override
   public ProfileView getProfileView()
   {
      return profileView;
   }

   @Override
   public ProjectView getProjectView()
   {
      return projectView;
   }

   @Override
   public NewProjectView getNewProjectView()
   {
      return newProjectView;
   }
}
