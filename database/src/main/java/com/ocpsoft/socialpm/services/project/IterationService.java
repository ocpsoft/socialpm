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

package com.ocpsoft.socialpm.services.project;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.project.iteration.Iteration;
import com.ocpsoft.socialpm.model.project.story.Story;
import com.ocpsoft.socialpm.services.PersistenceUtil;
import com.ocpsoft.socialpm.util.Dates;

@TransactionAttribute
public class IterationService extends PersistenceUtil
{
   private static final long serialVersionUID = -3555718237042388593L;

   @Override
   public void setEntityManager(final EntityManager em)
   {
      this.em = em;
   }

   /**
    * Commit the specified iteration
    * 
    * @throws IllegalStateException if the iteration is already committed, IllegalArgumentException if the iteration
    *            hasn't started, or the iteration has finished, or the iteration is Default past
    */
   public void commit(final Iteration iteration) throws IllegalStateException, IllegalArgumentException
   {
      Date today = Dates.now();

      if (iteration.isCommitted())
      {
         throw new IllegalStateException("Iteration: " + iteration.getTitle() + " has already been committed");
      }
      else if (!iteration.isDefault()
               && Dates.isInPrecisionRange(iteration.getStartDate(), iteration.getEndDate(), today, Calendar.DATE))
      {
         iteration.setCommittedOn(Dates.now());
         save(iteration);
      }
      else
      {
         String errorMessage = "Commit is not allowed on Iteration: " + iteration.getTitle();

         if (iteration.isDefault())
         {
            errorMessage += " because it is the Default.";
         }
         else if (iteration.isEnded())
         {
            errorMessage += " because the iteration has completed.";
         }
         else if (!iteration.isEnded())
         {
            errorMessage += " because the iteration has not started yet.";
         }

         throw new IllegalArgumentException(errorMessage);
      }
   }

   /**
    * Move a story from one Iteration to another. If from is a future iteration remove this Story's relationship with
    * From. If From is the Default Iteration, its relationship is always removed.
    * 
    * @throws IllegalStateException if To has ended, IllegalArgumentException if to is in the past
    */
   public void changeStoryIteration(final Iteration to, final Story story) throws IllegalStateException,
            IllegalArgumentException
   {
      Iteration from = story.getIteration();
      if (from.equals(to))
      {
         return;
      }

      if (to.isEnded())
      {
         throw new IllegalArgumentException("Cannot change iteration history: Iteration [" + to + "] is in the past");
      }

      if (!from.isEnded())
      {
         from.getStories().remove(story);
         // story.getIterationHistory().remove(from);
         // TODO re-implement iteration history
      }

      // story.getIterationHistory().add(to);
      save(story);

      if (story.isFrontBurner())
      {
         if (!from.isEnded())
         {
            updateIterationStatistics(from);
         }

         updateIterationStatistics(to);
      }

      return;
   }

   /**
    * @throws IllegalArgumentException if Iteration has ended
    */
   public void updateIterationStatistics(final Iteration iteration)
            throws IllegalStateException
   {
      save(iteration);
   }

   public void removeIteration(final Iteration iteration)
   {
      Project project = iteration.getProject();

      Set<Story> stories = iteration.getStories();
      for (Story s : stories)
      {
         changeStoryIteration(project.getDefaultIteration(), s);
      }

      iteration.getProject().getIterations().remove(iteration);
      delete(iteration);
   }

   public Iteration findByProjectAndNumber(final Project project, final int iterationNumber)
   {
      return findUniqueByNamedQuery("Iteration.byProjectAndNumber", project, iterationNumber);
   }

   public int getIterationNumber(final Iteration created)
   {
      Query query = em.createNativeQuery(
               "(SELECT count(i.id) + 1 FROM iterations i WHERE i.project_id = :pid AND i.id < :iid)");
      query.setParameter("pid", created.getProject().getId());
      query.setParameter("iid", created.getId());
      return ((BigInteger) query.getSingleResult()).intValue();
   }

   public Iteration create(final Project p, final Iteration i)
   {
      i.setProject(p);
      p.getIterations().add(i);

      super.create(i);
      return i;
   }

   public void save(final Iteration i)
   {
      super.save(i);
   }

   public Iteration findById(final Long id)
   {
      return findById(Iteration.class, id);
   }

}
