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
import com.ocpsoft.socialpm.model.project.story.ValidationCriteria;

public class ValidationBlock extends Composite implements HasClickHandlers
{
   interface ValidationCriteriaBlockBinder extends UiBinder<Widget, ValidationBlock>
   {
   }

   private static ValidationCriteriaBlockBinder binder = GWT.create(ValidationCriteriaBlockBinder.class);
   private ValidationCriteria validation;

   @UiField
   Span text;

   Label wrapped = null;

   public ValidationBlock()
   {
      initWidget(binder.createAndBindUi(this));
      sinkEvents(Event.ONCLICK);
   }

   public ValidationBlock(ValidationCriteria task)
   {
      this();
      setValidationCriteria(task);
   }

   public ValidationBlock setValidationCriteria(ValidationCriteria validation)
   {
      this.validation = validation;
      text.setInnerText(validation.getText());
      return this;
   }

   public ValidationCriteria getValidationCriteria()
   {
      return validation;
   }

   @Override
   public HandlerRegistration addClickHandler(ClickHandler clickHandler)
   {
      return addHandler(clickHandler, ClickEvent.getType());
   }
}
