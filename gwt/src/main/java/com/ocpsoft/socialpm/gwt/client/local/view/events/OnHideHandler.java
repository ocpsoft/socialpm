package com.ocpsoft.socialpm.gwt.client.local.view.events;

import com.google.gwt.event.shared.EventHandler;

public interface OnHideHandler extends EventHandler
{
   /**
    * Called when a native hide event is fired.
    * 
    * @param event the {@link HideEvent} that was fired
    */
   public void handleOnHide(HideEvent source);
}
