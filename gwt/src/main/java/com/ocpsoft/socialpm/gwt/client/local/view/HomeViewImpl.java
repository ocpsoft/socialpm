package com.ocpsoft.socialpm.gwt.client.local.view;

import javax.enterprise.context.ApplicationScoped;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.ocpsoft.socialpm.gwt.client.local.view.component.Div;
import com.ocpsoft.socialpm.gwt.client.local.view.component.HeroPanel;
import com.ocpsoft.socialpm.gwt.client.local.view.component.ProjectList;
import com.ocpsoft.socialpm.gwt.client.local.view.component.Row;
import com.ocpsoft.socialpm.gwt.client.local.view.component.StatusFeed;
import com.ocpsoft.socialpm.gwt.client.local.view.component.WelcomeBar;
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

      greeting.setHeading("Wilkommen!");
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
      getMessageBox().addKeyPressHandler(new KeyPressHandler() {

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

      getSendMessageButton().addClickHandler(new ClickHandler() {
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
