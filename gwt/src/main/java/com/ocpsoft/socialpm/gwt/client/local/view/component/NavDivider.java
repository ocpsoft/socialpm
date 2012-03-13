package com.ocpsoft.socialpm.gwt.client.local.view.component;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;

public class NavDivider extends ComplexPanel
{
   public NavDivider()
   {
      setElement((Element) Document.get().createLIElement().cast());
      setStyleName("divider-vertical");
   }
}
