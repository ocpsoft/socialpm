package com.ocpsoft.socialpm.gwt.client.local.view;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.ocpsoft.socialpm.gwt.client.local.EventsFactory;
import com.ocpsoft.socialpm.gwt.client.local.ServiceFactory;

@ApplicationScoped
public class LogoutViewImpl extends FixedLayoutView implements LogoutView
{
   @Inject
   private ServiceFactory serviceFactory;

   @Inject
   private EventsFactory eventFactory;

   private Presenter presenter;

   public LogoutViewImpl()
   {
      super();
   }

   @Override
   public void setup()
   {
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
