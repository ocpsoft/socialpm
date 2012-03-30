package com.ocpsoft.socialpm.gwt.client.local.view.component;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.model.feed.StoryCreated;
import com.ocpsoft.socialpm.model.feed.StoryEvent;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.project.story.Story;

@Dependent
public class StoryList extends Composite
{
   interface StoryListBinder extends UiBinder<Widget, StoryList>
   {
   }

   private static StoryListBinder binder = GWT.create(StoryListBinder.class);

   @UiField
   UnorderedList list;

   @UiField
   SpanElement storyCount;

   @UiField
   NavLink newStory;

   private Project project;

   private List<Story> stories;

   public StoryList()
   {
      initWidget(binder.createAndBindUi(this));
      newStory.setTargetHistoryToken(HistoryConstants.NEW_PROJECT());
   }

   public void setStories(List<Story> story)
   {
      list.clear();
      this.stories = new ArrayList<Story>();
      for (Story iter : story) {
         addStory(iter);
      }
   }

   public List<Story> getStories()
   {
      return stories;
   }

   public void handleStoryCreated(@Observes StoryEvent event)
   {
      if (event instanceof StoryCreated)
      {
         if (this.project != null && this.project.equals(event.getProject()))
         {
            addStory(event.getStory());
         }
      }
   }

   private void addStory(Story story)
   {
      stories.add(story);

      StoryBlock block = new StoryBlock(story);

      list.add(block);
      storyCount.setInnerText(String.valueOf(stories.size()));
   }

   public void setProject(Project project)
   {
      this.project = project;
   }

   public NavLink getManageStoriesLink()
   {
      return newStory;
   }
}
