package com.ocpsoft.socialpm.gwt.client.local.view.story.create;

import com.ocpsoft.socialpm.gwt.client.local.view.FixedLayout;
import com.ocpsoft.socialpm.gwt.client.local.view.FixedLayout.FixedPresenter;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.project.story.Story;



public interface NewStoryView extends FixedLayout
{
   public interface Presenter extends FixedPresenter
   {
      void createStory(Project project, Story story);
   }

   Presenter getPresenter();

   void setPresenter(Presenter presenter);

   void clearInputs();

   void setProject(Project project);
}
