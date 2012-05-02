package com.ocpsoft.socialpm.gwt.client.local.view.login;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.errai.bus.client.api.ErrorCallback;
import org.jboss.errai.bus.client.api.Message;
import org.jboss.errai.bus.client.api.RemoteCallback;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.ocpsoft.socialpm.gwt.client.local.App;
import com.ocpsoft.socialpm.gwt.client.local.ClientFactory;
import com.ocpsoft.socialpm.gwt.client.local.view.events.LoginEvent;
import com.ocpsoft.socialpm.gwt.client.local.view.events.LogoutEvent;
import com.ocpsoft.socialpm.gwt.client.local.view.home.HomePlace;
import com.ocpsoft.socialpm.model.user.Profile;

@Dependent
public class LoginActivity extends AbstractActivity implements LoginView.Presenter
{
   private final ClientFactory clientFactory;
   private final LoginView loginView;

   @Inject
   public LoginActivity(LoginView loginView, ClientFactory clientFactory)
   {
      this.loginView = loginView;
      this.clientFactory = clientFactory;
   }

   @Override
   public void start(AcceptsOneWidget containerWidget, EventBus eventBus)
   {
      if (App.getLoggedInProfile() != null)
      {
         goTo(new HomePlace());
      }
      else
      {
         loginView.setPresenter(this);

         containerWidget.setWidget(loginView.asWidget());
         loginView.focusUsername();
      }
   }

   @Override
   public void doLogin(String username, String password)
   {
      RemoteCallback<Profile> success = new RemoteCallback<Profile>() {

         @Override
         public void callback(Profile profile)
         {
            if (profile != null)
            {
               clientFactory.getEventFactory().fireLoginEvent(profile);
               goTo(new HomePlace());
            }
            else
            {
               Window.alert("WRONG! Try again...");
            }
         }

      };
      ErrorCallback failure = new ErrorCallback() {

         @Override
         public boolean error(Message message, Throwable throwable)
         {
            return false;
         }
      };

      clientFactory.getServiceFactory().getAuthService().call(success, failure).login(username, password);
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

   public void handleLogin(@Observes LoginEvent event)
   {
      loginView.clearForm();
   }

   public void handleLogout(@Observes LogoutEvent event)
   {}
}