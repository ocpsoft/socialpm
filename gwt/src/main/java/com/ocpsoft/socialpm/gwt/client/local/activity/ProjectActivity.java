package com.ocpsoft.socialpm.gwt.client.local.activity;

import org.jboss.errai.bus.client.api.RemoteCallback;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.ocpsoft.socialpm.gwt.client.local.App;
import com.ocpsoft.socialpm.gwt.client.local.ClientFactory;
import com.ocpsoft.socialpm.gwt.client.local.places.ProjectPlace;
import com.ocpsoft.socialpm.gwt.client.local.view.ProfileView;
import com.ocpsoft.socialpm.gwt.client.local.view.ProjectView;
import com.ocpsoft.socialpm.gwt.client.local.view.events.LoginEvent;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.user.Profile;

public class ProjectActivity extends AbstractActivity implements ProjectView.Presenter
{
   private final ClientFactory clientFactory;
   private final String username;
   private final String slug;

   public ProjectActivity(ProjectPlace place, ClientFactory clientFactory)
   {
      this.clientFactory = clientFactory;
      this.username = place.getUsername();
      this.slug = place.getSlug();
   }

   @Override
   public void start(AcceptsOneWidget containerWidget, EventBus eventBus)
   {
      final ProjectView projectView = clientFactory.getProjectView();
      projectView.setPresenter(this);

      projectView.getGreeting().setHeading("Loading...");

      clientFactory.getServiceFactory().getProjectService().call(new RemoteCallback<Project>() {

         @Override
         public void callback(Project project)
         {
            System.out.println(project);
         }
      }).getByOwnerAndSlug(new Profile(username), slug);

      Profile loggedInProfile = App.getLoggedInProfile();
      if (loggedInProfile != null)
      {
         handleLogin(new LoginEvent(loggedInProfile));
      }

      containerWidget.setWidget(projectView.asWidget());
   }

   @Override
   public void handleLogin(LoginEvent event)
   {
      ProfileView profileView = clientFactory.getProfileView();
      profileView.getSigninStatus().setSignedIn(event.getProfile());
   }

   @Override
   public String mayStop()
   {
      return null;
   }

   @Override
   public void goTo(Place place)
   {
      clientFactory.getPlaceController().goTo(place);
   }
}