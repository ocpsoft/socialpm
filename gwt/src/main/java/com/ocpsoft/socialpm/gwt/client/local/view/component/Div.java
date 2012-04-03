package com.ocpsoft.socialpm.gwt.client.local.view.component;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class Div extends HTMLPanel
{
   public Div()
   {
      super("div", "");
   }

   public Div(String s)
   {
      super("div", s);
   }

   public Div(Widget w)
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
}
