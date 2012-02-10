package com.ocpsoft.socialpm.gwt.client.local.template;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

public class Span extends ComplexPanel
{
   public Span()
   {
      setElement((Element) Document.get().createSpanElement().cast());
   }

   public Span(String s)
   {
      this();
      getElement().setInnerText(s);
   }

   public Span(Widget w)
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
