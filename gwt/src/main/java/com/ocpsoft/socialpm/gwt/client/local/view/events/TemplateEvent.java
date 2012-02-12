package com.ocpsoft.socialpm.gwt.client.local.view.events;

import com.google.gwt.user.client.ui.Widget;

public abstract class TemplateEvent
{
   private Widget source;

   public TemplateEvent(Widget source)
   {
      this.source = source;
   }
   
   /**
    * Get the source {@link Widget} from which this event was fired.
    * @return
    */
   public Widget getSource(){
      return source;
   }
}
