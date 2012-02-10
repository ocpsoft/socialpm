package com.ocpsoft.socialpm.gwt.client.local.template;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;

public class NavLink extends Anchor
{
   public NavLink(String text, final String href)
   {
      super(text);
      this.addClickHandler(new ClickHandler() {
         @Override
         public void onClick(ClickEvent event)
         {
            event.preventDefault();
            History.newItem(href);
         }
      });
   }
}
