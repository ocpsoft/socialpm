package com.ocpsoft.socialpm.gwt.client.local.view;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ocpsoft.socialpm.gwt.client.local.EventsFactory;
import com.ocpsoft.socialpm.gwt.client.local.ServiceFactory;

@ApplicationScoped
public class LoginViewImpl extends FixedLayoutView implements LoginView
{
   @Inject
   private ServiceFactory serviceFactory;

   @Inject
   private EventsFactory eventFactory;
   
   final TextBox username = new TextBox();
   final PasswordTextBox password = new PasswordTextBox();

   private LoginView.Presenter presenter;
   
   public LoginViewImpl()
   {
      super();
   }

   @Override
   public void setup()
   {
      System.out.println("Construct LoginView");

      HorizontalPanel login = new HorizontalPanel();

      KeyPressHandler handler = new KeyPressHandler() {
         @Override
         public void onKeyPress(KeyPressEvent event)
         {
            if (event.getCharCode() == KeyCodes.KEY_ENTER)
            {
               getPresenter().doLogin(username.getText(), password.getText());
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
            presenter.doLogin(username.getText(), password.getText());
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
   public void focusUsername()
   {
      username.setFocus(true);
   }

   @Override
   public void setPresenter(LoginView.Presenter presenter)
   {
      this.presenter = presenter;
   }
   
   @Override
   public LoginView.Presenter getPresenter()
   {
      return presenter;
   }

}
