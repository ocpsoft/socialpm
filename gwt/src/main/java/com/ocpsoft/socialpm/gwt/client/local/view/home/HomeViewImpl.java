package com.ocpsoft.socialpm.gwt.client.local.view.home;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.ocpsoft.socialpm.gwt.client.local.view.FixedLayoutView;
import com.ocpsoft.socialpm.gwt.client.local.view.component.Div;
import com.ocpsoft.socialpm.gwt.client.local.view.component.HeroPanel;
import com.ocpsoft.socialpm.gwt.client.local.view.component.ProjectList;
import com.ocpsoft.socialpm.gwt.client.local.view.component.Row;
import com.ocpsoft.socialpm.gwt.client.local.view.component.StatusFeed;
import com.ocpsoft.socialpm.gwt.client.local.view.component.WelcomeBar;
import com.ocpsoft.socialpm.model.feed.ProjectCreated;
import com.ocpsoft.socialpm.model.user.Profile;

@ApplicationScoped
public class HomeViewImpl extends FixedLayoutView implements HomeView
{
   private final HeroPanel greeting = new HeroPanel();
   private final Anchor sendMessageButton = new Anchor("Send it »");
   private final TextBox messageBox = new TextBox();

   @Inject
   private WelcomeBar welcomeBar;

   private final Row dashboard = new Row();

   private final StatusFeed statusFeed = new StatusFeed();

   @Inject
   private ProjectList projectList;

   private Presenter presenter;

   public HomeViewImpl()
   {
      super();
   }

   public void handleProjectCreated(@Observes ProjectCreated event)
   {
      System.out.println("Observed ProjectCreated event (ApplicationScoped)");
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

      greeting.setHeading("Willkommen!");
      greeting.setContent("Type a message and click to get started.");
      getContent().add(greeting);

      greeting.getUnder().add(getMessageBox());

      sendMessageButton.addStyleName("btn btn-primary btn-large");
      greeting.addAction(sendMessageButton);

      setupInputs();

      showSplash();
   }

   private void setupInputs()
   {
      messageBox.addKeyPressHandler(new KeyPressHandler() {

         @Override
         public void onKeyPress(KeyPressEvent event)
         {
            if (KeyCodes.KEY_ENTER == event.getCharCode())
            {
               event.preventDefault();
               presenter.fireMessage(getMessageBox().getText());
            }
         }
      });

      sendMessageButton.addClickHandler(new ClickHandler() {
         @Override
         public void onClick(ClickEvent event)
         {
            event.preventDefault();
            presenter.fireMessage(getMessageBox().getText());
         }
      });
   }

   @Override
   public void showDashboard(Profile profile)
   {
      projectList.setOwner(profile);
      greeting.setVisible(false);
      welcomeBar.setProfile(profile);
      welcomeBar.setVisible(true);
      dashboard.setVisible(true);
   }

   @Override
   public void showSplash()
   {
      greeting.setVisible(true);
      welcomeBar.setVisible(false);
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
