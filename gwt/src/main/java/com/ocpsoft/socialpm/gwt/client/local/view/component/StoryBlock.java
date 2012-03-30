package com.ocpsoft.socialpm.gwt.client.local.view.component;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.project.story.Story;

public class StoryBlock extends Composite
{
   interface StoryBlockBinder extends UiBinder<Widget, StoryBlock>
   {
   }

   private static StoryBlockBinder binder = GWT.create(StoryBlockBinder.class);
   private Project story;

   @UiField
   ProfileLink ownerLink;

   @UiField
   ProjectLink projectLink;

   @UiField
   ProjectLink assignmentsLink;

   public StoryBlock()
   {
      initWidget(binder.createAndBindUi(this));
   }

   public StoryBlock(Story story)
   {
      this();
      setProject(story.getProject());
   }

   public StoryBlock setProject(Project project)
   {
      this.story = project;
      ownerLink.setProfile(project.getOwner());
      projectLink.setProject(project);
      assignmentsLink.setProject(project);
      assignmentsLink.setText("tasks");
      return this;
   }

   public Project getProject()
   {
      return story;
   }
}
