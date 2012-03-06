package com.ocpsoft.socialpm.gwt.client.local.view;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.errai.bus.client.api.ErrorCallback;
import org.jboss.errai.bus.client.api.Message;
import org.jboss.errai.bus.client.api.RemoteCallback;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ocpsoft.socialpm.gwt.client.local.EventsFactory;
import com.ocpsoft.socialpm.gwt.client.local.ServiceFactory;
import com.ocpsoft.socialpm.model.user.Profile;

@ApplicationScoped
public class LoginViewImpl extends FixedLayoutView implements LoginView
{
   @Inject
   private ServiceFactory serviceFactory;

   @Inject
   private EventsFactory eventFactory;

   @Override
   public void setup()
   {
      System.out.println("Construct LoginView");

      HorizontalPanel login = new HorizontalPanel();
      final TextBox username = new TextBox();
      final PasswordTextBox password = new PasswordTextBox();

      KeyPressHandler handler = new KeyPressHandler() {
         @Override
         public void onKeyPress(KeyPressEvent event)
         {
            if (event.getCharCode() == KeyCodes.KEY_ENTER)
            {
               doLogin(serviceFactory, eventFactory, username, password);
            }
         }
      };
      username.addKeyPressHandler(handler);
      password.addKeyPressHandler(handler);

      Anchor submit = new Anchor("Login");
      submit.setStyleName("btn primary");

      submit.addClickHandler(new ClickHandler() {
         @Override
         public void onClick(ClickEvent event)
         {
            doLogin(serviceFactory, eventFactory, username, password);
         }
      });

      VerticalPanel left = new VerticalPanel();
      left.add(new Label("Username"));
      left.add(username);
      left.add(new Label("Password"));
      left.add(password);
      login.add(left);

      super.content.add(login);
      super.content.add(submit);
   }

   private void doLogin(final ServiceFactory serviceFactory, final EventsFactory eventFactory,
            final TextBox username, final PasswordTextBox password)
   {
      RemoteCallback<Profile> success = new RemoteCallback<Profile>() {

         @Override
         public void callback(Profile profile)
         {
            if (profile != null)
            {
               eventFactory.fireLoginEvent(profile);
               History.back();
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
            return false;
         }
      };

      serviceFactory.getAuthService().call(success, failure).login(username.getText(), password.getText());
   }

}
