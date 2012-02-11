package com.ocpsoft.socialpm.gwt.client.local.view.component;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * A splash screen
 */
public class SideNav extends Composite
{
   interface SideNavBinder extends UiBinder<Widget, SideNav>
   {
   }

   private static SideNavBinder binder = GWT.create(SideNavBinder.class);

   @UiField
   UnorderedList list;

   public SideNav()
   {
      initWidget(binder.createAndBindUi(this));
   }

   public SideNav addSectionHeader(String text)
   {
      ListItem li = new ListItem(text);
      li.setStyleName("nav-header");
      list.add(li);
      return this;
   }

   public SideNav add(Widget w)
   {
      return add(w, false);
   }

   public SideNav add(Widget w, boolean active)
   {
      Widget li = w;
      if (!(w instanceof ListItem))
      {
         li = new ListItem(w);
      }

      if (active)
         li.setStyleName("active");

      list.add(li);
      return this;
   }
}
