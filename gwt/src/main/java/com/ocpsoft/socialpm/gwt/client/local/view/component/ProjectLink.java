package com.ocpsoft.socialpm.gwt.client.local.view.component;

import com.ocpsoft.socialpm.gwt.client.local.view.project.view.ProjectPlace;
import com.ocpsoft.socialpm.model.project.Project;

public class ProjectLink extends NavLink
{
   public ProjectLink()
   {}

   public ProjectLink(Project project)
   {
      super(project.getName());
      setProject(project);
   }

   public ProjectLink setProject(Project project)
   {
      setTargetHistoryToken(new ProjectPlace.Tokenizer().getToken(new ProjectPlace(project.getOwner().getUsername(),
               project.getSlug())));
      setText(project.getName());
      return this;
   }
}
