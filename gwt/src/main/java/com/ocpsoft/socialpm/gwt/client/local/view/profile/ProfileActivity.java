package com.ocpsoft.socialpm.gwt.client.local.view.profile;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.errai.bus.client.api.RemoteCallback;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.ocpsoft.socialpm.gwt.client.local.ClientFactory;
import com.ocpsoft.socialpm.gwt.client.local.history.CurrentHistory;
import com.ocpsoft.socialpm.gwt.client.local.view.component.Alert;
import com.ocpsoft.socialpm.gwt.client.local.view.component.Alert.AlertType;
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

      /*
       * Load the Profile entity from client-side sync'd EntityManager
       * Tell Errai that this is a sync-able data set?
       */
      // CriteriaBuilder builder = entityManager.getCriteriaBuilder();
      // CriteriaQuery<Profile> q = builder.createQuery(Profile.class);
      // Root<Profile> p = q.from(Profile.class);
      // CriteriaQuery<Profile> query = q.select(p)
      // .where(builder.equal(p.get("username"),
      // username));
      //
      // SyncableDataSet<Profile> dataSet = SyncableDataSet.create(entityManager, query);

      clientFactory.getServiceFactory().getProfileService().call(new RemoteCallback<Profile>() {

         @Override
         public void callback(Profile response)
         {
            profileView.setProfile(response);
         }

      }).getProfileByUsername(username);

      clientFactory.getServiceFactory().getProjectService().call(new RemoteCallback<List<Project>>() {

         @Override
         public void callback(List<Project> projects)
         {
            profileView.setProjects(projects);
         }
      }).getByOwner(new Profile(username));

      containerWidget.setWidget(profileView.asWidget());
   }

   @Override
   public void save(Profile profile)
   {
      // Not necessary with data-sync
      // entityManager.flush();
      clientFactory.getServiceFactory().getProfileService().call(new RemoteCallback<Profile>() {
         @Override
         public void callback(Profile result)
         {
            profileView.alert(new Alert(AlertType.SUCCESS, true).setInnerHTML("Changes successfully saved."));
            
            clientFactory.getServiceFactory().getProfileService().call(new RemoteCallback<Profile>() {
               @Override
               public void callback(Profile response)
               {
                  profileView.setProfile(response);
               }

            }).getProfileByUsername(username);
         }
      }).save(profile);
   }

   @Override
   public String mayStop()
   {
      // entityManager.flush();
      return null;
   }

   @Override
   public void goTo(Place place)
   {
      clientFactory.getPlaceController().goTo(place);
   }
}