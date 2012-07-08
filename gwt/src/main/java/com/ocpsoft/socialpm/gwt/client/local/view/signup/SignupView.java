package com.ocpsoft.socialpm.gwt.client.local.view.signup;

import com.ocpsoft.socialpm.gwt.client.local.view.FixedLayout;

public interface SignupView extends FixedLayout
{
   public interface Presenter extends FixedLayout.FixedPresenter
   {
   }

   void setPresenter(Presenter presenter);
}
