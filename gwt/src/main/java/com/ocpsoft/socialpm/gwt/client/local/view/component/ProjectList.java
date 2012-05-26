package com.ocpsoft.socialpm.gwt.client.local.view.component;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;

import org.jboss.errai.ui.shared.api.annotations.Replace;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.user.client.ui.Composite;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.model.feed.ProjectCreated;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.user.Profile;
import com.google.gwt.user.client.ui.Label;

@Dependent
@Templated
public class ProjectList extends Composite
{
   @Replace
   UnorderedList list;

   @Replace
   Label projectCount;

   @Replace
   NavLink newProject;

   private Profile owner;

   private List<Project> projects;

   @PostConstruct
   public final void init()
   {
      projectCount.getElement().setAttribute("style", "display:inline;");
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
      projectCount.setText(String.valueOf(projects.size()));
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
