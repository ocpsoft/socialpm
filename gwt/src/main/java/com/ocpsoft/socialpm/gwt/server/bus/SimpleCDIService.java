/*
 * Copyright 2009 JBoss, a division of Red Hat Hat, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ocpsoft.socialpm.gwt.server.bus;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.ocpsoft.socialpm.gwt.client.shared.HelloMessage;
import com.ocpsoft.socialpm.gwt.client.shared.Response;

/**
 * A very simple CDI based service.
 */
@ApplicationScoped
public class SimpleCDIService
{
   @Inject
   private Event<Response> responseEvent;

   public void handleMessage(@Observes HelloMessage event)
   {
      System.out.println("Received HelloMessage from Client: " + event.getMessage());
      responseEvent.fire(new Response(event.getMessage() + " @ timemillis: " + System.currentTimeMillis()));
   }
}
