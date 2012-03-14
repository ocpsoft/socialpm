package com.ocpsoft.socialpm.gwt.client.local.view;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.ocpsoft.socialpm.gwt.client.local.view.component.HeroPanel;
import com.ocpsoft.socialpm.gwt.client.local.view.component.ProjectList;
import com.ocpsoft.socialpm.gwt.client.local.view.component.WelcomeBar;
import com.ocpsoft.socialpm.model.user.Profile;

public interface HomeView extends FixedLayout
{
   public interface Presenter extends FixedLayout.FixedPresenter
   {
   }

   TextBox getMessageBox();

   ComplexPanel getContent();

   HeroPanel getGreeting();

   Anchor getSendMessageButton();

   WelcomeBar getWelcomeBar();

   void showDashboard(Profile profile);

   void showSplash();

   ProjectList getProjectList();

   Presenter getPresenter();

   void setPresenter(Presenter presenter);

}
