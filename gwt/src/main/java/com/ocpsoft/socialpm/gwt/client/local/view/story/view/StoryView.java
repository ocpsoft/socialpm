package com.ocpsoft.socialpm.gwt.client.local.view.story.view;

import com.ocpsoft.socialpm.gwt.client.local.view.FixedLayout;
import com.ocpsoft.socialpm.gwt.client.local.view.FixedLayout.FixedPresenter;
import com.ocpsoft.socialpm.model.project.story.Story;

public interface StoryView extends FixedLayout
{
   public interface Presenter extends FixedPresenter
   {
   }

   Presenter getPresenter();

   void setPresenter(Presenter presenter);

   void setStory(Story story);

}
