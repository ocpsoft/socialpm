package com.ocpsoft.socialpm.gwt.client.local.view;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.errai.bus.client.api.ErrorCallback;
import org.jboss.errai.bus.client.api.Message;
import org.jboss.errai.bus.client.api.RemoteCallback;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import com.ocpsoft.socialpm.gwt.client.local.view.events.LoginEvent;
import com.ocpsoft.socialpm.model.user.Profile;

@ApplicationScoped
public class LoginViewImpl extends FixedLayoutView implements LoginView
{
   private Presenter presenter;

   @Inject
   public LoginViewImpl(final ServiceFactory serviceFactory, final EventsFactory eventFactory)
   {
      super(serviceFactory, eventFactory);
      System.out.println("Construct LoginView");

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
                     eventFactory.getLoginEvent().fire(new LoginEvent(profile));
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
                  System.out.println("Failure!");
                  return false;
               }
            };

            serviceFactory.getAuthService().call(success, failure).login(username.getText(), password.getText());
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

   @Override
   public void setPresenter(LoginView.Presenter presenter)
   {
      this.presenter = presenter;
   }

   public void onLogin(@Observes LoginEvent event)
   {
      if (presenter != null)
         presenter.handleLogin(event);
   }
}
