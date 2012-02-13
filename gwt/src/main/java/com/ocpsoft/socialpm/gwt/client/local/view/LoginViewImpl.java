package com.ocpsoft.socialpm.gwt.client.local.view;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class LoginViewImpl extends FixedLayoutView implements LoginView
{
   private Presenter presenter;

   @Inject
   public LoginViewImpl()
   {
      System.out.println("Construct LoginView");
   }

   @Override
   public void setPresenter(LoginView.Presenter presenter)
   {
      this.presenter = presenter;
   }
}
