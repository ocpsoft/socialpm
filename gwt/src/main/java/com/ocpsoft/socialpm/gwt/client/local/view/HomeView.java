package com.ocpsoft.socialpm.gwt.client.local.view;

import java.util.List;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TextBox;
import com.ocpsoft.socialpm.gwt.client.local.view.component.HeroPanel;
import com.ocpsoft.socialpm.gwt.client.local.view.component.NavLink;
import com.ocpsoft.socialpm.gwt.client.local.view.component.SigninStatus;
import com.ocpsoft.socialpm.gwt.client.local.view.presenter.AuthenticationAware;
import com.ocpsoft.socialpm.model.project.Project;

public interface HomeView extends IsWidget
{
   public interface Presenter extends AuthenticationAware
   {
      void goTo(Place place);
   }

   void setPresenter(Presenter presenter);

   TextBox getMessageBox();

   NavLink getBrandLink();

   ComplexPanel getContent();

   HeroPanel getGreeting();

   Anchor getSendMessageButton();

   SigninStatus getSigninStatus();

   void showDashboard(List<Project> projects);

}
