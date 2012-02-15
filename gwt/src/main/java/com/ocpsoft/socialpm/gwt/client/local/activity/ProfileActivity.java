package com.ocpsoft.socialpm.gwt.client.local.activity;

import org.jboss.errai.bus.client.api.ErrorCallback;
import org.jboss.errai.bus.client.api.Message;
import org.jboss.errai.bus.client.api.RemoteCallback;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.ocpsoft.socialpm.gwt.client.local.ClientFactory;
import com.ocpsoft.socialpm.gwt.client.local.places.ProfilePlace;
import com.ocpsoft.socialpm.gwt.client.local.view.ProfileView;
import com.ocpsoft.socialpm.gwt.client.local.view.events.LoginEvent;
import com.ocpsoft.socialpm.model.user.Profile;

public class ProfileActivity extends AbstractActivity implements ProfileView.Presenter
{
   private final ClientFactory clientFactory;
   private final String username;

   public ProfileActivity(ProfilePlace place, ClientFactory clientFactory)
   {
      this.clientFactory = clientFactory;
      this.username = place.getUsername();
   }

   @Override
   public void start(AcceptsOneWidget containerWidget, EventBus eventBus)
   {
      System.out.println("Starting ProfileActivity");
      final ProfileView profileView = clientFactory.getViewProfileView();
      profileView.setPresenter(this);

      profileView.getGreeting().setHeading("Loading...");

      clientFactory.getServiceFactory().getProfileService().call(new RemoteCallback<Profile>() {

         @Override
         public void callback(Profile response)
         {
            profileView.setProfile(response);
         }

      }, new ErrorCallback() {

         @Override
         public boolean error(Message message, Throwable throwable)
         {
            throwable.printStackTrace();
            return false;
         }

      }).getProfileByUsername(username);

      containerWidget.setWidget(profileView.asWidget());
   }

   @Override
   public void handleLogin(LoginEvent event)
   {
      // TODO Auto-generated method stub

   }

   @Override
   public String mayStop()
   {
      System.out.println("Stopping ProfileActivity");
      return null;
   }

   @Override
   public void goTo(Place place)
   {
      clientFactory.getPlaceController().goTo(place);
   }
}