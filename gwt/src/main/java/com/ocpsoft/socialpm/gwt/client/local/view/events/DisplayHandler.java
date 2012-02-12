package com.ocpsoft.socialpm.gwt.client.local.view.events;

import com.google.gwt.event.shared.EventHandler;

public interface DisplayHandler extends EventHandler
{
   /**
    * Called when a native display event is fired.
    * 
    * @param event the {@link DisplayEvent} that was fired
    */
   public void handleOnDisplay(DisplayEvent source);
}
