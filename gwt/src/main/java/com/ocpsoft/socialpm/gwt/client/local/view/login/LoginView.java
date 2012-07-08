package com.ocpsoft.socialpm.gwt.client.local.view.login;

import com.ocpsoft.socialpm.gwt.client.local.view.FixedLayout;

public interface LoginView extends FixedLayout
{
   public interface Presenter extends FixedLayout.FixedPresenter
   {
      void doLogin(String username, String password);
   }

   void focusUsername();

   Presenter getPresenter();

   void setPresenter(Presenter presenter);

   String getPassword();

   String getUsername();

   void clearForm();
}
