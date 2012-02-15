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
import javax.inject.Inject;

import org.jboss.errai.enterprise.client.cdi.api.CDI;
import org.jboss.errai.ioc.client.api.EntryPoint;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryStateImpl;
import com.ocpsoft.socialpm.gwt.client.local.places.HomePlace;

/**
 * Main application entry point.
 */
@EntryPoint
public class App
{
   public static final String NAME = "SocialPM";

   private final Place defaultPlace = new HomePlace(HistoryStateImpl.getContextPath());
   private final SimplePanel app = new SimplePanel();

   @Inject
   private ClientFactory clientFactory;

   @Inject
   private AppPlaceHistoryMapper historyMapper;

   @Inject
   private AppActivityMapper activityMapper;

   @PostConstruct
   public void setup()
   {
      EventBus eventBus = clientFactory.getEventBus();
      PlaceController placeController = clientFactory.getPlaceController();

      // Start ActivityManager for the main widget with our ActivityMapper
      ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
      activityManager.setDisplay(app);

      // Start PlaceHistoryHandler with our PlaceHistoryMapper
      final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
      historyHandler.register(placeController, eventBus, defaultPlace);

      RootPanel.get("rootPanel").add(app);

      CDI.addPostInitTask(new Runnable() {
         @Override
         public void run()
         {
            System.out.println("Running history handler");
            historyHandler.handleCurrentHistory();
         }
      });
   }

}
