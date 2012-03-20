package com.ocpsoft.socialpm.gwt.client.local.view.component;

import java.util.Stack;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A splash screen
 */
public class BreadCrumbList extends Span
{
   private static final String ACTIVE = "active";

   private final Stack<Widget> contents = new Stack<Widget>();

   public BreadCrumbList()
   {
   }

   public BreadCrumbList push(BreadCrumb crumb)
   {
      if (!contents.isEmpty())
      {
         HTMLPanel span = new HTMLPanel("span", " / ");
         span.setStyleName("divider");
         contents.add(span);
         add(span);
      }

      contents.add(crumb.getWidget());
      add(crumb.getWidget());
      return this;
   }

   public BreadCrumb pop()
   {
      BreadCrumb result = null;

      if (!contents.isEmpty())
      {
         Widget item = contents.pop();
         remove(item);

         result = new BreadCrumb();
         result.setActive(item.getStyleName() != null && item.getStyleName().equals(ACTIVE));
         result.setNavLink((NavLink) item);

         if (!contents.isEmpty())
         {
            contents.pop();
         }
      }

      return result;
   }

}
