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

import org.jboss.errai.bus.client.api.ErrorCallback;
import org.jboss.errai.bus.client.api.Message;
import org.jboss.errai.bus.client.api.RemoteCallback;
import org.jboss.errai.ioc.client.api.Caller;
import org.jboss.errai.ioc.client.api.EntryPoint;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.ocpsoft.socialpm.gwt.client.local.template.BreadCrumb;
import com.ocpsoft.socialpm.gwt.client.local.template.BreadCrumbStack;
import com.ocpsoft.socialpm.gwt.client.local.template.HeroPanel;
import com.ocpsoft.socialpm.gwt.client.local.template.SideNav;
import com.ocpsoft.socialpm.gwt.client.shared.HelloMessage;
import com.ocpsoft.socialpm.gwt.client.shared.Response;
import com.ocpsoft.socialpm.gwt.client.shared.rpc.LoginService;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.user.Profile;

/**
 * Main application entry point.
 */
@EntryPoint
public class App
{

   @Inject
   private Event<HelloMessage> messageEvent;

   @Inject
   private Caller<LoginService> loginService;

   private final Label responseLabel = new Label();
   private final TextBox messageBox = new TextBox();
   private final Anchor anchor = new Anchor("Learn more »");
   private final SideNav sideNav = new SideNav();
   private final BreadCrumbStack topBreadCrumbs = new BreadCrumbStack();

   private final HeroPanel hero = new HeroPanel().setHeading("Lincoln Hero!")
            .setContent("This is a hero panel added by JAVA!")
            .addAction(anchor);

   ClickHandler sendMessage = new ClickHandler() {
      @Override
      public void onClick(ClickEvent event)
      {
         System.out.println("Handling click event!");
         event.preventDefault();
         fireMessage();
      }
   };

   RemoteCallback<Profile> success = new RemoteCallback<Profile>() {

      @Override
      public void callback(Profile response)
      {
         System.out.println("Success!");
      }

   };
   ErrorCallback failure = new ErrorCallback() {

      @Override
      public boolean error(Message message, Throwable throwable)
      {
         System.out.println("Failure!");
         return false;
      }
   };

   @PostConstruct
   public void buildUI()
   {
      buildHero();
      buildSideNav();
      buildBreadCrumbs();

      HorizontalPanel login = new HorizontalPanel();
      final TextBox username = new TextBox();
      final TextBox password = new TextBox();
      Button submit = new Button();
      submit.setText("Login");

      submit.addClickHandler(new ClickHandler() {
         @Override
         public void onClick(ClickEvent event)
         {

            System.out.println("Clicked!");
            System.out.println(username.getText() + "/" + password.getText());

            Profile profile = loginService.call(success, failure).login(username.getText(), password.getText());
            System.out.println("After RPC!");

            System.out.println("Profile = " + profile);
         }
      });

      login.add(username);
      login.add(password);
      login.add(submit);

      RootPanel.get("account").add(login);

      System.out.println("UI Constructed!");
   }

   private void buildHero()
   {
      anchor.addStyleName("btn btn-primary btn-large");
      anchor.setHref("/learn");
      anchor.addClickHandler(sendMessage);
      RootPanel.get("content").add(hero);
   }

   private void buildBreadCrumbs()
   {
      topBreadCrumbs.push(new BreadCrumb().setWidget(new Anchor("Home")));
      topBreadCrumbs.push(new BreadCrumb().setWidget(new Anchor("Lincoln")));
      topBreadCrumbs.push(new BreadCrumb().setWidget(new Anchor("SocialPM")));
      BreadCrumb temp = topBreadCrumbs.pop();
      topBreadCrumbs.push(temp);
      RootPanel.get("breadcrumbs").add(topBreadCrumbs);
   }

   private void buildSideNav()
   {
      sideNav.addSectionHeader("Projects");
      sideNav.addLink(new Anchor("SocialPM"));
      sideNav.addLink(new Anchor("Rewrite"), true);
      sideNav.addLink(new Anchor("PrettyFaces"));
      sideNav.addLink(new Anchor("PrettyTime"));
      RootPanel.get("sidenav").add(sideNav);
   }

   void fireMessage()
   {
      String text = messageBox.getText();
      Project project = new Project();
      project.setName("foo");
      HelloMessage event1 = new HelloMessage(text + project);
      messageEvent.fire(event1);
   }

   public void response(@Observes Response event)
   {
      System.out.println("Got a Response!");
      responseLabel.setText("HelloMessage from Server: " + event.getMessage().toUpperCase());
      hero.setContent(event.getMessage());
   }

   /**
    * Returns the "Send" button. Exposed for testing.
    */
   Anchor getSendButton()
   {
      return anchor;
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
      return messageBox;
   }
}
