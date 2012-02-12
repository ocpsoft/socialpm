package com.ocpsoft.socialpm.gwt.client.local.view;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.errai.bus.client.api.ErrorCallback;
import org.jboss.errai.bus.client.api.Message;
import org.jboss.errai.bus.client.api.RemoteCallback;
import org.jboss.errai.ioc.client.api.Caller;

import com.google.gwt.user.client.ui.Label;
import com.ocpsoft.socialpm.gwt.client.local.view.component.HeroPanel;
import com.ocpsoft.socialpm.gwt.client.shared.rpc.AuthenticationService;
import com.ocpsoft.socialpm.gwt.client.shared.rpc.ProfileService;
import com.ocpsoft.socialpm.model.user.Profile;

@ApplicationScoped
public class ViewProfileView extends FixedLayoutView
{
   HeroPanel greeting = new HeroPanel();
   private final Caller<ProfileService> profileService;

   @Inject
   public ViewProfileView(Caller<ProfileService> profileService, Caller<AuthenticationService> loginService)
   {
      super(loginService);
      System.out.println("Construct ViewProfileView");

      this.profileService = profileService;
      content.add(greeting);
   }

   public void setUsername(final String username)
   {
      profileService.call(new RemoteCallback<Profile>() {

         @Override
         public void callback(Profile response)
         {
            greeting.setHeading("This is " + username + "!");
            greeting.setContent(response.getBio());
            greeting.getUnder().add(new Label(response.getEmail()));
         }

      }, new ErrorCallback() {

         @Override
         public boolean error(Message message, Throwable throwable)
         {
            System.out.println("Failed to fetch profile: " + username);
            return false;
         }
      });
   }

   /*
    * Getters & Setters
    */
   public HeroPanel getGreeting()
   {
      return greeting;
   }
}
