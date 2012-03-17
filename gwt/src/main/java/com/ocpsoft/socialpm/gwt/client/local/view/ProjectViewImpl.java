package com.ocpsoft.socialpm.gwt.client.local.view;

import javax.enterprise.context.ApplicationScoped;

import com.ocpsoft.socialpm.model.project.Project;

@ApplicationScoped
public class ProjectViewImpl extends FixedLayoutView implements ProjectView
{
   private Project project;

   private Presenter presenter;

   @Override
   public void setup()
   {
   }

   @Override
   public void setProject(Project project)
   {
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
