package com.ocpsoft.socialpm.gwt.client.local.view.login;

import javax.inject.Inject;

import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;

@Templated
public class LoginForm extends Composite
{
   @DataField
   private Element form = DOM.createForm();

   @Inject
   @DataField
   private TextBox username;

   @Inject
   @DataField
   private PasswordTextBox password;

   @Inject
   @DataField("remember")
   private CheckBox rememberMe;

   @Inject
   @DataField
   private Button submit;

   public void clear()
   {
      username.setText("");
      password.setText("");
      DOM.setEventListener(form, new EventListener() {
         @Override
         public void onBrowserEvent(Event event)
         {
            event.preventDefault();
         }
      });
   }

   public TextBox getUsername()
   {
      return username;
   }

   public PasswordTextBox getPassword()
   {
      return password;
   }

   public Button getSubmit()
   {
      return submit;
   }

   public CheckBox getRememberMe()
   {
      return rememberMe;
   }
}
