package com.ocpsoft.socialpm.gwt.client.local.view.component;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.ocpsoft.socialpm.model.feed.FeedEvent;

public class StatusFeed extends Composite
{
   interface ProjectListBinder extends UiBinder<Widget, StatusFeed>
   {
   }

   private static ProjectListBinder binder = GWT.create(ProjectListBinder.class);

   public StatusFeed()
   {
      initWidget(binder.createAndBindUi(this));
   }

   public void setStatuses(List<FeedEvent> statuses)
   {
      // TOOD implement
   }

   public void addStatuses(List<FeedEvent> statuses)
   {

   }
}
