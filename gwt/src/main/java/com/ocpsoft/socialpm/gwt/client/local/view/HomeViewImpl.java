package com.ocpsoft.socialpm.gwt.client.local.view;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.errai.bus.client.api.ErrorCallback;
import org.jboss.errai.bus.client.api.Message;
import org.jboss.errai.bus.client.api.RemoteCallback;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.ocpsoft.socialpm.gwt.client.local.ClientFactory;
import com.ocpsoft.socialpm.gwt.client.local.view.component.Div;
import com.ocpsoft.socialpm.gwt.client.local.view.component.HeroPanel;
import com.ocpsoft.socialpm.gwt.client.local.view.component.ProjectList;
import com.ocpsoft.socialpm.gwt.client.local.view.component.Row;
import com.ocpsoft.socialpm.gwt.client.local.view.component.StatusFeed;
import com.ocpsoft.socialpm.gwt.client.local.view.component.WelcomeBar;
import com.ocpsoft.socialpm.gwt.client.local.view.events.LoginEvent;
import com.ocpsoft.socialpm.gwt.client.local.view.events.LogoutEvent;
import com.ocpsoft.socialpm.gwt.client.shared.Response;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.user.Profile;

@ApplicationScoped
public class HomeViewImpl extends FixedLayoutView implements HomeView
{
   private final HeroPanel greeting = new HeroPanel();
   private final Anchor sendMessageButton = new Anchor("Send it »");
   private final TextBox messageBox = new TextBox();
   private final WelcomeBar welcomeBar = new WelcomeBar();
   private final Row dashboard = new Row();
   private final ProjectList projectList = new ProjectList();
   private final StatusFeed statusFeed = new StatusFeed();

   @Inject
   private ClientFactory clientFactory;
   private Presenter presenter;
   
   public HomeViewImpl()
   {
      super();
   }

   @Override
   public void setup()
   {
      welcomeBar.setVisible(false);
      header.add(welcomeBar);

      dashboard.setVisible(false);
      Div left = new Div();
      left.setStyleName("span6 cols");
      left.add(statusFeed);

      Div right = new Div();
      right.setStyleName("span6 cols");
      right.add(projectList);
      dashboard.add(left);
      dashboard.add(right);
      getContent().add(dashboard);

      getGreeting().setHeading("Wilkommen!");
      getGreeting().setContent("Type a message and click to get started.");
      getContent().add(getGreeting());
      

      getGreeting().getUnder().add(getMessageBox());
      getSendMessageButton().addStyleName("btn btn-primary btn-large");
      getGreeting().addAction(getSendMessageButton());

      showSplash();
   }

   @Override
   public void handleLogin(LoginEvent event)
   {
      showDashboard(event.getProfile());
      loadProjects(event.getProfile());
   }

   @Override
   public void handleLogout(LogoutEvent event)
   {
      showSplash();
   }

   private void loadProjects(Profile profile)
   {
      clientFactory.getServiceFactory().getProjectService().call(new RemoteCallback<List<Project>>() {

         @Override
         public void callback(List<Project> projects)
         {
            getProjectList().setProjects(projects);
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

   public void response(@Observes Response event)
   {
      System.out.println("Observed response " + event.getMessage());
      greeting.setContent("Message from server: " + event.getMessage());
   }

   @Override
   public void showDashboard(Profile profile)
   {
      getGreeting().setVisible(false);
      getWelcomeBar().setProfile(profile);
      getWelcomeBar().setVisible(true);
      dashboard.setVisible(true);
   }

   @Override
   public void showSplash()
   {
      getGreeting().setVisible(true);
      getWelcomeBar().setVisible(false);
      getTopNav().getBrandLink().setEnabled(false);
      dashboard.setVisible(false);
   }

   @Override
   public ComplexPanel getContent()
   {
      return content;
   }

   @Override
   public HeroPanel getGreeting()
   {
      return greeting;
   }

   @Override
   public TextBox getMessageBox()
   {
      return messageBox;
   }

   @Override
   public Anchor getSendMessageButton()
   {
      return sendMessageButton;
   }

   @Override
   public WelcomeBar getWelcomeBar()
   {
      return welcomeBar;
   }

   @Override
   public ProjectList getProjectList()
   {
      return projectList;
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
