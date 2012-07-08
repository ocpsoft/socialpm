package com.ocpsoft.socialpm.gwt.client.local.view.signup;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.ocpsoft.socialpm.gwt.client.local.ClientFactory;
import com.ocpsoft.socialpm.gwt.client.local.view.login.LoginView;

@Dependent
public class SignupActivity extends AbstractActivity implements SignupView.Presenter
{
   private final ClientFactory clientFactory;
   private final SignupView signupView;

   @Inject
   public SignupActivity(ClientFactory clientFactory, SignupView signupView)
   {
      this.signupView = signupView;
      this.clientFactory = clientFactory;
   }

   @Override
   public void start(AcceptsOneWidget containerWidget, EventBus eventBus)
   {
      signupView.setPresenter(this);

      containerWidget.setWidget(signupView.asWidget());
   }

   @Override
   public String mayStop()
   {
      return null;
   }

   @Override
   public void goTo(Place place)
   {
      clientFactory.getPlaceController().goTo(place);
   }

}