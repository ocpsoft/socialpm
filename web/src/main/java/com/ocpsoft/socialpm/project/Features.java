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
import com.ocpsoft.socialpm.model.project.Feature;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.project.story.Story;
import com.ocpsoft.socialpm.model.project.story.Task;
import com.ocpsoft.socialpm.model.user.Profile;
import com.ocpsoft.socialpm.services.project.FeatureService;
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
