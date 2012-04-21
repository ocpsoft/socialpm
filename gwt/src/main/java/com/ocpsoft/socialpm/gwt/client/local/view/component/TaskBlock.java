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
import com.ocpsoft.socialpm.model.project.story.Task;

public class TaskBlock extends Composite implements HasClickHandlers
{
   interface TaskBlockBinder extends UiBinder<Widget, TaskBlock>
   {
   }

   private static TaskBlockBinder binder = GWT.create(TaskBlockBinder.class);
   private Task task;

   @UiField
   Span text;

   Label wrapped = null;

   public TaskBlock()
   {
      initWidget(binder.createAndBindUi(this));
      sinkEvents(Event.ONCLICK);
   }

   public TaskBlock(Task task)
   {
      this();
      setTask(task);
   }

   public TaskBlock setTask(Task task)
   {
      this.task = task;
      text.setInnerText(task.getText());
      return this;
   }

   public Task getTask()
   {
      return task;
   }

   @Override
   public HandlerRegistration addClickHandler(ClickHandler clickHandler)
   {
      return addHandler(clickHandler, ClickEvent.getType());
   }
}
