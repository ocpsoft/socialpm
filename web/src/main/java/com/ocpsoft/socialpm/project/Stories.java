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
import java.util.ArrayList;
import java.util.Arrays;
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

import com.ocpsoft.rewrite.servlet.config.Forward;
import com.ocpsoft.socialpm.cdi.Current;
import com.ocpsoft.socialpm.cdi.Web;
import com.ocpsoft.socialpm.model.project.Points;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.project.story.Story;
import com.ocpsoft.socialpm.model.project.story.Task;
import com.ocpsoft.socialpm.model.user.Profile;
import com.ocpsoft.socialpm.security.Profiles;
import com.ocpsoft.socialpm.services.project.IterationService;
import com.ocpsoft.socialpm.services.project.StoryService;
import com.ocpsoft.socialpm.web.ParamsBean;

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

   private IterationService is;

   private ParamsBean params;

   private Profiles profiles;

   public Stories()
   {}

   @Inject
   public Stories(final @Web EntityManager em, final Profiles profiles, final StoryService ss, final Projects projects,
            final Messages messages,
            final IterationService is,
            final ParamsBean params)
   {
      this.params = params;
      this.messages = messages;
      this.profiles = profiles;
      this.projects = projects;
      this.ss = ss;
      this.ss.setEntityManager(em);
      this.is = is;
      this.is.setEntityManager(em);
   }

   @Produces
   @Current
   @Named("points")
   public List<Points> getPointsList()
   {
      return Arrays.asList(Points.values());
   }

   private Story current = new Story();

   public Object loadCurrent()
   {
      Project project = projects.getCurrent();

      if (project.isPersistent())
      {
         if (params.getStoryNumber() != 0)
         {
            try {
               current = ss.findByProjectAndNumber(project, params.getStoryNumber());
            }
            catch (NoResultException e) {
               return Forward.to("/404");
            }
         }
      }
      return null;
   }

   public int getAssignedTaskCount(final Profile p, final Story s)
   {
      return getAssignedTasks(p, s).size();
   }

   public List<Task> getAssignedTasks(final Profile p, final Story s)
   {
      List<Task> result = new ArrayList<Task>();
      for (Task t : s.getTasks()) {
         if (p.getUsername().equals(t.getAssignee().getUsername()))
            result.add(t);
      }
      return result;
   }

   @TransactionAttribute
   public String create()
   {
      Story created = ss.create(projects.getCurrent(), current);
      return "/pages/story/view?faces-redirect=true&project=" + projects.getCurrent().getSlug() + "&profile="
               + current.getProject().getOwner().getUsername()
               + "&story=" + ss.getStoryNumber(created);
   }

   @TransactionAttribute
   public void saveAjax()
   {
      ss.save(current);
   }

   @TransactionAttribute
   public void saveAjax(final Story s)
   {
      ss.save(s);
   }

   @TransactionAttribute
   public void saveIterationAjax()
   {
      is.changeStoryIteration(current.getIteration(), current);
   }

   @TransactionAttribute
   public void closeStoryAjax()
   {
      ss.closeStory(current, profiles.getCurrent());
   }

   @TransactionAttribute
   public void openStoryAjax()
   {
      ss.openStory(current, profiles.getCurrent());
   }

   @Produces
   @Current
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
