package com.ocpsoft.socialpm.gwt.client.local.view.component;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A splash screen
 */
public class ProgressBar extends Composite
{
   interface ProgressBarBinder extends UiBinder<Widget, ProgressBar>
   {
   }

   private static ProgressBarBinder binder = GWT.create(ProgressBarBinder.class);

   @UiField
   HTMLPanel container;

   @UiField
   Div bar;

   public ProgressBar()
   {
      initWidget(binder.createAndBindUi(this));
   }

   public void setLabel(Widget widget)
   {
      bar.clear();
      bar.add(widget);
   }

   public void setLabel(String text)
   {
      bar.setInnerText(text);
   }

   public void setPercentComplete(int percent)
   {
      if (percent < 0)
         percent = 0;
      if (percent > 100)
         percent = 100;

      bar.getElement().setAttribute("style", "width: " + percent + "%;");
   }
}
