package com.ocpsoft.socialpm.gwt.client.local.view.presenter;

import com.ocpsoft.socialpm.gwt.client.local.view.events.LoginEvent;

public interface AuthenticationAware
{
   void handleLogin(LoginEvent event);
}
