package com.ocpsoft.socialpm.gwt.client.local.view.project.create;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.errai.bus.client.api.ErrorCallback;
import org.jboss.errai.bus.client.api.Message;
import org.jboss.errai.bus.client.api.RemoteCallback;
import org.jboss.errai.ioc.client.api.Caller;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.ocpsoft.socialpm.gwt.client.local.App;
import com.ocpsoft.socialpm.gwt.client.local.ClientFactory;
import com.ocpsoft.socialpm.gwt.client.local.view.component.Alert;
import com.ocpsoft.socialpm.gwt.client.local.view.project.view.ProjectPlace;
import com.ocpsoft.socialpm.gwt.client.shared.rpc.ProjectService;
import com.ocpsoft.socialpm.model.project.Project;

@Dependent
public class NewProjectActivity extends AbstractActivity implements NewProjectView.Presenter
{
   private final ClientFactory clientFactory;
   private final NewProjectView newProjectView;

   @Inject
   public NewProjectActivity(ClientFactory clientFactory, NewProjectView newProjectView)
   {
      this.newProjectView = newProjectView;
      this.clientFactory = clientFactory;
   }

   @Override
   public void start(AcceptsOneWidget containerWidget, EventBus eventBus)
   {
      newProjectView.setPresenter(this);
      containerWidget.setWidget(newProjectView.asWidget());
      newProjectView.focusProjectName();
   }

   @Override
   public void createProject(final String projectName)
   {
      Caller<ProjectService> projectService = clientFactory.getServiceFactory().getProjectService();

      projectService.call(new RemoteCallback<Project>() {
         @Override
         public void callback(Project project)
         {
            newProjectView.clearAlerts();
            newProjectView.clearInputs();
            goTo(new ProjectPlace(project.getOwner().getUsername(), project.getSlug()));
         }
      }, new ErrorCallback() {
         @Override
         public boolean error(Message message, Throwable throwable)
         {
            newProjectView.alert(Alert.error().setInnerHTML("Error creating project <b>" + projectName + "</b>."));
            return false;
         }
      }).create(App.getLoggedInProfile(), projectName);
   }

   @Override
   public void verifyProject(final String projectName)
   {
      Caller<ProjectService> projectService = clientFactory.getServiceFactory().getProjectService();

      projectService.call(new RemoteCallback<Boolean>() {
         @Override
         public void callback(Boolean response)
         {
            if (!response)
            {
               Alert alert = Alert.error()
                        .setInnerHTML("The project name <b>" + projectName + "</b> is already in use.")
                        .setCloseable(false);
               newProjectView.alert(alert);
               newProjectView.setSubmitEnabled(false);
            }
            else
            {
               newProjectView.clearAlerts();
               newProjectView.setSubmitEnabled(true);
            }
         }
      }).verifyAvailable(App.getLoggedInProfile(), projectName);
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