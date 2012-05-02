package com.ocpsoft.socialpm.gwt.client.local.view.project.view;

import com.ocpsoft.socialpm.gwt.client.local.view.FixedLayout;
import com.ocpsoft.socialpm.gwt.client.local.view.FixedLayout.FixedPresenter;
import com.ocpsoft.socialpm.model.project.Project;

public interface ProjectView extends FixedLayout
{
   public interface Presenter extends FixedPresenter
   {
   }

   Presenter getPresenter();

   void setPresenter(Presenter presenter);

   void setProject(Project project);

}
