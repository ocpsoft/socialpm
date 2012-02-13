package com.ocpsoft.socialpm.gwt.client.local.view;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.ocpsoft.socialpm.gwt.client.local.view.component.NavLink;

public interface LoginView extends IsWidget
{

   public interface Presenter
   {
      void goTo(Place place);
   }

   void setPresenter(Presenter presenter);

   NavLink getBrandLink();
}
