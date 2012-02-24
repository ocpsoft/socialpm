package com.ocpsoft.socialpm.gwt.client.local.view.component;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.ocpsoft.socialpm.model.project.Project;

public class ProjectBlock extends Composite
{
   interface ProjectBlockBinder extends UiBinder<Widget, ProjectBlock>
   {
   }

   private static ProjectBlockBinder binder = GWT.create(ProjectBlockBinder.class);
   private Project project;

   @UiField
   ProfileLink ownerLink;

   @UiField
   ProjectLink projectLink;
   
   @UiField
   ProjectLink assignmentsLink;

   public ProjectBlock()
   {
      initWidget(binder.createAndBindUi(this));
   }

   public ProjectBlock(Project project)
   {
      this();
      setProject(project);
   }

   public ProjectBlock setProject(Project project)
   {
      this.project = project;
      ownerLink.setProfile(project.getOwner());
      projectLink.setProject(project);
      assignmentsLink.setProject(project);
      assignmentsLink.setText("tasks");
      return this;
   }

   public Project getProject()
   {
      return project;
   }
}
