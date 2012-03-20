package com.ocpsoft.socialpm.gwt.client.local.view.component;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

public class Heading extends ComplexPanel
{
   public Heading(int number)
   {
      setElement((Element) Document.get().createHElement(number).cast());
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
}
