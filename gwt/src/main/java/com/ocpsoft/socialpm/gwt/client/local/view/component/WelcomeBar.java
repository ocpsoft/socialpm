package com.ocpsoft.socialpm.gwt.client.local.view.component;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.ocpsoft.socialpm.model.user.Profile;

public class WelcomeBar extends Composite
{
   interface WelcomeBarBinder extends UiBinder<Widget, WelcomeBar>
   {}

   private static WelcomeBarBinder binder = GWT.create(WelcomeBarBinder.class);

   public WelcomeBar()
   {
      initWidget(binder.createAndBindUi(this));
   }

   public void setSignedIn(Profile profile)
   {
      // TOOD implement
   }
   
}
