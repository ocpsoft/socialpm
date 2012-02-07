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
package com.ocpsoft.socialpm.gwt.client.local;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.errai.ioc.client.api.EntryPoint;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.ocpsoft.socialpm.gwt.client.shared.HelloMessage;
import com.ocpsoft.socialpm.gwt.client.shared.Response;

/**
 * Main application entry point.
 */
@EntryPoint
public class App
{

   @Inject
   private Event<HelloMessage> messageEvent;

   private final Label responseLabel = new Label();
   private final Button button = new Button("Send");
   private final TextBox message = new TextBox();

   @PostConstruct
   public void buildUI()
   {

      button.addClickHandler(new ClickHandler() {
         @Override
         public void onClick(ClickEvent event)
         {
            System.out.println("Handling click event!");
            fireMessage();
         }
      });

      HorizontalPanel horizontalPanel = new HorizontalPanel();
      horizontalPanel.add(message);
      horizontalPanel.add(button);
      horizontalPanel.add(responseLabel);

      RootPanel.get().add(horizontalPanel);

      System.out.println("UI Constructed!");
   }

   /**
    * Fires a CDI HelloMessage with the current contents of the message textbox.
    */
   void fireMessage()
   {
      String text = message.getText();
      HelloMessage event = new HelloMessage(text);
      messageEvent.fire(event);
   }

   public void response(@Observes Response event)
   {
      System.out.println("Got a Response! ");
      responseLabel.setText("HelloMessage from Server: " + event.getMessage().toUpperCase());
   }

   /**
    * Returns the "Send" button. Exposed for testing.
    */
   Button getSendButton()
   {
      return button;
   }

   /**
    * Returns the response label. Exposed for testing.
    */
   Label getResponseLabel()
   {
      return responseLabel;
   }

   /**
    * Returns the "message" text box. Exposed for testing.
    */
   TextBox getMessageBox()
   {
      return message;
   }
}