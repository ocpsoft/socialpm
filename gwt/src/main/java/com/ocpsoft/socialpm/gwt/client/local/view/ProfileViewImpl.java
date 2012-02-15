package com.ocpsoft.socialpm.gwt.client.local.view;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.google.gwt.user.client.ui.Label;
import com.ocpsoft.socialpm.gwt.client.local.EventsFactory;
import com.ocpsoft.socialpm.gwt.client.local.ServiceFactory;
import com.ocpsoft.socialpm.gwt.client.local.view.component.HeroPanel;
import com.ocpsoft.socialpm.gwt.client.local.view.events.LoginEvent;
import com.ocpsoft.socialpm.model.user.Profile;

@ApplicationScoped
public class ProfileViewImpl extends FixedLayoutView implements ProfileView
{
   HeroPanel greeting = new HeroPanel();
   private Presenter presenter;

   @Inject
   public ProfileViewImpl(ServiceFactory serviceFactory, EventsFactory eventFactory)
   {
      super(serviceFactory, eventFactory);
      System.out.println("Construct ViewProfileView");
      content.add(greeting);
   }

   public void onLogin(@Observes LoginEvent event)
   {
      if (presenter != null)
         presenter.handleLogin(event);
   }

   @Override
   public void setPresenter(ProfileView.Presenter presenter)
   {
      this.presenter = presenter;
   }

   @Override
   public void setProfile(final Profile profile)
   {
      greeting.setHeading("This is " + profile.getUsername() + "!");
      greeting.setContent(profile.getBio());
      greeting.getUnder().add(new Label(profile.getEmail()));
   }

   /*
    * Getters & Setters
    */
   @Override
   public HeroPanel getGreeting()
   {
      return greeting;
   }
}
