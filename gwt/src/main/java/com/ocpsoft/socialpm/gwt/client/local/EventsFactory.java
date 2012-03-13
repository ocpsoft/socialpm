package com.ocpsoft.socialpm.gwt.client.local;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.ocpsoft.socialpm.gwt.client.local.view.events.LoginEvent;
import com.ocpsoft.socialpm.gwt.client.local.view.events.LogoutEvent;
import com.ocpsoft.socialpm.gwt.client.shared.HelloMessage;
import com.ocpsoft.socialpm.model.user.Profile;

@ApplicationScoped
public class EventsFactory
{
   @Inject
   private Event<LoginEvent> loginEvent;

   @Inject
   private Event<HelloMessage> messageEvent;

   @Inject
   private Event<LogoutEvent> logoutEvent;

   public void fireMessage(String message)
   {
      messageEvent.fire(new HelloMessage(message));
   }

   public void fireLoginEvent(Profile profile)
   {
      loginEvent.fire(new LoginEvent(profile));
   }

   public void fireLogoutEvent()
   {
      logoutEvent.fire(new LogoutEvent());
   }
}
