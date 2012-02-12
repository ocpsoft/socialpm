package com.ocpsoft.socialpm.gwt.client.local.view;

import org.jboss.errai.ioc.client.api.Caller;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.gwt.client.local.view.component.FluidRow;
import com.ocpsoft.socialpm.gwt.client.local.view.component.LoginModal;
import com.ocpsoft.socialpm.gwt.client.local.view.component.NavBar;
import com.ocpsoft.socialpm.gwt.client.local.view.component.NavLink;
import com.ocpsoft.socialpm.gwt.client.local.view.events.SignedInHandler;
import com.ocpsoft.socialpm.gwt.client.shared.rpc.AuthenticationService;
import com.ocpsoft.socialpm.model.user.Profile;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public abstract class FixedLayoutView extends Composite implements FluidLayout
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

   protected Presenter presenter;

   /*
    * Non-template fields
    */

   private final NavBar topnav = new NavBar();
   private final NavLink brandLink = new NavLink();
   private final NavLink signupLink = new NavLink("Join the party", HistoryConstants.SIGNUP());
   private final LoginModal loginModal;

   public FixedLayoutView(Caller<AuthenticationService> loginService)
   {
      initWidget(binder.createAndBindUi(this));
      loginModal = new LoginModal(loginService);
      loginModal.addSignedInHandler(new SignedInHandler() {

         @Override
         public void handleSignedIn(Profile profile)
         {
            topnav.remove(signupLink);
         }

      });

      topnav.setFixedTop(true);
      topnav.addBrand(brandLink);

      topnav.add(signupLink);
      topnav.addRight(loginModal);

      header.add(topnav);

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

   public LoginModal getLoginModal()
   {
      return loginModal;
   }

   @Override
   public void setPresenter(Presenter presenter)
   {
      this.presenter = presenter;
   }
}
