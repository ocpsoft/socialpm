package com.ocpsoft.socialpm.gwt.client.local.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.ocpsoft.socialpm.gwt.client.local.ClientFactory;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.gwt.client.local.places.LoginPlace;
import com.ocpsoft.socialpm.gwt.client.local.view.FluidLayout;
import com.ocpsoft.socialpm.gwt.client.local.view.LoginView;

public class LoginActivity extends AbstractActivity implements FluidLayout.Presenter
{
   private final ClientFactory clientFactory;

   public LoginActivity(LoginPlace place, ClientFactory clientFactory)
   {
      System.out.println("Created LoginActivity");
      this.clientFactory = clientFactory;
   }

   @Override
   public void start(AcceptsOneWidget containerWidget, EventBus eventBus)
   {
      LoginView loginView = clientFactory.getLoginView();
      loginView.setPresenter(this);

      System.out.println("Started LoginActivity");

      loginView.getBrandLink().setText("SocialPM");
      loginView.getBrandLink().setHref(HistoryConstants.HOME());
      loginView.getBrandLink().setEnabled(true);

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
}