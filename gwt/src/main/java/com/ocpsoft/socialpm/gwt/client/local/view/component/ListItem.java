package com.ocpsoft.socialpm.gwt.client.local.view.component;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class ListItem extends ComplexPanel implements HasWidgets
{
   public ListItem()
   {
      setElement((Element) Document.get().createLIElement().cast());
   }

   public ListItem(String s)
   {
      this();
      getElement().setInnerText(s);
   }

   public ListItem(Widget w)
   {
      this();
      super.add(w, getElement());
   }

   @Override
   public void add(Widget w)
   {
      super.add(w, getElement());
   }
}
