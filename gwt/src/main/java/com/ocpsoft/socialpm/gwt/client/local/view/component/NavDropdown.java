package com.ocpsoft.socialpm.gwt.client.local.view.component;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * A splash screen
 */
public class NavDropdown extends Composite
{
   interface NavDropdownBinder extends UiBinder<ListItem, NavDropdown>
   {
   }

   private static NavDropdownBinder binder = GWT.create(NavDropdownBinder.class);

   @UiField
   UnorderedList menu;

   @UiField
   Anchor toggleLink;

   public NavDropdown()
   {
      initWidget(binder.createAndBindUi(this));
      toggleLink.getElement().setAttribute("data-toggle", "dropdown");
      toggleLink.setHref("#");
   }

   public NavDropdown setToggleText(String text)
   {
      toggleLink.setHTML("");
      Span toggle = new Span(text);
      toggle.add(new InlineHTML("<b class=\"caret\"></b>"));
      toggleLink.getElement().appendChild(toggle.getElement());
      return this;
   }

   public void add(Widget w)
   {
      menu.add(w);
   }

   public void add(Widget w, boolean active)
   {
      Widget li = w;
      if (!"li".equals(w.getElement().getTagName()))
      {
         li = new ListItem(w);
      }

      if (active)
         li.setStyleName("active");

      menu.add(li);
   }

}
