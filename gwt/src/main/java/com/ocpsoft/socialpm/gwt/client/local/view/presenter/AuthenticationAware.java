package com.ocpsoft.socialpm.gwt.client.local.view.presenter;

import com.ocpsoft.socialpm.gwt.client.local.view.events.LoginEvent;
import com.ocpsoft.socialpm.gwt.client.local.view.events.LogoutEvent;

public interface AuthenticationAware
{
   void handleLogin(LoginEvent event);

   void handleLogout(LogoutEvent event);
}
