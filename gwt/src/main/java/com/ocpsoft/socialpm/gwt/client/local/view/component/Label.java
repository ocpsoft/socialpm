package com.ocpsoft.socialpm.gwt.client.local.view.component;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class Label extends HTMLPanel
{
   public Label()
   {
      super("label", "");
   }

   public Label(String s)
   {
      super("label", s);
   }

   public Label(Widget w)
   {
      this();
      super.add(w);
   }

   @Override
   public void add(Widget w)
   {
      super.add(w, getElement());
   }

   public void setInnerHTML(String html)
   {
      getElement().setInnerHTML(html);
   }

   public void setInnerText(String text)
   {
      getElement().setInnerText(text);
   }

   public void setFor(String forId)
   {
      getElement().setAttribute("for", forId);
   }
}
