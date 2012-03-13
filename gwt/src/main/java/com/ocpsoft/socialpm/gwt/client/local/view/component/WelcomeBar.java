package com.ocpsoft.socialpm.gwt.client.local.view.component;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.ocpsoft.socialpm.model.user.Profile;

public class WelcomeBar extends Composite
{
   interface WelcomeBarBinder extends UiBinder<Widget, WelcomeBar>
   {
   }

   private static WelcomeBarBinder binder = GWT.create(WelcomeBarBinder.class);

   @UiField
   Span greeting;

   @UiField
   GravatarImage gravatar;

   @UiField
   ProfileLink profileNameLink;

   
   public WelcomeBar()
   {
      initWidget(binder.createAndBindUi(this));
      gravatar.setSize(90);
      greeting.setInnerText("Everything is fine.");
   }

   public void setProfile(Profile profile)
   {
      gravatar.setProfile(profile);
      profileNameLink.setProfile(profile);
   }

}
