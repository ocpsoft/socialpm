package com.ocpsoft.socialpm.gwt.client.local.view.profile;

import javax.inject.Inject;

import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.databinding.client.api.InitialState;
import org.jboss.errai.ui.shared.api.annotations.DataField;
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
   @Inject
   @DataField
   private TextBox username;

   @Inject
   @DataField
   private TextBox email;

   @Inject
   @DataField
   private TextArea bio;

   @Inject
   @DataField
   private PasswordTextBox password;

   @Inject
   @DataField("emailPrivacy")
   private CheckBox emailPrivacy;

   @Inject
   @DataField
   private Button submit;

   @Inject
   @DataField
   private Button cancel;

   @Inject
   private DataBinder<Profile> binder;

   public void setProfile(Profile profile)
   {
      if (binder != null)
         binder.unbind();

      binder = DataBinder.forModel(profile,
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
