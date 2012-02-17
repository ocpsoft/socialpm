package com.ocpsoft.socialpm.gwt.client.local;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.ocpsoft.socialpm.gwt.client.local.view.events.LoginEvent;
import com.ocpsoft.socialpm.gwt.client.shared.HelloMessage;
import com.ocpsoft.socialpm.model.user.Profile;

@ApplicationScoped
public class EventsFactory
{
   @Inject
   private Event<LoginEvent> loggedInEvent;

   @Inject
   private Event<HelloMessage> messageEvent;

   public void fireMessage(String message)
   {
      messageEvent.fire(new HelloMessage(message));
   }

   public void fireLoginEvent(Profile profile)
   {
      loggedInEvent.fire(new LoginEvent(profile));
   }
}
