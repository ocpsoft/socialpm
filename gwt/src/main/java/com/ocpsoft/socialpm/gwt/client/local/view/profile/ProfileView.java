package com.ocpsoft.socialpm.gwt.client.local.view.profile;

import java.util.List;

import com.ocpsoft.socialpm.gwt.client.local.view.FixedLayout;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.user.Profile;

public interface ProfileView extends FixedLayout
{
   public interface Presenter extends FixedPresenter
   {
      void save(Profile profile);
   }

   Presenter getPresenter();

   void setPresenter(Presenter presenter);

   public ProfileForm getProfileForm();

   void setProfile(Profile response);

   void setProjects(List<Project> projects);

}
