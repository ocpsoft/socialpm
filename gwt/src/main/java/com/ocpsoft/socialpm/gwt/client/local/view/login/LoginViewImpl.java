package com.ocpsoft.socialpm.gwt.client.local.view.login;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.ocpsoft.socialpm.gwt.client.local.view.FixedLayoutView;

@ApplicationScoped
public class LoginViewImpl extends FixedLayoutView implements LoginView
{
   @Inject
   private LoginForm loginForm;
   
   private LoginView.Presenter presenter;

   public LoginViewImpl()
   {
      super();
   }

   @Override
   public void setup()
   {
      content.add(loginForm);

      KeyPressHandler handler = new KeyPressHandler() {
         @Override
         public void onKeyPress(KeyPressEvent event)
         {
            if (event.getCharCode() == KeyCodes.KEY_ENTER) {
               event.preventDefault();
               presenter.doLogin(getUsername(), getPassword());
            }
         }
      };

      loginForm.getUsername().addKeyPressHandler(handler);
      loginForm.getPassword().addKeyPressHandler(handler);

      loginForm.getSubmit().addClickHandler(new ClickHandler() {
         @Override
         public void onClick(ClickEvent event)
         {
            event.preventDefault();
            presenter.doLogin(getUsername(), getPassword());
         }
      });
   }

   @Override
   public void focusUsername()
   {
      loginForm.getUsername().setFocus(true);
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

   @Override
   public String getPassword()
   {
      return loginForm.getPassword().getText();
   }

   @Override
   public String getUsername()
   {
      return loginForm.getUsername().getText();
   }

   @Override
   public void clearForm()
   {
      loginForm.clear();
   }

}
