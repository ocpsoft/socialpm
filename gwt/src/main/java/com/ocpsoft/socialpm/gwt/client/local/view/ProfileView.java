package com.ocpsoft.socialpm.gwt.client.local.view;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.ocpsoft.socialpm.gwt.client.local.view.component.HeroPanel;
import com.ocpsoft.socialpm.gwt.client.local.view.component.NavLink;
import com.ocpsoft.socialpm.gwt.client.local.view.presenter.AuthenticationAware;
import com.ocpsoft.socialpm.model.user.Profile;

public interface ProfileView extends IsWidget
{
   public interface Presenter extends AuthenticationAware
   {
      void goTo(Place place);
   }

   void setPresenter(Presenter presenter);

   NavLink getBrandLink();

   HeroPanel getGreeting();

   void setProfile(Profile response);

}
