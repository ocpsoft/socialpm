package com.ocpsoft.socialpm.gwt.client.local.view;

import javax.enterprise.context.ApplicationScoped;

import com.ocpsoft.socialpm.gwt.client.local.view.component.BreadCrumb;
import com.ocpsoft.socialpm.gwt.client.local.view.component.BreadCrumbStack;
import com.ocpsoft.socialpm.gwt.client.local.view.component.Div;
import com.ocpsoft.socialpm.gwt.client.local.view.component.NavLink;
import com.ocpsoft.socialpm.gwt.client.local.view.component.ProfileLink;
import com.ocpsoft.socialpm.gwt.client.local.view.component.ProjectLink;
import com.ocpsoft.socialpm.model.project.Project;

@ApplicationScoped
public class ProjectViewImpl extends FixedLayoutView implements ProjectView
{
   private Project project;

   private Presenter presenter;

   ProjectLink projectLink = new ProjectLink();
   ProfileLink profileLink = new ProfileLink();

   @Override
   public void setup()
   {
      Div row = new Div();
      row.setStyleName("row");
      Div left = new Div();
      left.setStyleName("span6 cols");
      Div right = new Div();
      right.setStyleName("span6 cols");
      
      row.add(left);
      row.add(right);
      
      BreadCrumbStack breadcrumbs = new BreadCrumbStack();
      breadcrumbs.push(new BreadCrumb(profileLink));
      breadcrumbs.push(new BreadCrumb(projectLink));
      left.add(breadcrumbs);
      
      content.add(row);
   }

   @Override
   public void setProject(Project project)
   {
      this.profileLink.setProfile(project.getOwner());
      this.projectLink.setProject(project);
   }

   /*
    * Getters & Setters
    */
   @Override
   public Presenter getPresenter()
   {
      return presenter;
   }

   @Override
   public void setPresenter(Presenter presenter)
   {
      this.presenter = presenter;
   }
   
   public Project getProject()
   {
      return project;
   }
}
