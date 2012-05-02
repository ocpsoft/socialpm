package com.ocpsoft.socialpm.gwt.client.local.view.project.view;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

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
public class ProjectActivity extends AbstractActivity implements ProjectView.Presenter
{
   private final ClientFactory clientFactory;
   private final ProjectView projectView;
   private final String username;
   private final String slug;

   @Inject
   public ProjectActivity(ClientFactory clientFactory, ProjectView projectView, @CurrentHistory Place place)
   {
      this.clientFactory = clientFactory;
      this.projectView = projectView;
      this.username = ((ProjectPlace) place).getUsername();
      this.slug = ((ProjectPlace) place).getSlug();
   }

   @Override
   public void start(AcceptsOneWidget containerWidget, EventBus eventBus)
   {
      projectView.setPresenter(this);

      clientFactory.getServiceFactory().getProjectService().call(new RemoteCallback<Project>() {

         @Override
         public void callback(Project project)
         {
            projectView.setProject(project);
         }
      }).getByOwnerAndSlug(new Profile(username), slug);

      containerWidget.setWidget(projectView.asWidget());
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