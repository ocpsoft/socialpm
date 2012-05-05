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
import com.ocpsoft.socialpm.model.feed.ValidationCreated;
import com.ocpsoft.socialpm.model.project.story.Story;
import com.ocpsoft.socialpm.model.project.story.ValidationCriteria;

@Dependent
public class ValidationList extends Composite
{
   interface ValidationListBinder extends UiBinder<Widget, ValidationList>
   {
   }

   private static ValidationListBinder binder = GWT.create(ValidationListBinder.class);

   @UiField
   UnorderedList list;

   @UiField
   SpanElement validationCount;

   @UiField
   NavLink newValidation;

   private List<ValidationCriteria> validations;

   private Story story;

   public ValidationList()
   {
      initWidget(binder.createAndBindUi(this));
   }

   public void setValidations(List<ValidationCriteria> tasks)
   {
      list.clear();
      this.validations = new ArrayList<ValidationCriteria>();
      for (ValidationCriteria iter : tasks) {
         addValidationCriteria(iter);
      }
      validationCount.setInnerText(String.valueOf(tasks.size()));
   }

   public List<ValidationCriteria> getValidations()
   {
      return validations;
   }

   public void handleValidationCriteriaCreated(@Observes ValidationCreated event)
   {
      System.out.println("Observed task event: " + event.getValidation().getText());
      if (this.story != null && this.story.equals(event.getStory()))
      {
         addValidationCriteria(event.getValidation());
      }
   }

   private void addValidationCriteria(final ValidationCriteria validation)
   {
      validations.add(validation);

      ValidationBlock block = new ValidationBlock(validation);
      block.addStyleName("clickable");

      list.add(block);

      block.addClickHandler(new ClickHandler() {
         @Override
         public void onClick(ClickEvent event)
         {
            System.out.println("clicked validation: " + validation);
         }
      });

      validationCount.setInnerText(String.valueOf(validations.size()));
   }

   public void setStory(Story story)
   {
      this.story = story;
      newValidation.addClickHandler(new ClickHandler() {
         @Override
         public void onClick(ClickEvent event)
         {
            System.out.println("Clicked new task");
         }
      });
   }

   public NavLink getNewValidationCriteriaLink()
   {
      return newValidation;
   }
}
