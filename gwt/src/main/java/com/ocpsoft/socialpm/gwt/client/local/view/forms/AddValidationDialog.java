package com.ocpsoft.socialpm.gwt.client.local.view.forms;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.errai.bus.client.api.RemoteCallback;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextArea;
import com.ocpsoft.socialpm.gwt.client.local.ClientFactory;
import com.ocpsoft.socialpm.gwt.client.local.view.component.Div;
import com.ocpsoft.socialpm.gwt.client.local.view.component.ModalDialog;
import com.ocpsoft.socialpm.gwt.client.local.view.component.Span;
import com.ocpsoft.socialpm.model.project.story.Story;
import com.ocpsoft.socialpm.model.project.story.ValidationCriteria;

/**
 * A splash screen
 */
@Dependent
public class AddValidationDialog extends ModalDialog
{
   private final TextArea text = new TextArea();

   Button createButton = new Button();
   Button cancelButton = new Button();

   @Inject
   private ClientFactory clientFactory;

   private Story story;

   public AddValidationDialog()
   {
      super();

      hide();

      this.addHeader(new Span("Add validation criteria"));

      addContent(new Div("<h3>Description</h3>"));
      text.setWidth("500px");
      text.setHeight("150px");
      addContent(text);

      createButton.setText("Add validation");
      createButton.setStyleName("btn primary");
      addFooter(createButton);

      createButton.addClickHandler(new ClickHandler() {
         @Override
         public void onClick(ClickEvent event)
         {
            ValidationCriteria c = new ValidationCriteria();
            c.setText(text.getText());

            clientFactory.getServiceFactory().getStoryService().call(new RemoteCallback<ValidationCriteria>() {
               @Override
               public void callback(ValidationCriteria task)
               {
                  text.setText(null);
                  AddValidationDialog.this.hide();
               }
            }).createValidation(story, c);
         }
      });

      cancelButton.setText("Cancel");
      cancelButton.setStyleName("btn");
      addFooter(cancelButton);

      cancelButton.addClickHandler(new ClickHandler() {
         @Override
         public void onClick(ClickEvent event)
         {
            AddValidationDialog.this.hide();
         }
      });
   }

   public void setStory(Story story)
   {
      this.story = story;
   }
}
