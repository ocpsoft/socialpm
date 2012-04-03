package com.ocpsoft.socialpm.gwt.client.local.view.forms;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.Widget;
import com.ocpsoft.socialpm.gwt.client.local.view.component.Span;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.project.iteration.Iteration;

/**
 * A splash screen
 */
public class NewStoryForm extends Composite
{
   interface NewStoryFormBinder extends UiBinder<Widget, NewStoryForm>
   {
   }

   private static NewStoryFormBinder binder = GWT.create(NewStoryFormBinder.class);

   @UiField
   FormPanel form;

   @UiField
   Span formTitle;

   @UiField
   TextBox role;

   @UiField
   TextArea objective;

   @UiField
   TextArea result;

   @UiField(provided = true)
   ValueListBox<Iteration> iteration = new ValueListBox<Iteration>(iterationRenderer);
   static IterationRenderer iterationRenderer = new IterationRenderer();

   @UiField
   Button createButton;

   @UiField
   Button cancelButton;

   public NewStoryForm()
   {
      initWidget(binder.createAndBindUi(this));
      formTitle.setInnerText(" ");
   }

   public void setProject(Project project)
   {
      formTitle.setInnerText(project.getName() + "  ");
      iteration.setAcceptableValues(project.getOpenIterations());
      iteration.setValue(project.getCurrentIteration(), false);
   }

   public FormPanel getForm()
   {
      return form;
   }

   public Span getFormTitle()
   {
      return formTitle;
   }

   public TextBox getRole()
   {
      return role;
   }

   public TextArea getObjective()
   {
      return objective;
   }

   public TextArea getResult()
   {
      return result;
   }

   public ValueListBox<Iteration> getIteration()
   {
      return iteration;
   }

   public Button getCreateButton()
   {
      return createButton;
   }

   public Button getCancelButton()
   {
      return cancelButton;
   }
}
