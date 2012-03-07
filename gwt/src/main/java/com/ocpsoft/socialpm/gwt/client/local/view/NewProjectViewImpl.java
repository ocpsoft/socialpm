package com.ocpsoft.socialpm.gwt.client.local.view;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.ocpsoft.socialpm.gwt.client.local.EventsFactory;
import com.ocpsoft.socialpm.gwt.client.local.ServiceFactory;

@ApplicationScoped
public class NewProjectViewImpl extends FixedLayoutView implements NewProjectView
{
   @Inject
   private ServiceFactory serviceFactory;

   @Inject
   private EventsFactory eventFactory;

   public NewProjectViewImpl()
   {
      super();
   }

   @Override
   public void setup()
   {
      HorizontalPanel panel = new HorizontalPanel();
      super.content.add(panel);
   }

}
