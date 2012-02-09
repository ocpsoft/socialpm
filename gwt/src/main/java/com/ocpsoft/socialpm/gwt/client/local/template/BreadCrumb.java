package com.ocpsoft.socialpm.gwt.client.local.template;

import com.google.gwt.user.client.ui.Widget;

public class BreadCrumb
{
   private boolean active;
   private String text;
   private Widget widget;

   public boolean isActive()
   {
      return active;
   }

   public BreadCrumb setActive(boolean active)
   {
      this.active = active;
      return this;
   }

   public String getText()
   {
      return text;
   }

   public BreadCrumb setText(String text)
   {
      this.text = text;
      return this;
   }

   public Widget getWidget()
   {
      return widget;
   }

   public BreadCrumb setWidget(Widget widget)
   {
      this.widget = widget;
      return this;
   }

   public boolean isText()
   {
      return text != null;
   }
}
