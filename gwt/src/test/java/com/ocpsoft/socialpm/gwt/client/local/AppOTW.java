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

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.gwt.client.local.view.component.BreadCrumb;
import com.ocpsoft.socialpm.gwt.client.local.view.component.BreadCrumbStack;
import com.ocpsoft.socialpm.gwt.client.local.view.component.HeroPanel;
import com.ocpsoft.socialpm.gwt.client.local.view.component.NavBar;
import com.ocpsoft.socialpm.gwt.client.local.view.component.SideNav;
import com.ocpsoft.socialpm.gwt.client.shared.HelloMessage;
import com.ocpsoft.socialpm.gwt.client.shared.Response;
import com.ocpsoft.socialpm.model.project.Project;

/**
 * Main application entry point.
 */
//@EntryPoint
public class AppOTW
{

   @Inject
   private Event<HelloMessage> messageEvent;

   private final Label responseLabel = new Label();
   private final TextBox messageBox = new TextBox();
   private final Anchor anchor = new Anchor("Learn more »");
   private final SideNav sideNav = new SideNav();
   private final BreadCrumbStack topBreadCrumbs = new BreadCrumbStack();

   private final HeroPanel hero = new HeroPanel().setHeading("Social Agile Hero!")
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

//   @PostConstruct
   public void buildUI()
   {
      initHistory();
      buildHero();
      buildSideNav();
      buildTopNav();
      buildBreadCrumbs();
      // buildAuthentication();

      System.out.println("UI Constructed!");
   }

   private void buildTopNav()
   {
      NavBar topNav = new NavBar().setFixedTop(true);

      topNav.addBrand(new Anchor("SocialPM", HistoryConstants.HOME));

      RootPanel.get("topnav").add(topNav);
   }

   private void initHistory()
   {
      History.addValueChangeHandler(new ValueChangeHandler<String>() {
         @Override
         public void onValueChange(ValueChangeEvent<String> event)
         {
            String historyToken = event.getValue();
         }
      });
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
      sideNav.add(new Anchor("SocialPM"));
      sideNav.add(new Anchor("Rewrite"), true);
      sideNav.add(new Anchor("PrettyFaces"));
      sideNav.add(new Anchor("PrettyTime"));
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
