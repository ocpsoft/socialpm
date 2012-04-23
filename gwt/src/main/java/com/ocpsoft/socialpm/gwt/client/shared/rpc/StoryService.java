package com.ocpsoft.socialpm.gwt.client.shared.rpc;

import org.jboss.errai.bus.server.annotations.Remote;

import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.project.story.Story;
import com.ocpsoft.socialpm.model.project.story.Task;
import com.ocpsoft.socialpm.model.user.Profile;

@Remote
public interface StoryService
{
   Story create(Project project, Story story);

   Story getByOwnerSlugAndNumber(Profile owner, String slug, int storyNumber);

   Task createTask(Story story, Task task);

}
