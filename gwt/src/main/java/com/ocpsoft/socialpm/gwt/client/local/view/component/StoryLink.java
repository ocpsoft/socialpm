package com.ocpsoft.socialpm.gwt.client.local.view.component;

import com.ocpsoft.socialpm.gwt.client.local.view.story.view.StoryViewPlace;
import com.ocpsoft.socialpm.model.project.story.Story;

public class StoryLink extends NavLink
{
   public StoryLink()
   {}

   public StoryLink(Story story)
   {
      super("Story " + story.getNumber());
      setStory(story);
   }

   public StoryLink setStory(Story story)
   {
      setTargetHistoryToken(new StoryViewPlace.Tokenizer().getToken(new StoryViewPlace(story.getProject().getOwner()
               .getUsername(),
               story.getProject().getSlug(), story.getNumber())));
      setText("Story " + story.getNumber());
      return this;
   }
}
