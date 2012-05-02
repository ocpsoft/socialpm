package com.ocpsoft.socialpm.gwt.client.local.view.profile;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.errai.bus.client.api.ErrorCallback;
import org.jboss.errai.bus.client.api.Message;
import org.jboss.errai.bus.client.api.RemoteCallback;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.ocpsoft.socialpm.gwt.client.local.ClientFactory;
import com.ocpsoft.socialpm.gwt.client.local.history.CurrentHistory;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.user.Profile;

@Dependent
public class ProfileActivity extends AbstractActivity implements ProfileView.Presenter
{
   private final ClientFactory clientFactory;
   private final String username;
   private final ProfileView profileView;

   @Inject
   public ProfileActivity(ClientFactory clientFactory, ProfileView profileView, @CurrentHistory Place place)
   {
      this.clientFactory = clientFactory;
      this.profileView = profileView;
      this.username = ((ProfilePlace) place).getUsername();
   }

   @Override
   public void start(AcceptsOneWidget containerWidget, EventBus eventBus)
   {
      profileView.setPresenter(this);

      clientFactory.getServiceFactory().getProfileService().call(new RemoteCallback<Profile>() {

         @Override
         public void callback(Profile response)
         {
            profileView.setProfile(response);
         }

      }, new ErrorCallback() {

         @Override
         public boolean error(Message message, Throwable throwable)
         {
            throwable.printStackTrace();
            return false;
         }

      }).getProfileByUsername(username);

      clientFactory.getServiceFactory().getProjectService().call(new RemoteCallback<List<Project>>() {

         @Override
         public void callback(List<Project> projects)
         {
            profileView.setProjects(projects);
         }
      }, new ErrorCallback() {

         @Override
         public boolean error(Message message, Throwable throwable)
         {
            System.out.println("error");
            return false;
         }
      }).getByOwner(new Profile(username));

      containerWidget.setWidget(profileView.asWidget());
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