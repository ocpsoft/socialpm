/*
 * Copyright 2011 <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ocpsoft.socialpm.faces.listener;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.ocpsoft.socialpm.cdi.Web;
import com.ocpsoft.socialpm.domain.project.stories.Story;
import com.ocpsoft.socialpm.domain.project.stories.Task;
import com.ocpsoft.socialpm.faces.listener.events.TaskCommand;
import com.ocpsoft.socialpm.model.project.StoryService;
import com.ocpsoft.socialpm.project.Stories;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
@Named
@RequestScoped
public class TaskCommandListener
{
   private StoryService ss;
   private final List<TaskCommand> commands = new ArrayList<TaskCommand>();
   private Story story;

   protected TaskCommandListener()
   {}

   @Inject
   public TaskCommandListener(@Web EntityManager em, StoryService ss,
            Stories stories)
   {
      ss.setEntityManager(em);
      this.ss = ss;
      this.story = stories.getCurrent();
   }

   public void capture(@Observes TaskCommand command)
   {
      this.commands.add(command);
   }

   public void save(Task t)
   {
      for (TaskCommand c : commands) {
         c.perform(t);
      }
      ss.save(story);
   }

}
