package com.ocpsoft.socialpm.gwt.client.local.view.component;

import org.ocpsoft.rewrite.gwt.client.history.HistoryStateImpl;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;

public class NavLink extends Anchor
{
   ClickHandler enabledHandler = new ClickHandler() {

      @Override
      public void onClick(ClickEvent event)
      {
         event.preventDefault();
         String target = getTargetHistoryToken();
         String contextPath = HistoryStateImpl.getContextPath();
         System.out.println("Clicked nav link [" + target + "]");
         if (target.startsWith(contextPath) && !target.equals(contextPath))
         {
            target = target.substring(contextPath.length() - 1);
            if (target.startsWith("/"))
            {
               target = target.substring(1);
            }
         }
         History.newItem(target);
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
      super();
      setText(text);
      setEnabled(true);
   }

   public NavLink(String text, String target)
   {
      super();
      setText(text);
      setTargetHistoryToken(target);
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
         registeredHandler = this.addClickHandler(enabledHandler);
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
      if (token != null)
      {
         String contextPath = HistoryStateImpl.getContextPath();
         if (contextPath != null && !token.startsWith(contextPath))
         {
            token = contextPath + token;
         }
      }
      this.setHref(token);
      this.target = token;
      return this;
   }

   public String getTargetHistoryToken()
   {
      return target;
   }

}
