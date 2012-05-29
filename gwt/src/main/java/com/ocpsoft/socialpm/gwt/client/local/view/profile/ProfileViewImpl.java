package com.ocpsoft.socialpm.gwt.client.local.view.profile;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.ocpsoft.socialpm.gwt.client.local.view.FixedLayoutView;
import com.ocpsoft.socialpm.gwt.client.local.view.component.ProjectList;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.user.Profile;

@ApplicationScoped
public class ProfileViewImpl extends FixedLayoutView implements ProfileView
{
   @Inject
   private ProfileForm profileForm;
   
   @Inject
   private ProjectList projectList;

   private final Label email = new Label();
   private Presenter presenter;

   @Override
   public void setup()
   {
      profileForm.getSubmit().addClickHandler(new ClickHandler() {
         
         @Override
         public void onClick(ClickEvent event)
         {
            presenter.save(profileForm.getProfile());
         }
      });
      content.add(profileForm);
      content.add(projectList);
   }

   @Override
   public void setProfile(final Profile profile)
   {
      projectList.setOwner(profile);
      profileForm.setProfile(profile);
      email.setText(profile.getEmail());
   }

   /*
    * Getters & Setters
    */
   @Override
   public ProfileForm getProfileForm()
   {
      return profileForm;
   }

   @Override
   public void setProjects(List<Project> projects)
   {
      projectList.setProjects(projects);
   }

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
}
