package com.ocpsoft.socialpm.gwt.client.shared.rpc;

import org.jboss.errai.bus.server.annotations.Remote;

import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.project.story.Story;
import com.ocpsoft.socialpm.model.project.story.Task;
import com.ocpsoft.socialpm.model.project.story.ValidationCriteria;
import com.ocpsoft.socialpm.model.user.Profile;

@Remote
public interface StoryService
{
   Story create(Project project, Story story);

   Task createTask(Story story, Task task);

   ValidationCriteria createValidation(Story story, ValidationCriteria criteria);

   Story getByOwnerSlugAndNumber(Profile owner, String slug, int storyNumber);
}
