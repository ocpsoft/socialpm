package com.ocpsoft.socialpm.gwt.client.local.activity;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.errai.bus.client.api.RemoteCallback;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.ocpsoft.socialpm.gwt.client.local.ClientFactory;
import com.ocpsoft.socialpm.gwt.client.local.view.LogoutView;

@Dependent
public class LogoutActivity extends AbstractActivity implements LogoutView.Presenter
{
   private final ClientFactory clientFactory;

   @Inject
   public LogoutActivity(ClientFactory clientFactory)
   {
      this.clientFactory = clientFactory;
   }

   @Override
   public void start(AcceptsOneWidget containerWidget, EventBus eventBus)
   {
      LogoutView logoutView = clientFactory.getLogoutView();
      logoutView.setPresenter(this);

      containerWidget.setWidget(logoutView.asWidget());

      clientFactory.getServiceFactory().getAuthService().call(new RemoteCallback<Void>() {
         @Override
         public void callback(Void response)
         {
         }
      }).logout();

      clientFactory.getEventFactory().fireLogoutEvent();
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