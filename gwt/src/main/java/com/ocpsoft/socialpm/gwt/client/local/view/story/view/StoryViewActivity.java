package com.ocpsoft.socialpm.gwt.client.local.view.story.view;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.errai.bus.client.api.RemoteCallback;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.ocpsoft.socialpm.gwt.client.local.ClientFactory;
import com.ocpsoft.socialpm.gwt.client.local.history.CurrentHistory;
import com.ocpsoft.socialpm.model.project.story.Story;
import com.ocpsoft.socialpm.model.user.Profile;

@Dependent
public class StoryViewActivity extends AbstractActivity implements StoryView.Presenter
{
   private final ClientFactory clientFactory;
   private final StoryView storyView;

   private final String username;
   private final String slug;
   private final int storyNumber;

   @Inject
   public StoryViewActivity(ClientFactory clientFactory, StoryView storyView, @CurrentHistory Place place)
   {
      this.clientFactory = clientFactory;
      this.storyView = storyView;
      this.username = ((StoryViewPlace) place).getUsername();
      this.slug = ((StoryViewPlace) place).getSlug();
      this.storyNumber = ((StoryViewPlace) place).getStoryNumber();
   }

   @Override
   public void start(AcceptsOneWidget containerWidget, EventBus eventBus)
   {
      storyView.setPresenter(this);

      clientFactory.getServiceFactory().getStoryService().call(new RemoteCallback<Story>() {
         @Override
         public void callback(Story story)
         {
            storyView.setStory(story);
         }
      }).getByOwnerSlugAndNumber(new Profile(username), slug, storyNumber);

      containerWidget.setWidget(storyView.asWidget());
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