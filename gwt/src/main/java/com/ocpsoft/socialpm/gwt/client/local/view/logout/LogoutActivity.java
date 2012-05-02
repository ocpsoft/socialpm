package com.ocpsoft.socialpm.gwt.client.local.view.logout;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.errai.bus.client.api.RemoteCallback;
import org.ocpsoft.rewrite.gwt.client.history.HistoryStateImpl;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.ocpsoft.socialpm.gwt.client.local.App;
import com.ocpsoft.socialpm.gwt.client.local.ClientFactory;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.gwt.client.local.util.Redirect;

@Dependent
public class LogoutActivity extends AbstractActivity
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
      clientFactory.getEventFactory().fireLogoutEvent();

      clientFactory.getServiceFactory().getAuthService().call(new RemoteCallback<Void>() {
         @Override
         public void callback(Void response)
         {
         }
      }).logout();

      String contextPath = HistoryStateImpl.getContextPath();
      String homeUrl = GWT.getModuleBaseURL();
      if (homeUrl.contains(contextPath))
      {
         homeUrl = homeUrl.substring(0, homeUrl.indexOf(contextPath));
      }
      homeUrl = homeUrl + HistoryConstants.HOME();
      if (App.isDevelopmentMode())
      {
         homeUrl = homeUrl + "?gwt.codesvr=127.0.0.1:9997";
      }
      Redirect.to(homeUrl);
   }

   @Override
   public String mayStop()
   {
      return null;
   }
}