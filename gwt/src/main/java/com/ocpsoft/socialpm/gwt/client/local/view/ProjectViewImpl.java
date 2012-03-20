package com.ocpsoft.socialpm.gwt.client.local.view;

import javax.enterprise.context.ApplicationScoped;

import com.ocpsoft.socialpm.gwt.client.local.view.component.BreadCrumb;
import com.ocpsoft.socialpm.gwt.client.local.view.component.BreadCrumbList;
import com.ocpsoft.socialpm.gwt.client.local.view.component.Div;
import com.ocpsoft.socialpm.gwt.client.local.view.component.Heading;
import com.ocpsoft.socialpm.gwt.client.local.view.component.ProfileLink;
import com.ocpsoft.socialpm.gwt.client.local.view.component.ProjectLink;
import com.ocpsoft.socialpm.gwt.client.local.view.component.Span;
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
      left.setStyleName("span8 cols");
      Div right = new Div();
      right.setStyleName("span4 cols");
      
      row.add(left);
      row.add(right);
      
      BreadCrumbList breadcrumbs = new BreadCrumbList();
      breadcrumbs.push(new BreadCrumb(profileLink));
      breadcrumbs.push(new BreadCrumb(projectLink));
      
      Heading heading = new Heading(1);
      Span badge = new Span(":)");
      badge.setStyleName("badge");
      heading.add(new Span(" "));
      heading.add(badge);
      heading.add(breadcrumbs);
      heading.addStyleName("project-title");
      
      left.add(heading);
      
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
