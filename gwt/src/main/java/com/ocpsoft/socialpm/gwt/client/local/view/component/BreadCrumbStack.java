package com.ocpsoft.socialpm.gwt.client.local.view.component;

import java.util.Stack;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A splash screen
 */
public class BreadCrumbStack extends Composite
{
   private static final String ACTIVE = "active";

   interface SideNavBinder extends UiBinder<Widget, BreadCrumbStack>
   {
   }

   private static SideNavBinder binder = GWT.create(SideNavBinder.class);

   @UiField
   UnorderedList list;

   private final Stack<ListItem> contents = new Stack<ListItem>();

   public BreadCrumbStack()
   {
      initWidget(binder.createAndBindUi(this));
   }

   public BreadCrumbStack push(BreadCrumb crumb)
   {
      if (!contents.isEmpty())
      {
         ListItem peek = contents.peek();

         HTMLPanel span = new HTMLPanel("span", "/");
         span.setStyleName("divider");
         peek.add(span);
      }

      ListItem li = buildListItem(crumb);

      contents.add(li);
      list.add(li);
      return this;
   }

   public BreadCrumb pop()
   {
      BreadCrumb result = null;

      if (!contents.isEmpty())
      {
         ListItem item = contents.pop();
         list.remove(item);

         result = new BreadCrumb();
         result.setActive(item.getStyleName() != null && item.getStyleName().equals(ACTIVE));
         result.setNavLink((NavLink) item.getWidget(0));

         if (!contents.isEmpty())
         {
            item = contents.peek();
            item.remove(1);
         }
      }

      return result;
   }

   private ListItem buildListItem(BreadCrumb crumb)
   {
      ListItem li = new ListItem();
      if (crumb.isText())
      {
         li = new ListItem(crumb.getText());
      }
      else
      {
         li.add(crumb.getWidget());
      }

      if (crumb.isActive())
      {
         li.setStyleName(ACTIVE);
      }

      return li;
   }

}
