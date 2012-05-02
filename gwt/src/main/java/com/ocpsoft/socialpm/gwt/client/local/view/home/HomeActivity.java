package com.ocpsoft.socialpm.gwt.client.local.view.home;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.errai.bus.client.api.ErrorCallback;
import org.jboss.errai.bus.client.api.Message;
import org.jboss.errai.bus.client.api.RemoteCallback;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.ocpsoft.socialpm.gwt.client.local.ClientFactory;
import com.ocpsoft.socialpm.gwt.client.local.view.events.LoginEvent;
import com.ocpsoft.socialpm.gwt.client.local.view.events.LogoutEvent;
import com.ocpsoft.socialpm.gwt.client.shared.Response;
import com.ocpsoft.socialpm.model.project.Project;

@Dependent
public class HomeActivity extends AbstractActivity implements HomeView.Presenter
{
   private final ClientFactory clientFactory;
   private final HomeView homeView;

   @Inject
   public HomeActivity(HomeView homeView, ClientFactory clientFactory)
   {
      this.homeView = homeView;
      this.clientFactory = clientFactory;
   }

   @Override
   public void start(AcceptsOneWidget containerWidget, EventBus eventBus)
   {
      homeView.setPresenter(this);
      containerWidget.setWidget(homeView.asWidget());
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

   @Override
   public void fireMessage(String text)
   {
      clientFactory.getEventFactory().fireMessage(text);
      System.out.println("Done handling click event!");
   }

   public void response(@Observes Response event)
   {
      System.out.println("Observed response " + event.getMessage());
      homeView.getGreeting().setContent("Message from server: " + event.getMessage());
   }

   public void handleLogin(@Observes LoginEvent event)
   {
      homeView.showDashboard(event.getProfile());

      clientFactory.getServiceFactory().getProjectService().call(new RemoteCallback<List<Project>>() {

         @Override
         public void callback(List<Project> projects)
         {
            homeView.getProjectList().setProjects(projects);
         }
      }, new ErrorCallback() {
         @Override
         public boolean error(Message message, Throwable throwable)
         {
            return false;
         }
      }).getByOwner(event.getProfile());
   }

   public void handleLogout(@Observes LogoutEvent event)
   {
      homeView.showSplash();
   }
}