/**
 * This file is part of OCPsoft SocialPM: Agile Project Management Tools (SocialPM) 
 *
 * Copyright (c)2011 Lincoln Baxter, III <lincoln@ocpsoft.com> (OCPsoft)
 * Copyright (c)2011 OCPsoft.com (http://ocpsoft.com)
 * 
 * If you are developing and distributing open source applications under 
 * the GNU General Public License (GPL), then you are free to re-distribute SocialPM 
 * under the terms of the GPL, as follows:
 *
 * SocialPM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SocialPM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SocialPM.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * For individuals or entities who wish to use SocialPM privately, or
 * internally, the following terms do not apply:
 *  
 * For OEMs, ISVs, and VARs who wish to distribute SocialPM with their 
 * products, or host their product online, OCPsoft provides flexible 
 * OEM commercial licenses.
 * 
 * Optionally, Customers may choose a Commercial License. For additional 
 * details, contact an OCPsoft representative (sales@ocpsoft.com)
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

import com.ocpsoft.socialpm.cdi.Current;
import com.ocpsoft.socialpm.cdi.Web;
import com.ocpsoft.socialpm.model.project.story.Status;
import com.ocpsoft.socialpm.model.project.story.Task;
import com.ocpsoft.socialpm.model.user.Profile;
import com.ocpsoft.socialpm.services.ProfileService;
import com.ocpsoft.socialpm.services.project.TaskService;

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
   @Current
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
   @Current
   @Named("task")
   @RequestScoped
   public Task getCurrent()
   {
      return current;
   }

   public void setCurrent(final Task current)
   {
      this.current = current;
   }

   @Produces
   @Current
   @Named("taskStatuses")
   @RequestScoped
   public List<Status> getStatuses()
   {
      return Arrays.asList(Status.values());
   }
}
