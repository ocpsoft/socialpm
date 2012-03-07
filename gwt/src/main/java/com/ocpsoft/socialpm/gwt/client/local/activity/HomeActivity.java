package com.ocpsoft.socialpm.gwt.client.local.activity;

import java.util.List;

import org.jboss.errai.bus.client.api.ErrorCallback;
import org.jboss.errai.bus.client.api.Message;
import org.jboss.errai.bus.client.api.RemoteCallback;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.ocpsoft.socialpm.gwt.client.local.ClientFactory;
import com.ocpsoft.socialpm.gwt.client.local.places.HomePlace;
import com.ocpsoft.socialpm.gwt.client.local.view.HomeView;
import com.ocpsoft.socialpm.gwt.client.local.view.events.LoginEvent;
import com.ocpsoft.socialpm.gwt.client.local.view.events.LogoutEvent;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.user.Profile;

public class HomeActivity extends AbstractActivity implements HomeView.Presenter
{
   private final ClientFactory clientFactory;

   public HomeActivity(HomePlace place, ClientFactory clientFactory)
   {
      this.clientFactory = clientFactory;
   }

   @Override
   public void start(AcceptsOneWidget containerWidget, EventBus eventBus)
   {
      final HomeView homeView = clientFactory.getHomeView();
      homeView.setPresenter(this);
      setupInputs(homeView);
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

   private void setupInputs(final HomeView homeView)
   {
      homeView.getGreeting().getUnder().add(homeView.getMessageBox());
      homeView.getMessageBox().addKeyPressHandler(new KeyPressHandler() {

         @Override
         public void onKeyPress(KeyPressEvent event)
         {
            if (KeyCodes.KEY_ENTER == event.getCharCode())
            {
               event.preventDefault();
               fireMessage(homeView.getMessageBox().getText());
            }
         }
      });

      homeView.getSendMessageButton().addStyleName("btn btn-primary btn-large");
      homeView.getSendMessageButton().addClickHandler(new ClickHandler() {
         @Override
         public void onClick(ClickEvent event)
         {
            event.preventDefault();

            fireMessage(homeView.getMessageBox().getText());
         }
      });
      homeView.getGreeting().addAction(homeView.getSendMessageButton());
   }

   private void fireMessage(String text)
   {
      clientFactory.getEventFactory().fireMessage(text);
      System.out.println("Done handling click event!");
   }

   @Override
   public void handleLogin(LoginEvent event)
   {
      HomeView homeView = clientFactory.getHomeView();
      homeView.showDashboard(event.getProfile());

      loadProjects(event.getProfile());
   }

   @Override
   public void handleLogout(LogoutEvent event)
   {
      HomeView homeView = clientFactory.getHomeView();
      homeView.showSplash();
   }

   private void loadProjects(Profile profile)
   {
      clientFactory.getServiceFactory().getProjectService().call(new RemoteCallback<List<Project>>() {

         @Override
         public void callback(List<Project> projects)
         {

            HomeView homeView = clientFactory.getHomeView();
            homeView.getProjectList().setProjects(projects);

         }
      }, new ErrorCallback() {

         @Override
         public boolean error(Message message, Throwable throwable)
         {
            System.out.println("error");
            return false;
         }
      }).getByOwner(profile);
   }
}