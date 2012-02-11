package com.ocpsoft.socialpm.gwt.client.local.view.component;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

public class Row extends ComplexPanel
{
   public Row()
   {
      setElement((Element) Document.get().createDivElement().cast());
      getElement().setClassName("row");
   }

   @Override
   public void add(Widget w)
   {
      super.add(w, getElement());
   }
}
