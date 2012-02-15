package com.ocpsoft.socialpm.gwt.client.local.view.component;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;

public class NavLink extends Anchor
{
   ClickHandler handler = new ClickHandler() {

      @Override
      public void onClick(ClickEvent event)
      {
         event.preventDefault();
         pushHistory();
      }
   };

   ClickHandler disabledHandler = new ClickHandler() {

      @Override
      public void onClick(ClickEvent event)
      {
         event.preventDefault();
      }
   };

   private HandlerRegistration registeredHandler;
   private String target;

   public NavLink()
   {
      super();
      setEnabled(true);
   }

   public NavLink(String text)
   {
      super(text);
      setEnabled(true);
   }

   public NavLink(String text, String target)
   {
      super(text);
      this.target = target;
      setEnabled(true);
   }

   @Override
   public void setEnabled(boolean enabled)
   {
      if (registeredHandler != null)
      {
         registeredHandler.removeHandler();
         registeredHandler = null;
      }

      super.setEnabled(enabled);
      if (enabled)
      {
         registeredHandler = this.addClickHandler(handler);
         this.removeStyleName("disabled");
      }
      else
      {
         registeredHandler = this.addClickHandler(disabledHandler);
         this.addStyleName("disabled");
      }
   }

   public NavLink setTargetHistoryToken(String token)
   {
      this.target = token;
      this.setHref(token);
      return this;
   }

   public String getTargetHistoryToken()
   {
      return target;
   }

   /*
    * Private methods
    */
   private void pushHistory()
   {
      History.newItem(getTargetHistoryToken());
   }
}
