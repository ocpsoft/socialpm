package com.ocpsoft.socialpm.gwt.client.local.view.story.create;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.errai.bus.client.api.RemoteCallback;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.ocpsoft.socialpm.gwt.client.local.ClientFactory;
import com.ocpsoft.socialpm.gwt.client.local.history.CurrentHistory;
import com.ocpsoft.socialpm.gwt.client.local.view.story.view.StoryViewPlace;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.project.story.Story;
import com.ocpsoft.socialpm.model.user.Profile;

@Dependent
public class NewStoryActivity extends AbstractActivity implements NewStoryView.Presenter
{
   private final ClientFactory clientFactory;
   private final NewStoryView newStoryView;
   private final String username;
   private final String slug;

   @Inject
   public NewStoryActivity(ClientFactory clientFactory, NewStoryView newStoryView, @CurrentHistory Place place)
   {
      this.clientFactory = clientFactory;
      this.newStoryView = newStoryView;
      this.username = ((NewStoryPlace) place).getUsername();
      this.slug = ((NewStoryPlace) place).getSlug();
   }

   @Override
   public void start(final AcceptsOneWidget containerWidget, EventBus eventBus)
   {
      newStoryView.setPresenter(this);
      containerWidget.setWidget(newStoryView.asWidget());

      clientFactory.getServiceFactory().getProjectService().call(new RemoteCallback<Project>() {

         @Override
         public void callback(Project project)
         {
            newStoryView.setProject(project);
         }
      }).getByOwnerAndSlug(new Profile(username), slug);
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

   @Override
   public void createStory(Project project, Story story)
   {
      System.out.println("Clicked create story");

      clientFactory.getServiceFactory().getStoryService().call(new RemoteCallback<Story>() {

         @Override
         public void callback(Story story)
         {
            System.out.println(story);
            goTo(new StoryViewPlace(username, slug, story.getNumber()));
         }
      }).create(project, story);
   }

}