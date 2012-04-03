package com.ocpsoft.socialpm.gwt.client.local.view;

import java.util.List;

import com.ocpsoft.socialpm.gwt.client.local.view.component.HeroPanel;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.user.Profile;

public interface ProfileView extends FixedLayout
{
   public interface Presenter extends FixedPresenter
   {
   }

   Presenter getPresenter();

   void setPresenter(Presenter presenter);

   HeroPanel getGreeting();

   void setProfile(Profile response);

   void setProjects(List<Project> projects);

}
