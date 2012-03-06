package com.ocpsoft.socialpm.gwt.client.local.view;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ocpsoft.socialpm.gwt.client.local.App;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.gwt.client.local.view.component.FluidRow;
import com.ocpsoft.socialpm.gwt.client.local.view.component.NavBar;
import com.ocpsoft.socialpm.gwt.client.local.view.component.NavLink;
import com.ocpsoft.socialpm.gwt.client.local.view.component.SigninStatus;
import com.ocpsoft.socialpm.gwt.client.local.view.events.LoginEvent;

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

   private Presenter presenter;

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

   /*
    * Non-template fields
    */

   private final NavBar topnav = new NavBar();
   private final NavLink brandLink = new NavLink(App.NAME, HistoryConstants.HOME());
   private final NavLink signupLink = new NavLink("Join the party", HistoryConstants.SIGNUP());

   @Inject
   private SigninStatus signinStatus;

   protected abstract void setup();

   public FixedLayoutView()
   {
      initWidget(binder.createAndBindUi(this));
   }
   
   @PostConstruct
   public void postConstruct()
   {
      System.out.println("PC - FLV" + this);
      topnav.setFixedTop(true);
      topnav.addBrand(brandLink);

      signupLink.setTargetHistoryToken(HistoryConstants.LOGIN());
      topnav.add(signupLink);

      topnav.addRight(signinStatus);
      header.add(topnav);
      setup();
   }

   @Override
   public void setPresenter(FixedLayout.Presenter presenter)
   {
      this.presenter = presenter;
   }

   public void onLogin(@Observes LoginEvent event)
   {
      if (presenter != null)
         presenter.handleLogin(event);
   }

   /*
    * Getters & Setters
    */
   public NavLink getBrandLink()
   {
      return brandLink;
   }

   public NavLink getSignupLink()
   {
      return signupLink;
   }

   public SigninStatus getSigninStatus()
   {
      return signinStatus;
   }
}
