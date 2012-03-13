package com.ocpsoft.socialpm.gwt.client.local.view;

import com.google.gwt.place.shared.Place;
import com.ocpsoft.socialpm.gwt.client.local.view.component.HeroPanel;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.user.Profile;

public interface ProjectView extends FixedLayout
{
   public interface Presenter
   {
      void goTo(Place place);
   }
   
   Presenter getPresenter();
   
   void setPresenter(Presenter presenter);

   HeroPanel getGreeting();

   void setProfile(Profile response);

   void setProject(Project project);

}
