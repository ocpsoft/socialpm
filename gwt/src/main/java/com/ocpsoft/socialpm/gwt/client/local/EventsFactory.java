package com.ocpsoft.socialpm.gwt.client.local;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.ocpsoft.socialpm.gwt.client.local.view.events.LoginEvent;
import com.ocpsoft.socialpm.gwt.client.shared.HelloMessage;

@ApplicationScoped
public class EventsFactory
{
   @Inject
   private Event<LoginEvent> loggedInEvent;

   @Inject
   private Event<HelloMessage> messageEvent;

   public Event<LoginEvent> getLoginEvent()
   {
      return loggedInEvent;
   }

   public Event<HelloMessage> getMessageEvent()
   {
      return messageEvent;
   }
}
