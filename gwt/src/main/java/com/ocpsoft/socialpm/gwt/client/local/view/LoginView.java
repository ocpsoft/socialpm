package com.ocpsoft.socialpm.gwt.client.local.view;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.errai.ioc.client.api.Caller;

import com.ocpsoft.socialpm.gwt.client.shared.rpc.AuthenticationService;

@ApplicationScoped
public class LoginView extends FixedLayoutView
{
   @Inject
   public LoginView(Caller<AuthenticationService> loginService)
   {
      super(loginService);
      System.out.println("Construct LoginView");
   }
}
