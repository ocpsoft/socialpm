package com.ocpsoft.socialpm.gwt.client.local.view;

import com.google.gwt.place.shared.Place;
import com.ocpsoft.socialpm.model.project.Project;

public interface ProjectView extends FixedLayout
{
   public interface Presenter
   {
      void goTo(Place place);
   }
   
   Presenter getPresenter();
   
   void setPresenter(Presenter presenter);

   void setProject(Project project);

}
