package com.ocpsoft.socialpm.gwt.client.local.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.ocpsoft.socialpm.gwt.client.local.App;
import com.ocpsoft.socialpm.gwt.client.local.ClientFactory;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.gwt.client.local.places.LoginPlace;
import com.ocpsoft.socialpm.gwt.client.local.view.LoginView;
import com.ocpsoft.socialpm.gwt.client.local.view.LoginViewImpl;
import com.ocpsoft.socialpm.gwt.client.local.view.events.LoginEvent;
import com.ocpsoft.socialpm.model.user.Profile;

public class LoginActivity extends AbstractActivity implements LoginViewImpl.Presenter
{
   private final ClientFactory clientFactory;

   public LoginActivity(LoginPlace place, ClientFactory clientFactory)
   {
      this.clientFactory = clientFactory;
   }

   @Override
   public void start(AcceptsOneWidget containerWidget, EventBus eventBus)
   {
      LoginView loginView = clientFactory.getLoginView();
      loginView.setPresenter(this);

      loginView.getBrandLink().setText("SocialPM");
      loginView.getBrandLink().setTargetHistoryToken(HistoryConstants.HOME());
      loginView.getBrandLink().setEnabled(true);

      Profile loggedInProfile = App.getLoggedInProfile();
      if(loggedInProfile != null)
      {
         handleLogin(new LoginEvent(loggedInProfile));
      }

      containerWidget.setWidget(loginView.asWidget());
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

   @Override
   public void handleLogin(LoginEvent event)
   {
      LoginView loginView = clientFactory.getLoginView();
      loginView.getSigninStatus().setSignedIn(event.getProfile());
   }
}