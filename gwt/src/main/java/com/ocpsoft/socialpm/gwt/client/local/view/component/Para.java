package com.ocpsoft.socialpm.gwt.client.local.view.component;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

public class Para extends ComplexPanel
{
   public Para()
   {
      setElement((Element) Document.get().createPElement().cast());
   }

   public Para(String s)
   {
      this();
      getElement().setInnerText(s);
   }

   public Para(Widget w)
   {
      this();
      super.add(w, getElement());
   }

   @Override
   public void add(Widget w)
   {
      super.add(w, getElement());
   }

   public void setInnerText(String text)
   {
      getElement().setInnerText(text);
   }
}
