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
import com.ocpsoft.socialpm.domain.project.Feature;
import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.domain.project.stories.Story;
import com.ocpsoft.socialpm.domain.project.stories.Task;
import com.ocpsoft.socialpm.domain.user.Profile;
import com.ocpsoft.socialpm.model.project.FeatureService;
import com.ocpsoft.socialpm.web.ParamsBean;

/**
 * @author <a href="mailto:bleathem@gmail.com">Brian Leathem</a>
 * 
 */
@Named
@ConversationScoped
public class Features implements Serializable
{
   private static final long serialVersionUID = -6828711689148386870L;

   private Messages messages;

   private Projects projects;

   private FeatureService fs;

   private ParamsBean params;

   public Features()
   {}

   @Inject
   public Features(final @Web EntityManager em, final FeatureService fs, final Projects projects,
            final Messages messages,
            final ParamsBean params)
   {
      this.params = params;
      this.messages = messages;
      this.projects = projects;
      this.fs = fs;
      this.fs.setEntityManager(em);
   }

   private Feature current = new Feature();

   public String loadCurrent()
   {
      Project project = projects.getCurrent();

      if (project.isPersistent())
      {
         if ((current != null) && (params.getFeatureNumber() != 0))
         {
            try {
               current = fs.findByProjectAndNumber(project, params.getFeatureNumber());
            }
            catch (NoResultException e) {
               messages.error("Oops! We couldn't find that Feature.");
               return "/pages/project/view?faces-redirect=true&profile=" + project.getOwner().getUsername()
                        + "&project=" + project.getSlug();
            }
         }
      }
      return null;
   }

   public int getAssignedTaskCount(final Profile p, final Feature f)
   {
      return getAssignedTasks(p, f).size();
   }

   public List<Task> getAssignedTasks(final Profile p, final Feature f)
   {
      List<Task> result = new ArrayList<Task>();
      // FIXME Super slow - query-ify this!
      for (Story s : f.getStories()) {
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
      Feature created = fs.create(projects.getCurrent(), current);
      return "/pages/feature/view?faces-redirect=true&project=" + projects.getCurrent().getSlug() + "&profile="
               + current.getProject().getOwner().getUsername()
               + "&feature=" + fs.getFeatureNumber(created);
   }

   @TransactionAttribute
   public void saveAjax()
   {
      fs.save(current);
   }

   @Produces
   @Named("feature")
   @RequestScoped
   public Feature getCurrent()
   {
      return current;
   }

   public void setCurrent(final Feature current)
   {
      this.current = current;
   }
}
