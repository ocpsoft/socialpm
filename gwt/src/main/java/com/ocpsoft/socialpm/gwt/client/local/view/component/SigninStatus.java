package com.ocpsoft.socialpm.gwt.client.local.view.component;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.model.user.Profile;

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

   private final NavLink profileLink = new NavLink();
   private final NavLink signinLink = new NavLink("Sign in", HistoryConstants.LOGIN());

   public SigninStatus()
   {
      initWidget(binder.createAndBindUi(this));

      signedIn.add(new Span("Signed in as "));
      signedIn.add(profileLink);
      signedIn.setVisible(false);

      signedOut.add(signinLink);
      signedOut.setVisible(true);
   }

   public SigninStatus setSignedIn(Profile profile)
   {
      profileLink.setText(profile.getUsername());
      profileLink.setTargetHistoryToken(HistoryConstants.PROFILE(profile));
      signedIn.setVisible(true);
      signedOut.setVisible(false);
      return this;
   }

   public SigninStatus setSignedOut()
   {
      signedIn.setVisible(false);
      signedOut.setVisible(true);
      return this;
   }

   public void addSigninClickHandler(ClickHandler clickHandler)
   {
      signinLink.addClickHandler(clickHandler);
   }

   public Anchor getProfileLink()
   {
      return profileLink;
   }

   public Anchor getSigninLink()
   {
      return signinLink;
   }

}
