package com.ocpsoft.socialpm.gwt.client.local.view;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.ocpsoft.socialpm.gwt.client.local.view.component.NavLink;
import com.ocpsoft.socialpm.gwt.client.local.view.component.SigninStatus;
import com.ocpsoft.socialpm.gwt.client.local.view.presenter.AuthenticationAware;

public interface LoginView extends IsWidget
{

   public interface Presenter extends AuthenticationAware
   {
      void goTo(Place place);
   }

   void setPresenter(Presenter presenter);

   NavLink getBrandLink();

   SigninStatus getSigninStatus();

}
