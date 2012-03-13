package com.ocpsoft.socialpm.gwt.client.local.view.component;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.model.project.Project;

public class ProjectList extends Composite
{
   interface ProjectListBinder extends UiBinder<Widget, ProjectList>
   {
   }

   private static ProjectListBinder binder = GWT.create(ProjectListBinder.class);
   
   @UiField
   UnorderedList list;
   
   @UiField
   SpanElement projectCount;
   
   @UiField
   NavLink newProject;

   public ProjectList()
   {
      initWidget(binder.createAndBindUi(this));
      newProject.setTargetHistoryToken(HistoryConstants.NEW_PROJECT());
   }

   public void setProjects(List<Project> projects)
   {
      list.clear();
      projectCount.setInnerText(String.valueOf(projects.size()));
      for (Project project : projects) {
         list.add(new ProjectBlock(project));
      }
   }
   
   public NavLink getNewProjectLink()
   {
      return newProject;
   }
}
