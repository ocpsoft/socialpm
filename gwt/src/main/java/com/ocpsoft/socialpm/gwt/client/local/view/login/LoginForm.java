package com.ocpsoft.socialpm.gwt.client.local.view.login;

import org.jboss.errai.ui.shared.api.annotations.Replace;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;

@Templated
public class LoginForm extends Composite
{
   @Replace
   private TextBox username;

   @Replace
   private PasswordTextBox password;

   @Replace
   private Anchor submit;

   public void clear()
   {
      username.setText("");
      password.setText("");
   }

   public TextBox getUsername()
   {
      return username;
   }

   public PasswordTextBox getPassword()
   {
      return password;
   }

   public Anchor getSubmit()
   {
      return submit;
   }
}
