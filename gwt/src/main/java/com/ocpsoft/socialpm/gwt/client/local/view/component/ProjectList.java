package com.ocpsoft.socialpm.gwt.client.local.view.component;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.model.feed.ProjectCreated;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.user.Profile;

@Dependent
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

   private Profile owner;

   private List<Project> projects;

   public ProjectList()
   {
      initWidget(binder.createAndBindUi(this));
      newProject.setTargetHistoryToken(HistoryConstants.NEW_PROJECT());
   }

   public void setProjects(List<Project> projects)
   {
      list.clear();
      this.projects = new ArrayList<Project>();
      for (Project project : projects) {
         addProject(project);
      }
   }

   public List<Project> getProjects()
   {
      return projects;
   }

   public void handleProjectCreated(@Observes ProjectCreated event)
   {
      System.out.println("Observed ProjectCreated event");
      if (this.owner != null && this.owner.equals(event.getProject().getOwner()))
      {
         addProject(event.getProject());
      }
   }

   private void addProject(Project project)
   {
      projects.add(project);
      list.add(new ProjectBlock(project));
      projectCount.setInnerText(String.valueOf(projects.size()));
   }

   public void setOwner(Profile owner)
   {
      this.owner = owner;
   }

   public NavLink getNewProjectLink()
   {
      return newProject;
   }
}
