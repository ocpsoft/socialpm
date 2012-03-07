package com.ocpsoft.socialpm.gwt.client.local.view;

import javax.enterprise.context.ApplicationScoped;

import com.google.gwt.user.client.ui.Label;
import com.ocpsoft.socialpm.gwt.client.local.view.component.HeroPanel;
import com.ocpsoft.socialpm.gwt.client.local.view.component.Span;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.user.Profile;

@ApplicationScoped
public class ProjectViewImpl extends FixedLayoutView implements ProjectView
{
   HeroPanel greeting = new HeroPanel();

   private final Label email = new Label();
   private Project project;

   @Override
   public void setup()
   {
      System.out.println("Construct ViewProfileView");
      content.add(greeting);
      greeting.getUnder().add(email);
   }

   @Override
   public void setProfile(final Profile profile)
   {
      greeting.setHeading("This is " + profile.getUsername() + "!");
      greeting.setContent(profile.getBio());
      email.setText(profile.getEmail());
   }

   @Override
   public void setProject(Project project)
   {
      this.project = project;
      content.add(new Span(project.getName()));
      this.setProfile(project.getOwner());
   }

   /*
    * Getters & Setters
    */
   @Override
   public HeroPanel getGreeting()
   {
      return greeting;
   }
}
