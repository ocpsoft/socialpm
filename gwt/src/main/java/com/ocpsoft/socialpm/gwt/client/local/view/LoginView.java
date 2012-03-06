package com.ocpsoft.socialpm.gwt.client.local.view;

import com.ocpsoft.socialpm.gwt.client.local.view.component.NavLink;
import com.ocpsoft.socialpm.gwt.client.local.view.component.SigninStatus;

public interface LoginView extends FixedLayout
{
   public interface Presenter extends FixedLayout.Presenter
   {
   }

   NavLink getBrandLink();

   SigninStatus getSigninStatus();

}
