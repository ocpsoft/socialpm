package com.ocpsoft.socialpm.gwt.client.local.view.component;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

public class Fieldset extends ComplexPanel
{
   public Fieldset()
   {
      setElement((Element) Document.get().createFieldSetElement().cast());
   }

   public Fieldset(String s)
   {
      this();
      getElement().setInnerText(s);
   }

   public Fieldset(Widget w)
   {
      this();
      super.add(w, getElement());
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

   public void setInnerText(String text)
   {
      getElement().setInnerText(text);

   }
}
