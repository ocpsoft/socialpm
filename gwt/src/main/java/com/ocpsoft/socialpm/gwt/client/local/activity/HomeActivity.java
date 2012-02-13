package com.ocpsoft.socialpm.gwt.client.local.activity;

import javax.enterprise.event.Event;

import org.jboss.errai.ioc.client.api.Caller;

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
import com.ocpsoft.socialpm.gwt.client.local.view.events.SignedInHandler;
import com.ocpsoft.socialpm.gwt.client.shared.HelloMessage;
import com.ocpsoft.socialpm.gwt.client.shared.rpc.AuthenticationService;
import com.ocpsoft.socialpm.model.user.Profile;

public class HomeActivity extends AbstractActivity implements HomeView.Presenter
{
   private final ClientFactory clientFactory;
   private final Caller<AuthenticationService> authService;
   private final Event<HelloMessage> messageEvent;

   public HomeActivity(HomePlace place, ClientFactory clientFactory, Caller<AuthenticationService> authService,
            Event<HelloMessage> messageEvent)
   {
      this.authService = authService;
      System.out.println("Created HomeActivity");
      this.clientFactory = clientFactory;
      this.messageEvent = messageEvent;
   }

   @Override
   public void start(AcceptsOneWidget containerWidget, EventBus eventBus)
   {
      final HomeView homeView = clientFactory.getHomeView();
      homeView.setPresenter(this);

      System.out.println("Started HomeActivity");

      homeView.getBrandLink().setText("SocialPM");
      homeView.getBrandLink().setHref(HistoryConstants.HOME());
      homeView.getBrandLink().setEnabled(false);

      homeView.getGreeting().setHeading("Wilkommen!");
      homeView.getGreeting().setContent("Type a message and click to get started.");

      homeView.getLoginModal().setAuthService(authService);
      homeView.getLoginModal().addSignedInHandler(new SignedInHandler() {
         @Override
         public void handleSignedIn(Profile profile)
         {
            homeView.getContent().remove(homeView.getGreeting());
         }
      });

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
               System.out.println("Handling enter event! " + messageEvent);
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
            System.out.println("Handling click event! " + messageEvent);
            event.preventDefault();

            fireMessage(homeView.getMessageBox().getText());
         }
      });
      homeView.getGreeting().addAction(homeView.getSendMessageButton());
   }

   private void fireMessage(String text)
   {
      HelloMessage msg = new HelloMessage(text);
      messageEvent.fire(msg);
      System.out.println("Done handling click event!");
   }
}