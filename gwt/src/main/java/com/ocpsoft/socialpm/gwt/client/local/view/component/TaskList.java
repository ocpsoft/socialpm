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
import com.ocpsoft.socialpm.model.feed.TaskCreated;
import com.ocpsoft.socialpm.model.project.story.Story;
import com.ocpsoft.socialpm.model.project.story.Task;

@Dependent
public class TaskList extends Composite
{
   interface TaskListBinder extends UiBinder<Widget, TaskList>
   {
   }

   private static TaskListBinder binder = GWT.create(TaskListBinder.class);

   @UiField
   UnorderedList list;

   @UiField
   SpanElement taskCount;

   @UiField
   NavLink newTask;

   private List<Task> tasks;

   private Story story;

   public TaskList()
   {
      initWidget(binder.createAndBindUi(this));
   }

   public void setTasks(List<Task> tasks)
   {
      list.clear();
      this.tasks = new ArrayList<Task>();
      for (Task iter : tasks) {
         addTask(iter);
      }
      taskCount.setInnerText(String.valueOf(tasks.size()));
   }

   public List<Task> getTasks()
   {
      return tasks;
   }

   public void handleTaskCreated(@Observes TaskCreated event)
   {
      System.out.println("Observed task event: " + event.getTask().getText());
      if (this.story != null && this.story.equals(event.getStory()))
      {
         addTask(event.getTask());
      }
   }

   private void addTask(final Task task)
   {
      tasks.add(task);

      TaskBlock block = new TaskBlock(task);
      block.addStyleName("clickable");

      list.add(block);

      block.addClickHandler(new ClickHandler() {
         @Override
         public void onClick(ClickEvent event)
         {
            System.out.println("clicked task: " + task);
         }
      });

      taskCount.setInnerText(String.valueOf(tasks.size()));
   }

   public void setStory(Story story)
   {
      this.story = story;
      newTask.addClickHandler(new ClickHandler() {
         @Override
         public void onClick(ClickEvent event)
         {
            System.out.println("Clicked new task");
         }
      });
   }

   public NavLink getNewTaskLink()
   {
      return newTask;
   }
}
