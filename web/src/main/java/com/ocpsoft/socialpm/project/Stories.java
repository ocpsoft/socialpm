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

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.international.status.Messages;

import com.ocpsoft.socialpm.cdi.Web;
import com.ocpsoft.socialpm.domain.project.Points;
import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.domain.project.stories.Story;
import com.ocpsoft.socialpm.model.project.StoryService;
import com.ocpsoft.socialpm.web.ParamsBean;
import com.ocpsoft.socialpm.web.constants.UrlConstants;

/**
 * @author <a href="mailto:bleathem@gmail.com">Brian Leathem</a>
 * 
 */
@Named
@ConversationScoped
public class Stories implements Serializable
{
   private static final long serialVersionUID = -6828711689148386870L;

   private Messages messages;

   private Projects projects;

   private StoryService ss;

   private ParamsBean params;

   public Stories()
   {}

   @Inject
   public Stories(final @Web EntityManager em, final StoryService ss, final Projects projects, final Messages messages,
            final ParamsBean params)
   {
      this.params = params;
      this.messages = messages;
      this.projects = projects;
      this.ss = ss;
      this.ss.setEntityManager(em);
   }

   @Produces
   @Named("points")
   public List<Points> getPointsList()
   {
      return Arrays.asList(Points.values());
   }

   private Story current = new Story();

   public String loadCurrent()
   {
      Project project = projects.getCurrent();

      if (project.isPersistent())
      {
         if ((current != null) && (params.getStoryNumber() != 0))
         {
            current = ss.findByProjectAndNumber(project, params.getStoryNumber());
            return null;
         }
         else
         {
            messages.error("Oops! We couldn't find that Story.");
            // TODO need a better navigation system
            return "/pages/project/view?faces-redirect=true&profile=" + project.getOwner().getUsername()
                     + "&project=" + project.getSlug();
         }
      }
      return null;
   }

   public String create()
   {
      ss.create(projects.getCurrent(), current);
      return UrlConstants.PROJECT_VIEW + "&project=" + projects.getCurrent().getSlug() + "&profile="
               + current.getProject().getOwner().getUsername()
               + "&project=" + current.getProject().getSlug()
               + "&story=" + current.getNumber();
   }

   public void saveAjax()
   {
      ss.save(current);
   }

   @Produces
   @Named("story")
   @RequestScoped
   public Story getCurrent()
   {
      return current;
   }

   public void setCurrent(final Story current)
   {
      this.current = current;
   }
}
