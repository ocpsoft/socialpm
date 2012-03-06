package com.ocpsoft.socialpm.gwt.client.local.view;

import java.util.List;

import com.ocpsoft.socialpm.gwt.client.local.view.component.HeroPanel;
import com.ocpsoft.socialpm.gwt.client.local.view.component.NavLink;
import com.ocpsoft.socialpm.gwt.client.local.view.component.SigninStatus;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.user.Profile;

public interface ProfileView extends FixedLayout
{
   public interface Presenter extends FixedLayout.Presenter
   {
   }

   NavLink getBrandLink();

   HeroPanel getGreeting();

   void setProfile(Profile response);

   SigninStatus getSigninStatus();

   void setProjects(List<Project> projects);

}
