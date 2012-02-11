package com.ocpsoft.socialpm.gwt.client.local.view;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.errai.ioc.client.api.Caller;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.TextBox;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.gwt.client.local.view.component.HeroPanel;
import com.ocpsoft.socialpm.gwt.client.local.view.component.NavBar;
import com.ocpsoft.socialpm.gwt.client.local.view.component.NavLink;
import com.ocpsoft.socialpm.gwt.client.shared.HelloMessage;
import com.ocpsoft.socialpm.gwt.client.shared.Response;
import com.ocpsoft.socialpm.gwt.client.shared.rpc.AuthenticationService;

@ApplicationScoped
public class LoginView extends FixedLayoutView
{
   HeroPanel greeting = new HeroPanel();
   NavBar topnav = new NavBar();
   NavLink brandLink = new NavLink();
   NavLink signupLink = new NavLink("Join the party", HistoryConstants.SIGNUP);
   private final Anchor sendMessageButton = new Anchor("Send it »");
   private final TextBox messageBox = new TextBox();

   @Inject
   private Caller<AuthenticationService> loginService;

   public LoginView()
   {
      topnav.setFixedTop(true);
      topnav.addBrand(brandLink);

      topnav.add(signupLink);

      header.add(topnav);
      content.add(greeting);

      setupInputs();
   }

   private void setupInputs()
   {
      greeting.getUnder().add(messageBox);
      messageBox.addKeyPressHandler(new KeyPressHandler() {

         @Override
         public void onKeyPress(KeyPressEvent event)
         {
            if (KeyCodes.KEY_ENTER == event.getCharCode())
            {
               System.out.println("Handling enter event! " + messageEvent);
               event.preventDefault();
               fireMessage();
            }
         }
      });

      sendMessageButton.addStyleName("btn btn-primary btn-large");
      sendMessageButton.addClickHandler(new ClickHandler() {
         @Override
         public void onClick(ClickEvent event)
         {
            System.out.println("Handling click event! " + messageEvent);
            event.preventDefault();

            fireMessage();
         }
      });
      greeting.addAction(sendMessageButton);
   }

   private void fireMessage()
   {
      String text = messageBox.getText();
      HelloMessage msg = new HelloMessage(text);
      messageEvent.fire(msg);
      System.out.println("Done handling click event!");
   }

   /*
    * Getters & Setters
    */
   public NavLink getBrandLink()
   {
      return brandLink;
   }

   public NavLink getSignupLink()
   {
      return signupLink;
   }

   public HeroPanel getGreeting()
   {
      return greeting;
   }

   @Inject
   private Event<HelloMessage> messageEvent;

   public void response(@Observes Response event)
   {
      System.out.println("Observed response " + event.getMessage());
      getGreeting().setContent("Message from server: " + event.getMessage());
   }

   /*
    * Builders
    */

}
