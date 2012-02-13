package com.ocpsoft.socialpm.gwt.client.local.activity;

import org.jboss.errai.bus.client.api.ErrorCallback;
import org.jboss.errai.bus.client.api.Message;
import org.jboss.errai.bus.client.api.RemoteCallback;
import org.jboss.errai.enterprise.client.cdi.api.CDI;
import org.jboss.errai.ioc.client.api.Caller;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.ocpsoft.socialpm.gwt.client.local.ClientFactory;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.gwt.client.local.places.ViewProfilePlace;
import com.ocpsoft.socialpm.gwt.client.local.view.ProfileView;
import com.ocpsoft.socialpm.gwt.client.shared.rpc.AuthenticationService;
import com.ocpsoft.socialpm.gwt.client.shared.rpc.ProfileService;
import com.ocpsoft.socialpm.model.user.Profile;

public class ViewProfileActivity extends AbstractActivity implements ProfileView.Presenter
{
   private final ClientFactory clientFactory;
   private final String username;
   private final Caller<ProfileService> profileService;
   private final Caller<AuthenticationService> authService;

   public ViewProfileActivity(ViewProfilePlace place, ClientFactory clientFactory,
            Caller<AuthenticationService> authService, Caller<ProfileService> profileService)
   {
      System.out.println("Created ViewProfileActivity");
      this.clientFactory = clientFactory;
      this.authService = authService;
      this.profileService = profileService;
      this.username = place.getUsername();
   }

   @Override
   public void start(AcceptsOneWidget containerWidget, EventBus eventBus)
   {
      final ProfileView profileView = clientFactory.getViewProfileView();
      profileView.setPresenter(this);

      System.out.println("Started ViewProfileActivity");

      profileView.getBrandLink().setText("SocialPM");
      profileView.getBrandLink().setHref(HistoryConstants.HOME());
      profileView.getBrandLink().setEnabled(true);

      profileView.getGreeting().setHeading("Loading...");

      CDI.addPostInitTask(new Runnable() {
         @Override
         public void run()
         {

            profileService.call(new RemoteCallback<Profile>() {

               @Override
               public void callback(Profile response)
               {
                  profileView.setProfile(response);
               }

            }, new ErrorCallback() {

               @Override
               public boolean error(Message message, Throwable throwable)
               {
                  System.out.println("Failed to fetch profile: " + username);
                  return false;
               }

            });
         }
      });

      containerWidget.setWidget(profileView.asWidget());
      System.out.println("Finished Startup ViewProfileActivity");
   }

   @Override
   public String mayStop()
   {
      return null;
   }

   @Override
   public void goTo(Place place)
   {
      clientFactory.getPlaceController().goTo(place);
   }
}