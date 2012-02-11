package com.ocpsoft.socialpm.gwt.client.local.view.component;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SigninStatus extends Composite
{
   interface SigninStatusBinder extends UiBinder<Widget, SigninStatus>
   {
   }

   private static SigninStatusBinder binder = GWT.create(SigninStatusBinder.class);

   @UiField
   Span signedIn;

   @UiField
   Span signedOut;

   private final Anchor profileLink = new Anchor();
   private final Anchor signinLink = new Anchor("Sign in");

   public SigninStatus()
   {
      initWidget(binder.createAndBindUi(this));

      signedIn.add(new Span("Signed in as "));
      signedIn.add(profileLink);
      signedIn.setVisible(false);

      signedOut.add(signinLink);
      signedOut.setVisible(true);
   }

   public SigninStatus setSignedIn(String username)
   {
      profileLink.setText(username);
      signedIn.setVisible(true);
      signedOut.setVisible(false);
      return this;
   }

   public SigninStatus setSignedOut(String text)
   {
      signedIn.setVisible(false);
      signedOut.setVisible(true);
      return this;
   }

   public void addSigninClickHandler(ClickHandler clickHandler)
   {
      signinLink.addClickHandler(clickHandler);
   }

}
