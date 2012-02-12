package com.ocpsoft.socialpm.gwt.client.local.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.ocpsoft.socialpm.gwt.client.local.ClientFactory;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.gwt.client.local.places.ViewProfilePlace;
import com.ocpsoft.socialpm.gwt.client.local.view.FluidLayout;
import com.ocpsoft.socialpm.gwt.client.local.view.ViewProfileView;

public class ViewProfileActivity extends AbstractActivity implements FluidLayout.Presenter
{
   private final ClientFactory clientFactory;
   private final String username;

   public ViewProfileActivity(ViewProfilePlace place, ClientFactory clientFactory)
   {
      System.out.println("Created ViewProfileActivity");
      this.clientFactory = clientFactory;
      this.username = place.getUsername();
   }

   @Override
   public void start(AcceptsOneWidget containerWidget, EventBus eventBus)
   {
      ViewProfileView profileView = clientFactory.getViewProfileView();
      profileView.setPresenter(this);

      System.out.println("Started ViewProfileActivity");

      profileView.getBrandLink().setText("SocialPM");
      profileView.getBrandLink().setHref(HistoryConstants.HOME());
      profileView.getBrandLink().setEnabled(true);

      profileView.getGreeting().setHeading("Loading...");
      profileView.setUsername(username);

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