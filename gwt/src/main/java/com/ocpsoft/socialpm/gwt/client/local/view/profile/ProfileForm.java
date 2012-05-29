package com.ocpsoft.socialpm.gwt.client.local.view.profile;

import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.databinding.client.api.InitialState;
import org.jboss.errai.ui.shared.api.annotations.Replace;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.ocpsoft.socialpm.model.user.Profile;

@Templated
public class ProfileForm extends Composite
{
   @Replace
   private TextBox username;

   @Replace
   private TextBox email;

   @Replace
   private TextArea bio;

   @Replace
   private PasswordTextBox password;

   @Replace("emailPrivacy")
   private CheckBox emailPrivacy;

   @Replace
   private Button submit;

   @Replace
   private Button cancel;

   private DataBinder<Profile> binder;


   public void setProfile(Profile profile)
   {
      if (binder != null)
         binder.unbind();

      binder = new DataBinder<Profile>(profile,
               InitialState.FROM_MODEL);
      binder.bind(bio, "bio");
      binder.bind(username, "username");
      binder.bind(email, "email");
      binder.bind(password, "password");
      binder.bind(emailPrivacy, "emailSecret");
   }

   public void clear()
   {
      username.setText("");
      password.setText("");
   }

   /*
    * Getters & Setters
    */

   public Button getSubmit()
   {
      return submit;
   }
   
   public Button getCancel()
   {
      return cancel;
   }

   public Profile getProfile()
   {
      return binder.getModel();
   }
}
