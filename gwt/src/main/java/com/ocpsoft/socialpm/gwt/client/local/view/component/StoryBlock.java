package com.ocpsoft.socialpm.gwt.client.local.view.component;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.ocpsoft.socialpm.model.project.story.Story;

public class StoryBlock extends Composite implements HasClickHandlers
{
   interface StoryBlockBinder extends UiBinder<Widget, StoryBlock>
   {
   }

   private static StoryBlockBinder binder = GWT.create(StoryBlockBinder.class);
   private Story story;

   @UiField
   Span text;

   Label wrapped = null;

   public StoryBlock()
   {
      initWidget(binder.createAndBindUi(this));
      sinkEvents(Event.ONCLICK);
   }

   public StoryBlock(Story story)
   {
      this();
      setStory(story);
   }

   public StoryBlock setStory(Story story)
   {
      this.story = story;
      text.setInnerText("As a " + story.getRole() + ", I want " + story.getObjective() + ", so I can "
               + story.getResult());
      return this;
   }

   public Story getStory()
   {
      return story;
   }

   @Override
   public HandlerRegistration addClickHandler(ClickHandler clickHandler)
   {
      return addHandler(clickHandler, ClickEvent.getType());
   }
}
