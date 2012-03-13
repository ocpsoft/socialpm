package com.ocpsoft.socialpm.gwt.client.local.activity;

import org.jboss.errai.bus.client.api.ErrorCallback;
import org.jboss.errai.bus.client.api.Message;
import org.jboss.errai.bus.client.api.RemoteCallback;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.ocpsoft.socialpm.gwt.client.local.ClientFactory;
import com.ocpsoft.socialpm.gwt.client.local.EventsFactory;
import com.ocpsoft.socialpm.gwt.client.local.ServiceFactory;
import com.ocpsoft.socialpm.gwt.client.local.places.HomePlace;
import com.ocpsoft.socialpm.gwt.client.local.places.LoginPlace;
import com.ocpsoft.socialpm.gwt.client.local.view.LoginView;
import com.ocpsoft.socialpm.gwt.client.local.view.events.LoginEvent;
import com.ocpsoft.socialpm.gwt.client.local.view.events.LogoutEvent;
import com.ocpsoft.socialpm.model.user.Profile;

public class LoginActivity extends AbstractActivity implements LoginView.Presenter
{
   private final ClientFactory clientFactory;

   public LoginActivity(LoginPlace place, ClientFactory clientFactory)
   {
      this.clientFactory = clientFactory;
   }

   @Override
   public void start(AcceptsOneWidget containerWidget, EventBus eventBus)
   {
      // TODO handle this redirect with Rewrite utility?
      // Redirect.temporary(HistoryConstants.HOME());

      LoginView loginView = clientFactory.getLoginView();
      loginView.setPresenter(this);

      containerWidget.setWidget(loginView.asWidget());
      loginView.focusUsername();
   }

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
}