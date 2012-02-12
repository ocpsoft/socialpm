package com.ocpsoft.socialpm.gwt.client.local.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.ocpsoft.socialpm.gwt.client.local.ClientFactory;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.gwt.client.local.places.HomePlace;
import com.ocpsoft.socialpm.gwt.client.local.view.FluidLayout;
import com.ocpsoft.socialpm.gwt.client.local.view.HomeView;

public class HomeActivity extends AbstractActivity implements FluidLayout.Presenter
{
   private final ClientFactory clientFactory;

   public HomeActivity(HomePlace place, ClientFactory clientFactory)
   {
      System.out.println("Created HomeActivity");
      this.clientFactory = clientFactory;
   }

   @Override
   public void start(AcceptsOneWidget containerWidget, EventBus eventBus)
   {
      HomeView homeView = clientFactory.getHomeView();
      homeView.setPresenter(this);

      System.out.println("Started HomeActivity");

      homeView.getBrandLink().setText("SocialPM");
      homeView.getBrandLink().setHref(HistoryConstants.HOME());
      homeView.getBrandLink().setEnabled(false);

      homeView.getGreeting().setHeading("Wilkommen!");
      homeView.getGreeting().setContent("Type a message and click to get started.");


      containerWidget.setWidget(homeView.asWidget());
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