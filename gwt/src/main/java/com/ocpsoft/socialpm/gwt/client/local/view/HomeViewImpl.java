package com.ocpsoft.socialpm.gwt.client.local.view;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.ocpsoft.socialpm.gwt.client.local.EventsFactory;
import com.ocpsoft.socialpm.gwt.client.local.ServiceFactory;
import com.ocpsoft.socialpm.gwt.client.local.view.component.HeroPanel;
import com.ocpsoft.socialpm.gwt.client.local.view.events.LoginEvent;
import com.ocpsoft.socialpm.gwt.client.shared.Response;

@ApplicationScoped
public class HomeViewImpl extends FixedLayoutView implements HomeView
{
   private Presenter presenter;
   private final HeroPanel greeting = new HeroPanel();
   private final Anchor sendMessageButton = new Anchor("Send it »");
   private final TextBox messageBox = new TextBox();

   @Inject
   public HomeViewImpl(ServiceFactory serviceFactory, EventsFactory eventFactory)
   {
      super(serviceFactory, eventFactory);
      System.out.println("Construct HomeView");
   }

   public void onLogin(@Observes LoginEvent event)
   {
      if (presenter != null)
         presenter.handleLogin(event);
   }

   public void response(@Observes Response event)
   {
      System.out.println("Observed response " + event.getMessage());
      greeting.setContent("Message from server: " + event.getMessage());
   }

   @Override
   public void setPresenter(HomeView.Presenter presenter)
   {
      this.presenter = presenter;
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

}
