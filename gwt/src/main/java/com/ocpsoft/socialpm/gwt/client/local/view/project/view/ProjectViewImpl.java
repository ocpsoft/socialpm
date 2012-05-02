package com.ocpsoft.socialpm.gwt.client.local.view.project.view;

import javax.enterprise.context.ApplicationScoped;

import com.google.inject.Inject;
import com.ocpsoft.socialpm.gwt.client.local.view.FixedLayoutView;
import com.ocpsoft.socialpm.gwt.client.local.view.component.BreadCrumb;
import com.ocpsoft.socialpm.gwt.client.local.view.component.BreadCrumbList;
import com.ocpsoft.socialpm.gwt.client.local.view.component.Div;
import com.ocpsoft.socialpm.gwt.client.local.view.component.Heading;
import com.ocpsoft.socialpm.gwt.client.local.view.component.IterationList;
import com.ocpsoft.socialpm.gwt.client.local.view.component.ProfileLink;
import com.ocpsoft.socialpm.gwt.client.local.view.component.ProjectLink;
import com.ocpsoft.socialpm.gwt.client.local.view.component.Span;
import com.ocpsoft.socialpm.gwt.client.local.view.component.StoryList;
import com.ocpsoft.socialpm.model.project.Project;

@ApplicationScoped
public class ProjectViewImpl extends FixedLayoutView implements ProjectView
{
   private Project project;

   private Presenter presenter;

   ProjectLink projectLink = new ProjectLink();
   ProfileLink profileLink = new ProfileLink();
   Span pulse = new Span(":)");

   @Inject
   IterationList iterationList;

   @Inject
   StoryList storyList;

   @Override
   public void setup()
   {
      Div projectHeader = createProjectHeaderRow();
      Div body = createProjectBodyRow();

      content.add(projectHeader);
      content.add(body);

      storyList.setPresenter(getPresenter());
   }

   private Div createProjectHeaderRow()
   {
      Div projectHeader = new Div();
      projectHeader.setStyleName("row");
      Div left = new Div();
      left.setStyleName("span8 cols");
      Div right = new Div();
      right.setStyleName("span4 cols");

      projectHeader.add(left);
      projectHeader.add(right);

      BreadCrumbList breadcrumbs = new BreadCrumbList();
      breadcrumbs.push(new BreadCrumb(profileLink));
      breadcrumbs.push(new BreadCrumb(projectLink));

      Heading heading = new Heading(1);
      pulse.setStyleName("badge");
      heading.add(pulse);
      heading.add(new Span(" "));
      heading.add(breadcrumbs);
      heading.addStyleName("project-title");

      left.add(heading);
      return projectHeader;
   }

   private Div createProjectBodyRow()
   {
      Div projectHeader = new Div();
      projectHeader.setStyleName("row");
      Div left = new Div();
      left.setStyleName("span6 cols");
      Div right = new Div();
      right.setStyleName("span6 cols");

      projectHeader.add(left);
      projectHeader.add(right);

      right.add(iterationList);
      left.add(storyList);
      return projectHeader;
   }

   @Override
   public void setProject(Project project)
   {
      this.profileLink.setProfile(project.getOwner());
      this.projectLink.setProject(project);
      this.iterationList.setProject(project);
      this.iterationList.setIterations(project.getIterations());

      this.storyList.setPresenter(presenter);
      this.storyList.setProject(project);
      this.storyList.setStories(project.getStories());

      pulse.setInnerText(project.getOpenStories().size() + "");
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
