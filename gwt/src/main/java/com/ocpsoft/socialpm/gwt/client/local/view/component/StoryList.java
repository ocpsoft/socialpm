package com.ocpsoft.socialpm.gwt.client.local.view.component;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.gwt.client.local.view.FixedLayout.FixedPresenter;
import com.ocpsoft.socialpm.gwt.client.local.view.story.view.StoryViewPlace;
import com.ocpsoft.socialpm.model.feed.StoryCreated;
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

   private FixedPresenter presenter;

   public StoryList()
   {
      initWidget(binder.createAndBindUi(this));
   }

   public void setStories(List<Story> stories)
   {
      list.clear();
      this.stories = new ArrayList<Story>();
      for (Story iter : stories) {
         addStory(iter);
      }
      storyCount.setInnerText(String.valueOf(stories.size()));
   }

   public List<Story> getStories()
   {
      return stories;
   }

   public void handleStoryCreated(@Observes StoryCreated event)
   {
      System.out.println("Observed story event: " + event.getStory().getNumber());
      if (this.project != null && this.project.equals(event.getProject()))
      {
         addStory(event.getStory());
      }
   }

   private void addStory(final Story story)
   {
      stories.add(story);

      StoryBlock block = new StoryBlock(story);
      block.addStyleName("clickable");

      list.add(block);

      block.addClickHandler(new ClickHandler() {
         @Override
         public void onClick(ClickEvent event)
         {
            System.out.println("clicked story: " + story);
            presenter.goTo(new StoryViewPlace(project.getOwner().getUsername(),
                     project.getSlug(), story.getNumber()));
         }
      });

      storyCount.setInnerText(String.valueOf(stories.size()));
   }

   public void setProject(Project project)
   {
      this.project = project;
      newStory.setTargetHistoryToken(HistoryConstants.NEW_STORY(project));
   }

   public NavLink getManageStoriesLink()
   {
      return newStory;
   }

   public void setPresenter(FixedPresenter presenter)
   {
      this.presenter = presenter;
   }
}
