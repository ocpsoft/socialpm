package com.ocpsoft.socialpm.gwt.client.local.view.component;


public class BreadCrumb
{
   private boolean active;
   private String text;
   private NavLink nav;

   public BreadCrumb()
   {
   }
   
   public BreadCrumb(NavLink link)
   {
      this();
      setNavLink(link);
   }

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

   public NavLink getWidget()
   {
      return nav;
   }

   public BreadCrumb setNavLink(NavLink link)
   {
      this.nav = link;
      return this;
   }

   public boolean isText()
   {
      return text != null;
   }
}
