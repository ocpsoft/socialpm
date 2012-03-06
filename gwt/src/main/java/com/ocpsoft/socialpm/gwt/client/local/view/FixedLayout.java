package com.ocpsoft.socialpm.gwt.client.local.view;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.ocpsoft.socialpm.gwt.client.local.view.presenter.AuthenticationAware;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public interface FixedLayout extends IsWidget
{
   void setPresenter(Presenter presenter);

   public interface Presenter extends AuthenticationAware
   {
      void goTo(Place place);
   };
}
