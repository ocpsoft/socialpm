package com.ocpsoft.socialpm.gwt.client.local.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.ocpsoft.socialpm.gwt.client.local.ClientFactory;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.gwt.client.local.places.HomePlace;
import com.ocpsoft.socialpm.gwt.client.local.view.HomeView;
import com.ocpsoft.socialpm.gwt.client.local.view.events.LoginEvent;
import com.ocpsoft.socialpm.gwt.client.shared.HelloMessage;

public class HomeActivity extends AbstractActivity implements HomeView.Presenter
{
   private final ClientFactory clientFactory;

   public HomeActivity(HomePlace place, ClientFactory clientFactory)
   {
      this.clientFactory = clientFactory;
      System.out.println("Created HomeActivity");
   }

   @Override
   public void start(AcceptsOneWidget containerWidget, EventBus eventBus)
   {
      final HomeView homeView = clientFactory.getHomeView();
      System.out.println("HomeView is " + homeView);
      homeView.setPresenter(this);

      System.out.println("Started HomeActivity");

      homeView.getBrandLink().setText("SocialPM");
      homeView.getBrandLink().setHref(HistoryConstants.HOME());
      homeView.getBrandLink().setEnabled(false);

      homeView.getGreeting().setHeading("Wilkommen!");
      homeView.getGreeting().setContent("Type a message and click to get started.");

      homeView.getContent().add(homeView.getGreeting());
      setupInputs(homeView);

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

   private void setupInputs(final HomeView homeView)
   {
      homeView.getGreeting().getUnder().add(homeView.getMessageBox());
      homeView.getMessageBox().addKeyPressHandler(new KeyPressHandler() {

         @Override
         public void onKeyPress(KeyPressEvent event)
         {
            if (KeyCodes.KEY_ENTER == event.getCharCode())
            {
               event.preventDefault();
               fireMessage(homeView.getMessageBox().getText());
            }
         }
      });

      homeView.getSendMessageButton().addStyleName("btn btn-primary btn-large");
      homeView.getSendMessageButton().addClickHandler(new ClickHandler() {
         @Override
         public void onClick(ClickEvent event)
         {
            event.preventDefault();

            fireMessage(homeView.getMessageBox().getText());
         }
      });
      homeView.getGreeting().addAction(homeView.getSendMessageButton());
   }

   private void fireMessage(String text)
   {
      HelloMessage msg = new HelloMessage(text);
      clientFactory.getEventFactory().getMessageEvent().fire(msg);
      System.out.println("Done handling click event!");
   }

   @Override
   public void handleLogin(LoginEvent event)
   {

   }
}