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

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.inject.Inject;
import javax.inject.Named;

import com.ocpsoft.socialpm.domain.project.stories.Task;
import com.ocpsoft.socialpm.domain.user.Profile;
import com.ocpsoft.socialpm.faces.listener.events.TaskCommand;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
@RequestScoped
@Named("taskAssigneeChanged")
public class TaskAssigneeChanged implements ValueChangeListener
{
   @Inject
   private Event<TaskCommand> changeEvent;

   @Override
   public void processValueChange(final ValueChangeEvent event) throws AbortProcessingException
   {
      TaskCommand command = new TaskCommand() {

         private final Profile newVal = (Profile) event.getNewValue();

         @Override
         public void perform(Task task)
         {
            task.setAssignee(newVal);
         }
      };

      changeEvent.fire(command);
   }
}
