package com.ocpsoft.socialpm.gwt.client.local.view;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.errai.bus.client.api.ErrorCallback;
import org.jboss.errai.bus.client.api.Message;
import org.jboss.errai.bus.client.api.RemoteCallback;
import org.jboss.errai.ioc.client.api.Caller;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.gwt.client.local.template.events.DisplayEvent;
import com.ocpsoft.socialpm.gwt.client.local.template.events.DisplayHandler;
import com.ocpsoft.socialpm.gwt.client.local.template.events.HideEvent;
import com.ocpsoft.socialpm.gwt.client.local.template.events.OnHideHandler;
import com.ocpsoft.socialpm.gwt.client.local.view.component.HeroPanel;
import com.ocpsoft.socialpm.gwt.client.local.view.component.ModalDialog;
import com.ocpsoft.socialpm.gwt.client.local.view.component.NavBar;
import com.ocpsoft.socialpm.gwt.client.local.view.component.NavLink;
import com.ocpsoft.socialpm.gwt.client.local.view.component.SigninStatus;
import com.ocpsoft.socialpm.gwt.client.local.view.component.Span;
import com.ocpsoft.socialpm.gwt.client.shared.HelloMessage;
import com.ocpsoft.socialpm.gwt.client.shared.Response;
import com.ocpsoft.socialpm.gwt.client.shared.rpc.AuthenticationService;
import com.ocpsoft.socialpm.model.user.Profile;

@ApplicationScoped
public class HomeView extends FixedLayoutView
{
   HeroPanel greeting = new HeroPanel();
   NavBar topnav = new NavBar();
   NavLink brandLink = new NavLink();
   NavLink signupLink = new NavLink("Join the party", HistoryConstants.SIGNUP);
   private final Anchor sendMessageButton = new Anchor("Send it »");
   private final TextBox messageBox = new TextBox();
   
   @Inject
   private Caller<AuthenticationService> loginService;
   
   public HomeView()
   {
      topnav.setFixedTop(true);
      topnav.addBrand(brandLink);

      topnav.add(signupLink);
      topnav.addRight(buildAuthentication());
      
      header.add(topnav);
      content.add(greeting);
      greeting.getUnder().add(messageBox);
      
      sendMessageButton.addStyleName("btn btn-primary btn-large");
      sendMessageButton.addClickHandler(sendMessage);
      greeting.addAction(sendMessageButton);
   }


   ClickHandler sendMessage = new ClickHandler() {
      @Override
      public void onClick(ClickEvent event)
      {
         System.out.println("Handling click event! " + messageEvent);
         event.preventDefault();

         String text = messageBox.getText();
         HelloMessage msg = new HelloMessage(text);
         messageEvent.fire(msg);
         System.out.println("Done handling click event!");
      }
   };
   
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

   private SigninStatus buildAuthentication()
   {
      final SigninStatus auth = new SigninStatus();
      auth.addSigninClickHandler(new ClickHandler() {
         @Override
         public void onClick(ClickEvent event)
         {
            final ModalDialog loginDialog = new ModalDialog();

            HorizontalPanel login = new HorizontalPanel();
            final TextBox username = new TextBox();
            final PasswordTextBox password = new PasswordTextBox();
            Anchor submit = new Anchor("Login");
            submit.setStyleName("btn primary");

            submit.addClickHandler(new ClickHandler() {
               @Override
               public void onClick(ClickEvent event)
               {
                  RemoteCallback<Profile> success = new RemoteCallback<Profile>() {

                     @Override
                     public void callback(Profile profile)
                     {
                        if (profile != null)
                        {
                           System.out.println("Profile = " + profile.getUsername());
                           auth.setSignedIn(profile.getUsername());
                           loginDialog.hide();
                        }
                        else
                        {
                           Window.alert("WRONG! Try again...");
                           System.out.println("Profile was null!");
                        }
                     }

                  };
                  ErrorCallback failure = new ErrorCallback() {

                     @Override
                     public boolean error(Message message, Throwable throwable)
                     {
                        System.out.println("Failure!");
                        return false;
                     }
                  };

                  System.out.println("Clicked!");
                  System.out.println(username.getText() + "/" + password.getText());

                  loginService.call(success, failure).login(username.getText(), password.getText());
                  System.out.println("After RPC!");
               }
            });

            VerticalPanel left = new VerticalPanel();
            left.add(new Label("Username"));
            left.add(username);
            left.add(new Label("Password"));
            left.add(password);
            login.add(left);

            loginDialog.addHeader(new Span("Sign in"));
            loginDialog.addContent(login);
            loginDialog.addFooter(submit);

            RootPanel.get().add(loginDialog);

            loginDialog.addOnHideHandler(new OnHideHandler() {
               @Override
               public void handleOnHide(HideEvent source)
               {
                  History.back();
               }
            });

            loginDialog.addDisplayHandler(new DisplayHandler() {
               @Override
               public void handleOnDisplay(DisplayEvent source)
               {
                  History.newItem("login");
               }
            });

            loginDialog.display();
            // auth.setSignedIn("Foobar");
         }
      });
      return auth;
   }
   
}
