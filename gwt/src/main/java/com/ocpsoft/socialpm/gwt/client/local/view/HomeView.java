package com.ocpsoft.socialpm.gwt.client.local.view;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.ocpsoft.socialpm.gwt.client.local.view.component.HeroPanel;
import com.ocpsoft.socialpm.gwt.client.local.view.component.NavLink;
import com.ocpsoft.socialpm.gwt.client.local.view.component.ProjectList;
import com.ocpsoft.socialpm.gwt.client.local.view.component.SigninStatus;
import com.ocpsoft.socialpm.gwt.client.local.view.component.WelcomeBar;
import com.ocpsoft.socialpm.model.user.Profile;

public interface HomeView extends FixedLayout
{
   public interface Presenter extends FixedLayout.Presenter
   {
   }

   TextBox getMessageBox();

   NavLink getBrandLink();

   ComplexPanel getContent();

   HeroPanel getGreeting();

   Anchor getSendMessageButton();

   SigninStatus getSigninStatus();

   WelcomeBar getWelcomeBar();

   void showDashboard(Profile profile);

   void showSplash();

   ProjectList getProjectList();

}
