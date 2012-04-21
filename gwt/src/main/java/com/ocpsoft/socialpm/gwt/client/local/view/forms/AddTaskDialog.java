package com.ocpsoft.socialpm.gwt.client.local.view.forms;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueListBox;
import com.ocpsoft.socialpm.gwt.client.local.view.component.Div;
import com.ocpsoft.socialpm.gwt.client.local.view.component.ModalDialog;
import com.ocpsoft.socialpm.gwt.client.local.view.component.Span;
import com.ocpsoft.socialpm.gwt.client.shared.rpc.StoryService;
import com.ocpsoft.socialpm.model.project.story.Story;
import com.ocpsoft.socialpm.model.user.Profile;

/**
 * A splash screen
 */
@Dependent
public class AddTaskDialog extends ModalDialog
{
   private final TextArea text = new TextArea();

   private final TextBox hours = new TextBox();

   ValueListBox<Profile> assignee = new ValueListBox<Profile>(profileRenderer);
   static ProfileRenderer profileRenderer = new ProfileRenderer();

   Button createButton = new Button();

   Button cancelButton = new Button();

   @Inject
   private StoryService storyService;

   private Story story;

   public AddTaskDialog()
   {
      super();

      hide();

      this.addHeader(new Span("Add a task"));

      addContent(new Div("<h3>Description</h3>"));
      text.setWidth("500px");
      text.setHeight("150px");
      addContent(text);

      addContent(new Div("<h3>Hours</h3>"));
      hours.setWidth("60px");
      addContent(hours);

      addContent(new Div("<h3>Assignee</h3>"));
      addContent(assignee);

      createButton.setText("Create task");
      addFooter(createButton);

      cancelButton.setText("Cancel");
      addFooter(cancelButton);

      cancelButton.addClickHandler(new ClickHandler() {

         @Override
         public void onClick(ClickEvent event)
         {
            AddTaskDialog.this.hide();
         }
      });
   }

   public void setStory(Story story)
   {
      this.story = story;
      this.assignee.setAcceptableValues(story.getProject().getActiveMembers());
   }
}
