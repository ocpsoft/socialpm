package com.ocpsoft.socialpm.gwt.client.local.view.story.view;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.ocpsoft.socialpm.gwt.client.local.view.FixedLayoutView;
import com.ocpsoft.socialpm.gwt.client.local.view.component.BreadCrumb;
import com.ocpsoft.socialpm.gwt.client.local.view.component.BreadCrumbList;
import com.ocpsoft.socialpm.gwt.client.local.view.component.Div;
import com.ocpsoft.socialpm.gwt.client.local.view.component.Heading;
import com.ocpsoft.socialpm.gwt.client.local.view.component.ProfileLink;
import com.ocpsoft.socialpm.gwt.client.local.view.component.ProjectLink;
import com.ocpsoft.socialpm.gwt.client.local.view.component.Span;
import com.ocpsoft.socialpm.gwt.client.local.view.component.StoryLink;
import com.ocpsoft.socialpm.gwt.client.local.view.component.TaskList;
import com.ocpsoft.socialpm.gwt.client.local.view.component.UnorderedList;
import com.ocpsoft.socialpm.gwt.client.local.view.component.ValidationList;
import com.ocpsoft.socialpm.gwt.client.local.view.forms.AddTaskDialog;
import com.ocpsoft.socialpm.gwt.client.local.view.forms.AddValidationDialog;
import com.ocpsoft.socialpm.model.project.story.Story;

@ApplicationScoped
public class StoryViewImpl extends FixedLayoutView implements StoryView
{
   private Story story;

   private Presenter presenter;

   ProjectLink projectLink = new ProjectLink();
   ProfileLink profileLink = new ProfileLink();
   StoryLink storyLink = new StoryLink();
   Span pulse = new Span(":)");

   Div storyText = new Div();
   Div detailsWell = new Div();

   @Inject
   AddTaskDialog addTaskDialog;

   @Inject
   private TaskList taskList;

   @Inject
   AddValidationDialog addValidationDialog;

   @Inject
   private ValidationList validationList;

   @Override
   public void setup()
   {
      Div projectHeader = createProjectHeaderRow();
      Div body = createProjectBodyRow();

      setupInputs();

      storyText.addStyleName("box");
      detailsWell.addStyleName("well");

      content.add(projectHeader);
      content.add(body);

      addTaskDialog.setVisible(false);
      content.add(addTaskDialog);

      addValidationDialog.setVisible(false);
      content.add(addValidationDialog);
   }

   private void setupInputs()
   {
      taskList.getNewTaskLink().addClickHandler(new ClickHandler() {
         @Override
         public void onClick(ClickEvent event)
         {
            addTaskDialog.display();
         }
      });
      validationList.getNewValidationCriteriaLink().addClickHandler(new ClickHandler() {
         @Override
         public void onClick(ClickEvent event)
         {
            addValidationDialog.display();
         }
      });
   }

   private Div createProjectHeaderRow()
   {
      Div projectHeader = new Div();
      projectHeader.setStyleName("row");
      Div left = new Div();
      left.setStyleName("span8 cols");
      Div right = new Div();
      right.setStyleName("span4 cols");

      projectHeader.add(left);
      projectHeader.add(right);

      BreadCrumbList breadcrumbs = new BreadCrumbList();
      breadcrumbs.push(new BreadCrumb(profileLink));
      breadcrumbs.push(new BreadCrumb(projectLink));
      breadcrumbs.push(new BreadCrumb(storyLink));

      Heading heading = new Heading(1);
      pulse.setStyleName("badge");
      heading.add(pulse);
      heading.add(new Span(" "));
      heading.add(breadcrumbs);
      heading.addStyleName("project-title");

      left.add(heading);
      return projectHeader;
   }

   private Div createProjectBodyRow()
   {
      Div row = new Div();
      row.setStyleName("row");
      Div left = new Div();
      left.setStyleName("span8 cols");
      Div right = new Div();
      right.setStyleName("span4 cols");

      left.add(storyText);

      left.add(validationList);
      left.add(taskList);

      row.add(left);

      right.add(detailsWell);
      row.add(right);

      return row;
   }

   @Override
   public void setStory(Story story)
   {
      this.story = story;
      this.profileLink.setProfile(story.getProject().getOwner());
      this.projectLink.setProject(story.getProject());
      this.storyLink.setStory(story);

      taskList.setStory(story);
      taskList.setTasks(story.getTasks());

      validationList.setStory(story);
      validationList.setValidations(story.getValidations());
      System.out.println(story.getTasks());

      addTaskDialog.setStory(story);
      addValidationDialog.setStory(story);

      pulse.setInnerText(story.getTaskCount() + "");
      storyText.setInnerHTML("<h3>As a " + story.getRole() + " I want " + story.getObjective() + " so that "
               + story.getResult());

      detailsWell.clear();
      detailsWell.add(new HTMLPanel("h3", "Story details"));
      UnorderedList list = new UnorderedList();

      list.add(new Span("Priority: " + story.getPriority()));
      list.add(new Span("Iteration: " + story.getIteration().getNumber() + " - " + story.getIteration().getTitle()));
      list.add(new Span("Burner: " + story.getBurner()));
      list.add(new Span("Points: " + story.getStoryPoints()));
      list.add(new Span("Business value: " + story.getBusinessValue()));

      detailsWell.add(list);

   }

   /*
    * Getters & Setters
    */
   public Story getStory()
   {
      return story;
   }

   @Override
   public Presenter getPresenter()
   {
      return presenter;
   }

   @Override
   public void setPresenter(Presenter presenter)
   {
      this.presenter = presenter;
   }
}
