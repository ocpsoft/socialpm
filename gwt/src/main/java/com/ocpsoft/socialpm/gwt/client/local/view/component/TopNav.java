package com.ocpsoft.socialpm.gwt.client.local.view.component;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.ocpsoft.rewrite.gwt.client.history.ContextPathListener;
import org.ocpsoft.rewrite.gwt.client.history.HistoryStateImpl;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.gwt.client.local.view.events.LoginEvent;
import com.ocpsoft.socialpm.gwt.client.local.view.events.LogoutEvent;
import com.ocpsoft.socialpm.gwt.client.local.view.presenter.AuthenticationAware;
import com.ocpsoft.socialpm.model.user.Profile;

@Dependent
@Templated
public class TopNav extends Composite implements AuthenticationAware
{
   private final NavLink signupLink = new NavLink("Join the party", HistoryConstants.SIGNUP());

   private final NavDropdown status = new NavDropdown();
   private final NavLink signinLink = new NavLink("Sign in", HistoryConstants.LOGIN());
   private final NavLink signoutLink = new NavLink("Sign out", HistoryConstants.LOGOUT());

   @Inject
   @DataField
   private NavLink brand;

   @Inject
   @DataField
   private ProfileLink profileLink;

   @Inject
   @DataField
   private GravatarImage gravatar;

   @Inject
   @DataField
   private UnorderedList right;

   @Inject
   @DataField
   private UnorderedList list;

   @PostConstruct
   public final void init()
   {
      setFixedTop(true);

      /*
       * Initialize brand and signup links
       */
      HistoryStateImpl.addContextPathListener(new ContextPathListener() {
         @Override
         public void onContextPathSet(String contextPath)
         {
            getBrandLink().setTargetHistoryToken(HistoryConstants.HOME());
         }
      });
      if (getBrandLink().getTargetHistoryToken() == null && HistoryStateImpl.getContextPath() != null)
      {
         getBrandLink().setTargetHistoryToken(HistoryConstants.HOME());
      }

      add(signupLink);

      /*
       * Initialize Gravatar and Account Menu
       */
      gravatar.setSize(30);
      gravatar.setVisible(true);
      gravatar.getImage().getElement()
               .setAttribute("style", "padding: 2px; background-color: #eee; position: relative; top: 3px;");
      addRight(new ListItem(gravatar));

      status.setVisible(false);
      status.add(profileLink);
      status.add(new NavDropdownDivider());
      status.add(signoutLink);
      addRight(status);

      signinLink.setVisible(true);
      addRight(new ListItem(signinLink));
   }

   public TopNav setFixedTop(boolean enabled)
   {
      if (enabled)
         this.addStyleName("navbar-fixed-top");
      else
         this.removeStyleName("navbar-fixed-stop");
      return this;
   }

   public TopNav add(Widget w)
   {
      return add(w, false);
   }

   public TopNav add(Widget w, boolean active)
   {
      Widget li = w;
      if (!"li".equals(w.getElement().getTagName()))
      {
         li = new ListItem(w);
      }

      if (active)
         li.setStyleName("active");

      list.add(li);
      return this;
   }

   public TopNav addRight(Widget w)
   {
      right.add(w);
      return this;
   }

   public boolean remove(Widget w)
   {
      return list.remove(w);
   }

   /*
    * Getters & Setters
    */
   public NavLink getBrandLink()
   {
      return brand;
   }

   public NavLink getSignupLink()
   {
      return signupLink;
   }

   @Override
   public void handleLogin(@Observes LoginEvent event)
   {
      Profile profile = event.getProfile();
      gravatar.setProfile(profile);
      gravatar.setVisible(true);

      profileLink.setProfile(profile);
      profileLink.setText("View your profile");

      signinLink.setVisible(false);
      signupLink.setVisible(false);

      status.setToggleText(profile.getUsername());
      status.setVisible(true);
   }

   @Override
   public void handleLogout(@Observes LogoutEvent event)
   {
      gravatar.clear();
      gravatar.setVisible(false);
      status.setVisible(false);
      signinLink.setVisible(true);
      signupLink.setVisible(true);

      profileLink.clear();
   }

}
