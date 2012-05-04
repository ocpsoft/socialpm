package com.ocpsoft.socialpm.gwt.client.local.view.signup;

import javax.enterprise.context.ApplicationScoped;

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
import com.ocpsoft.socialpm.gwt.client.local.view.FixedLayoutView;

@ApplicationScoped
public class SignupViewImpl extends FixedLayoutView implements SignupView
{
   final TextBox username = new TextBox();
   final PasswordTextBox password = new PasswordTextBox();
   Anchor submit = new Anchor("Login");

   private SignupView.Presenter presenter;

   public SignupViewImpl()
   {
      super();
   }

   @Override
   public void setup()
   {
      HorizontalPanel login = new HorizontalPanel();
      submit.setStyleName("btn primary");

      VerticalPanel left = new VerticalPanel();
      left.add(new Label("Username"));
      left.add(username);
      left.add(new Label("Password"));
      left.add(password);
      login.add(left);

      super.content.add(login);
      super.content.add(submit);

      KeyPressHandler handler = new KeyPressHandler() {
         @Override
         public void onKeyPress(KeyPressEvent event)
         {
            if (event.getCharCode() == KeyCodes.KEY_ENTER)
            {
               presenter.doLogin(getUsername(), getPassword());
            }
         }
      };
      username.addKeyPressHandler(handler);
      password.addKeyPressHandler(handler);

      submit.addClickHandler(new ClickHandler() {
         @Override
         public void onClick(ClickEvent event)
         {
            presenter.doLogin(getUsername(), getPassword());
         }
      });
   }

   @Override
   public void focusUsername()
   {
      username.setFocus(true);
   }

   @Override
   public void setPresenter(SignupView.Presenter presenter)
   {
      this.presenter = presenter;
   }

   @Override
   public SignupView.Presenter getPresenter()
   {
      return presenter;
   }

   @Override
   public String getPassword()
   {
      return password.getText();
   }

   @Override
   public String getUsername()
   {
      return username.getText();
   }

   @Override
   public void clearForm()
   {
      username.setText("");
      password.setText("");
   }

}
