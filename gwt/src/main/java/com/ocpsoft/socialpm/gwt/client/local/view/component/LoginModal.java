package com.ocpsoft.socialpm.gwt.client.local.view.component;

import java.util.ArrayList;
import java.util.List;

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
import com.ocpsoft.socialpm.gwt.client.local.view.events.DisplayEvent;
import com.ocpsoft.socialpm.gwt.client.local.view.events.DisplayHandler;
import com.ocpsoft.socialpm.gwt.client.local.view.events.HideEvent;
import com.ocpsoft.socialpm.gwt.client.local.view.events.OnHideHandler;
import com.ocpsoft.socialpm.gwt.client.local.view.events.SignedInHandler;
import com.ocpsoft.socialpm.gwt.client.shared.rpc.AuthenticationService;
import com.ocpsoft.socialpm.model.user.Profile;

public class LoginModal extends SigninStatus
{
   private final List<SignedInHandler> signinHandlers = new ArrayList<SignedInHandler>();

   public LoginModal(final Caller<AuthenticationService> service)
   {
      addSigninClickHandler(new ClickHandler() {
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
                           for (SignedInHandler handler : signinHandlers) {
                              handler.handleSignedIn(profile);
                           }

                           setSignedIn(profile);
                           loginDialog.hide();
                        }
                        else
                        {
                           Window.alert("WRONG! Try again...");
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

                  service.call(success, failure).login(username.getText(), password.getText());
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
                  System.out.println("Closed Login Modal");
                  History.back();
                  RootPanel.get().remove(loginDialog);
               }
            });

            loginDialog.addDisplayHandler(new DisplayHandler() {
               @Override
               public void handleOnDisplay(DisplayEvent source)
               {
                  System.out.println("Displayed Login Modal");
               }
            });

            loginDialog.display();
         }
      });
   }

   public void addSignedInHandler(SignedInHandler handler)
   {
      this.signinHandlers.add(handler);
   }

}
