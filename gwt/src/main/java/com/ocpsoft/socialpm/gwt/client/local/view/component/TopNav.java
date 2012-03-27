package com.ocpsoft.socialpm.gwt.client.local.view.component;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;

import org.ocpsoft.rewrite.gwt.client.history.ContextPathListener;
import org.ocpsoft.rewrite.gwt.client.history.HistoryStateImpl;

import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.gwt.client.local.view.events.LoginEvent;
import com.ocpsoft.socialpm.gwt.client.local.view.events.LogoutEvent;
import com.ocpsoft.socialpm.gwt.client.local.view.presenter.AuthenticationAware;
import com.ocpsoft.socialpm.model.user.Profile;

/**
 * A splash screen
 */
@Dependent
public class TopNav extends NavBar implements AuthenticationAware
{
   private final NavLink signupLink = new NavLink("Join the party", HistoryConstants.SIGNUP());

   private final GravatarImage gravatar = new GravatarImage(30);

   private final NavDropdown status = new NavDropdown();
   private final NavLink signinLink = new NavLink("Sign in", HistoryConstants.LOGIN());
   private final NavLink signoutLink = new NavLink("Sign out", HistoryConstants.LOGOUT());

   private final ProfileLink profileLink = new ProfileLink();

   public TopNav()
   {
      super();

      setFixedTop(true);

      /*
       * Initialize brand and signup links
       */
      HistoryStateImpl.addContextPathListener(new ContextPathListener() {
         @Override
         public void onContextPathSet(String contextPath)
         {
            System.out.println("Deferred Brand Link set to [" + contextPath + "]");
            getBrandLink().setTargetHistoryToken(HistoryConstants.HOME());
         }
      });
      if (getBrandLink().getTargetHistoryToken() == null && HistoryStateImpl.getContextPath() != null)
      {
         getBrandLink().setTargetHistoryToken(HistoryConstants.HOME());
      }

      getBrandLink().setText("SocialPM");
      getBrandLink().setEnabled(true);

      add(signupLink);

      /*
       * Initialize Gravatar and Account Menu
       */
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

   @PostConstruct
   public void postConstruct()
   {}

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
