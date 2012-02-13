package com.ocpsoft.socialpm.gwt.client.local.view;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.ocpsoft.socialpm.gwt.client.local.EventsFactory;
import com.ocpsoft.socialpm.gwt.client.local.ServiceFactory;

@ApplicationScoped
public class LoginViewImpl extends FixedLayoutView implements LoginView
{
   private Presenter presenter;

   @Inject
   public LoginViewImpl(ServiceFactory serviceFactory, EventsFactory eventFactory)
   {
      super(serviceFactory, eventFactory);
      System.out.println("Construct LoginView");
   }

   @Override
   public void setPresenter(LoginView.Presenter presenter)
   {
      this.presenter = presenter;
   }
}
