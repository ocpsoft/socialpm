/**
 * This file is part of SocialPM: Agile Project Management Tools (SocialPM) 
 *
 * Copyright (c)2010 Lincoln Baxter, III <lincoln@ocpsoft.com> (OcpSoft)
 * 
 * If you are developing and distributing open source applications under 
 * the GPL Licence, then you are free to use SocialPM under the GPL 
 * License:
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
 * For OEMs, ISVs, and VARs who distribute SocialPM with their products, 
 * host their product online, OcpSoft provides flexible OEM commercial 
 * Licences. 
 * 
 * Optionally, customers may choose a Commercial License. For additional 
 * details, contact OcpSoft (http://ocpsoft.com)
 */

package com.ocpsoft.socialpm.pages.project.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import com.ocpsoft.socialpm.constants.UrlConstants;
import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.domain.project.iteration.Iteration;
import com.ocpsoft.socialpm.domain.project.iteration.IterationDateComparator;
import com.ocpsoft.socialpm.pages.PageBean;
import com.ocpsoft.util.Dates;

@Named
@RequestScoped
public class ManageIterationsBean extends PageBean
{
   private static final long serialVersionUID = 4112350219007426053L;

   private Iteration newIteration = new Iteration();

   private Project project;

   private List<Iteration> iterations = new ArrayList<Iteration>();

   public void load()
   {
      project = currentProjectBean.getProject();
      iterations = new ArrayList<Iteration>(project.getIterations());
      Collections.sort(iterations, new IterationDateComparator());
   }

   /*
    * Action methods
    */
   public String create()
   {
      try
      {
         if (isValidDateRange())
         {
            ps.addIteration(project, newIteration);
         }
      }
      catch (PersistenceException e)
      {
         facesUtils.addErrorMessage("Error adding iteration.");
         return facesUtils.beautify(UrlConstants.REFRESH);
      }

      return facesUtils.beautify(UrlConstants.REFRESH);
   }

   private boolean isValidDateRange()
   {
      List<Iteration> allIterations = currentProjectBean.getProject().getIterations();
      if (!newIteration.getStartDate().before(newIteration.getEndDate()))
      {
         facesUtils.addErrorMessage("Begin date must precede end date.");
         return false;
      }

      for (Iteration iteration : allIterations)
      {
         Date start = iteration.getStartDate();
         Date end = iteration.getEndDate();

         if ((start != null) && (end != null))
         {
            if (Dates.anyInRange(start, end, newIteration.getStartDate(), newIteration.getEndDate()))
            {
               facesUtils.addErrorMessage("Iteration already exists between the dates specified.");
               return false;
            }
         }
      }

      return true;
   }

   public String remove(final Iteration iteration)
   {
      project.getIterations().remove(iteration);
      ps.removeIteration(project.getId(), iteration.getId());
      return facesUtils.beautify(UrlConstants.REFRESH);
   }

   /*
    * Getters and setters
    */
   public Project getProject()
   {
      return project;
   }

   public void setProject(final Project project)
   {
      this.project = project;
   }

   public List<Iteration> getIterations()
   {
      return iterations;
   }

   public void setIterations(final List<Iteration> iterations)
   {
      this.iterations = iterations;
   }

   public Iteration getNewIteration()
   {
      return newIteration;
   }

   public void setNewIteration(final Iteration newIteration)
   {
      this.newIteration = newIteration;
   }
}