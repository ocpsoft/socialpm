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
package com.ocpsoft.socialpm.project;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.ocpsoft.socialpm.cdi.Web;
import com.ocpsoft.socialpm.domain.project.stories.Status;
import com.ocpsoft.socialpm.domain.project.stories.Task;
import com.ocpsoft.socialpm.domain.user.Profile;
import com.ocpsoft.socialpm.model.ProfileService;
import com.ocpsoft.socialpm.model.project.TaskService;

/**
 * @author <a href="mailto:bleathem@gmail.com">Brian Leathem</a>
 * 
 */
@Named
@ConversationScoped
public class Tasks implements Serializable
{
   private static final long serialVersionUID = -6828711689148386870L;

   private Stories stories;

   private TaskService ts;

   private ProfileService prs;

   public Tasks()
   {}

   @Inject
   public Tasks(final @Web EntityManager em, final TaskService ts, final ProfileService prs, final Stories stories)
   {
      this.stories = stories;
      this.prs = prs;
      this.ts = ts;
      this.prs.setEntityManager(em);
      this.ts.setEntityManager(em);
   }

   @Produces
   @Named("assignees")
   public List<Profile> getProjectMembers()
   {
      return prs.getProfiles(0, 0);
   }

   @TransactionAttribute
   public void createAjax()
   {
      Task created = ts.create(stories.getCurrent(), current);
      current = new Task();
      current.setAssignee(created.getAssignee());
   }

   @TransactionAttribute
   public void clearImpediments(final Task t)
   {
      t.clearImpediments();
      ts.save(t);
   }

   private Task current = new Task();

   @Produces
   @Named("task")
   @RequestScoped
   public Task getCurrent()
   {
      return current;
   }

   @Produces
   @Named("taskStatuses")
   @RequestScoped
   public List<Status> getStatuses()
   {
      return Arrays.asList(Status.values());
   }

   public void setCurrent(final Task current)
   {
      this.current = current;
   }
}
