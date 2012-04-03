package com.ocpsoft.socialpm.gwt.client.local.view;

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
