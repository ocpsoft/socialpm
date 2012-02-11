package com.ocpsoft.socialpm.gwt.client.local.view.component;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A splash screen
 */
public class HeroPanel extends Composite
{
   interface HeroBinder extends UiBinder<Widget, HeroPanel>
   {}

   private static HeroBinder binder = GWT.create(HeroBinder.class);

   @UiField
   HeadingElement heading;

   @UiField
   ParagraphElement content;

   @UiField
   HTMLPanel under;
   
   @UiField
   HTMLPanel action;

   public HeroPanel()
   {
      initWidget(binder.createAndBindUi(this));
   }
   
   public HTMLPanel getUnder()
   {
      return under;
   }

   public HeroPanel setContent(String text)
   {
      content.setInnerText(text);
      return this;
   }

   public HeroPanel setHeading(String text)
   {
      heading.setInnerText(text);
      return this;
   }

   public HeroPanel addAction(Widget widget)
   {
      action.add(widget);
      return this;
   }
}
