package com.ocpsoft.socialpm.gwt.client.local.activity;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.ocpsoft.socialpm.gwt.client.local.ClientFactory;
import com.ocpsoft.socialpm.gwt.client.local.view.NewProjectView;

@Dependent
public class NewProjectActivity extends AbstractActivity implements NewProjectView.Presenter
{
   private final ClientFactory clientFactory;
   private final NewProjectView newProjectView;

   @Inject
   public NewProjectActivity(ClientFactory clientFactory, NewProjectView newProjectView)
   {
      this.newProjectView = newProjectView;
      this.clientFactory = clientFactory;
   }

   @Override
   public void start(AcceptsOneWidget containerWidget, EventBus eventBus)
   {
      newProjectView.setPresenter(this);

      containerWidget.setWidget(newProjectView.asWidget());
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