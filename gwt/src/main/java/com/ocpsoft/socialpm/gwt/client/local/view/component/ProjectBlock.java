package com.ocpsoft.socialpm.gwt.client.local.view.component;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.user.client.ui.Composite;
import com.ocpsoft.socialpm.model.project.Project;

@Dependent
@Templated
public class ProjectBlock extends Composite
{
   private Project project;

   @Inject
   @DataField
   ProfileLink ownerLink;

   @Inject
   @DataField
   ProjectLink projectLink;

   @Inject
   @DataField
   ProjectLink assignmentsLink;

   public ProjectBlock setProject(Project project)
   {
      this.project = project;
      ownerLink.setProfile(project.getOwner());
      projectLink.setProject(project);
      assignmentsLink.setProject(project);
      return this;
   }

   public Project getProject()
   {
      return project;
   }
}
