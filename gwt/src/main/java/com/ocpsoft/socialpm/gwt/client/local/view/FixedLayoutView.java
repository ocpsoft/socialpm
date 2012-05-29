package com.ocpsoft.socialpm.gwt.client.local.view;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ocpsoft.socialpm.gwt.client.local.view.component.Alert;
import com.ocpsoft.socialpm.gwt.client.local.view.component.FluidRow;
import com.ocpsoft.socialpm.gwt.client.local.view.component.TopNav;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public abstract class FixedLayoutView extends Composite implements FixedLayout
{
   @UiTemplate("FixedLayoutView.ui.xml")
   interface FixedLayoutViewBinder extends UiBinder<Widget, FixedLayoutView>
   {
   }

   private static FixedLayoutViewBinder binder = GWT.create(FixedLayoutViewBinder.class);

   /*
    * UiBinder template fields
    */

   @UiField
   protected HTMLPanel header = new HTMLPanel("");

   @UiField
   protected FluidRow subheader = new FluidRow();

   @UiField
   protected HTMLPanel content = new HTMLPanel("");

   @UiField
   protected HTMLPanel footer = new HTMLPanel("");

   private List<Alert> alerts = new ArrayList<Alert>();

   /*
    * Non-template fields
    */

   @Inject
   private TopNav topnav;

   protected abstract void setup();

   public FixedLayoutView()
   {
      initWidget(binder.createAndBindUi(this));
   }

   @PostConstruct
   public void postConstruct()
   {
      header.add(topnav);
      setup();
   }

   @Override
   public TopNav getTopNav()
   {
      return topnav;
   }

   @Override
   public void alert(Alert alert)
   {
      subheader.add(alert);
      alerts.add(alert);
   }

   @Override
   public void clearAlerts()
   {
      subheader.clear();
      alerts.clear();
   }
}
