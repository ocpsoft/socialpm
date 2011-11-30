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
import java.util.ArrayList;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.jboss.seam.international.status.Messages;

import com.ocpsoft.socialpm.cdi.Web;
import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.domain.project.Velocity;
import com.ocpsoft.socialpm.domain.project.iteration.Iteration;
import com.ocpsoft.socialpm.domain.project.iteration.IterationStatistics;
import com.ocpsoft.socialpm.domain.project.stories.Story;
import com.ocpsoft.socialpm.domain.project.stories.Task;
import com.ocpsoft.socialpm.domain.user.Profile;
import com.ocpsoft.socialpm.model.project.IterationService;
import com.ocpsoft.socialpm.web.ParamsBean;

/**
 * @author <a href="mailto:bleathem@gmail.com">Brian Leathem</a>
 * 
 */
@Named
@ConversationScoped
public class Iterations implements Serializable
{
   private static final long serialVersionUID = -6828711689148386870L;

   private Messages messages;

   private Projects projects;

   private IterationService is;

   private ParamsBean params;

   public Iterations()
   {}

   @Inject
   public Iterations(final @Web EntityManager em, final IterationService is, final Projects projects,
            final Messages messages,
            final ParamsBean params)
   {
      this.params = params;
      this.messages = messages;
      this.projects = projects;
      this.is = is;
      this.is.setEntityManager(em);
   }

   private Iteration current = new Iteration();

   public String loadCurrent()
   {
      Project project = projects.getCurrent();

      if (project.isPersistent())
      {
         if ((current != null) && (params.getIterationNumber() != 0))
         {
            try {
               current = is.findByProjectAndNumber(project, params.getIterationNumber());
            }
            catch (NoResultException e) {
               messages.error("Oops! We couldn't find that Iteration.");
               return "/pages/project/view?faces-redirect=true&profile=" + project.getOwner().getUsername()
                        + "&project=" + project.getSlug();
            }
         }
      }
      return null;
   }

   public int getAssignedTaskCount(final Profile p, final Iteration i)
   {
      return getAssignedTasks(p, i).size();
   }

   public List<Task> getAssignedTasks(final Profile p, final Iteration i)
   {
      List<Task> result = new ArrayList<Task>();
      // FIXME Super slow - query-ify this!
      for (Story s : i.getStories()) {
         for (Task t : s.getTasks()) {
            if (p.getUsername().equals(t.getAssignee().getUsername()))
               result.add(t);
         }
      }
      return result;
   }

   @TransactionAttribute
   public String create()
   {
      Iteration created = is.create(projects.getCurrent(), current);
      return "/pages/iteration/sorter?faces-redirect=true&project=" + projects.getCurrent().getSlug() + "&profile="
               + current.getProject().getOwner().getUsername()
               + "&iteration=" + is.getIterationNumber(created);
   }

   @TransactionAttribute
   public void saveAjax()
   {
      is.save(current);
   }

   @Produces
   @Named("iteration")
   @RequestScoped
   public Iteration getCurrent()
   {
      return current;
   }

   public void setCurrent(final Iteration current)
   {
      this.current = current;
   }

   /* Velocity Visualization */
   public int getVelocityPercentage()
   {
      return 50;
   }

   public int getAllocationPercentage()
   {
      IterationStatistics commitmentStats = current.getCommitmentStats();
      Project project = projects.getCurrent();
      Velocity velocity = project.getVelocity();
      double allocated = commitmentStats.getHoursRemain() == 0 ? 1 : commitmentStats.getHoursRemain();
      double velocityHours = velocity.getHours();
      double result = (allocated / velocityHours) * 100;
      System.out.println(result);
      return (int) ((result / 100) * getVelocityPercentage());
   }
}
