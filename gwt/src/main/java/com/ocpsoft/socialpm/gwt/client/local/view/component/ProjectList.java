package com.ocpsoft.socialpm.gwt.client.local.view.component;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.errai.ioc.client.container.IOC;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.model.feed.ProjectCreated;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.user.Profile;

@Dependent
@Templated
public class ProjectList extends Composite
{
   @Inject
   @DataField
   UnorderedList list;

   @Inject
   @DataField
   Label projectCount;

   @Inject
   @DataField
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
      ProjectBlock block = IOC.getBeanManager().lookupBean(ProjectBlock.class).getInstance();
      block.setProject(project);
      list.add(block);
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
