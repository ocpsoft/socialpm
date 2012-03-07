package com.ocpsoft.socialpm.gwt.client.local.view.component;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.ocpsoft.socialpm.gwt.client.local.util.Gravatar;
import com.ocpsoft.socialpm.model.user.Profile;

public class WelcomeBar extends Composite
{
   interface WelcomeBarBinder extends UiBinder<Widget, WelcomeBar>
   {
   }

   private static WelcomeBarBinder binder = GWT.create(WelcomeBarBinder.class);

   @UiField
   Span greeting;

   Image profileImage = new Image();

   @UiField
   ProfileLink profileImageLink;

   @UiField
   ProfileLink profileNameLink;

   public WelcomeBar()
   {
      initWidget(binder.createAndBindUi(this));
      greeting.setText("Everything is fine.");
      profileImage.setHeight("90px");
      profileImage.setWidth("90px");
   }

   public void setProfile(Profile profile)
   {
      System.out.println("Set welcomebar profile");
      profileImageLink.setProfile(profile);
      profileNameLink.setProfile(profile);
      profileImage.setUrl(Gravatar.forEmail(profile.getEmail(), 90));
      profileImageLink.getElement().setInnerHTML("");
      profileImageLink.getElement().appendChild(profileImage.getElement());
   }

}
