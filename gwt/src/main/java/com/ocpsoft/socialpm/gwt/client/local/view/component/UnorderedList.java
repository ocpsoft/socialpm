package com.ocpsoft.socialpm.gwt.client.local.view.component;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

public class UnorderedList extends ComplexPanel
{
   public UnorderedList()
   {
      setElement(Document.get().createULElement());
   }

   public void setId(String id)
   {
      getElement().setId(id);
   }

   @Override
   public void add(Widget w)
   {
      if (!((w instanceof ListItem) || w instanceof NavDropdown))
         super.add(new ListItem(w), getElement());
      else
         super.add(w, getElement());
   }
}
