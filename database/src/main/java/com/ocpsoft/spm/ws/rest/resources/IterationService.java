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

package com.ocpsoft.spm.ws.rest.resources;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.ocpsoft.socialpm.domain.PersistenceUtil;
import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.domain.project.iteration.Iteration;
import com.ocpsoft.socialpm.domain.project.stories.Story;
import com.ocpsoft.socialpm.model.project.iteration.DailyStatsChain;
import com.ocpsoft.socialpm.util.Dates;

public class IterationService extends PersistenceUtil
{
   private static final long serialVersionUID = -3555718237042388593L;

   @Inject
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
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
         iteration.updateCommitmentStats();
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
      }

      // story.getIterationHistory().add(to);
      save(story);

      if (story.onFrontBurner())
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
      DailyStatsChain.getInstance().update(iteration);
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

}
