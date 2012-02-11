package com.ocpsoft.socialpm.gwt.client.local.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ocpsoft.socialpm.gwt.client.local.view.component.FluidRow;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public abstract class FluidLayoutView extends Composite implements FluidLayout
{
   @UiTemplate("FluidLayoutView.ui.xml")
   interface FluidLayoutViewBinder extends UiBinder<Widget, FluidLayoutView>
   {}

   private static FluidLayoutViewBinder binder = GWT.create(FluidLayoutViewBinder.class);

   @UiField
   protected HTMLPanel header = new HTMLPanel("");
   
   @UiField
   protected FluidRow subheader = new FluidRow();
   
   @UiField
   protected HTMLPanel side = new HTMLPanel("");
   
   @UiField
   protected HTMLPanel content = new HTMLPanel("");
   
   @UiField
   protected HTMLPanel footer = new HTMLPanel("");
   
   protected Presenter presenter;
   
   public FluidLayoutView()
   {
      initWidget(binder.createAndBindUi(this));
   }

   @Override
   public void setPresenter(Presenter presenter)
   {
      this.presenter = presenter;
   }
}
