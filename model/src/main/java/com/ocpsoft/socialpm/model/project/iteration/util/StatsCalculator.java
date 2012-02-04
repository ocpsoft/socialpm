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

package com.ocpsoft.socialpm.model.project.iteration.util;

import com.ocpsoft.socialpm.model.project.iteration.Iteration;
import com.ocpsoft.socialpm.model.project.iteration.IterationStatistics;
import com.ocpsoft.socialpm.model.project.story.Status;
import com.ocpsoft.socialpm.model.project.story.Story;
import com.ocpsoft.socialpm.model.project.story.Task;

public class StatsCalculator
{
   public IterationStatistics calculate(final Iteration iteration)
   {
      IterationStatistics ns = new IterationStatistics();
      ns.setIteration(iteration);

      int completedStories = 0;
      int completedPoints = 0;
      int completedTasks = 0;
      int completedValue = 0;
      int hoursRemain = 0;
      int totalPoints = 0;
      int totalTasks = 0;
      int totalValue = 0;

      for (Story story : iteration.getStories())
      {
         if (!story.isOpen())
         {
            completedStories++;
            completedPoints += story.getStoryPoints().getValue();
            completedValue += story.getBusinessValue().getValue();
         }

         for (Task t : story.getTasks())
         {
            if (Status.DONE.equals(t.getStatus()))
            {
               completedTasks++;
            }
            totalTasks++;
         }

         hoursRemain += story.getTotalTaskHoursRemaining();
         totalPoints += story.getStoryPoints().getValue();
         totalValue += story.getBusinessValue().getValue();
      }

      ns.setCompletedPoints(completedPoints);
      ns.setCompletedStories(completedStories);
      ns.setCompletedTasks(completedTasks);
      ns.setCompletedValue(completedValue);

      ns.setHoursRemain(hoursRemain);
      ns.setTotalPoints(totalPoints);
      ns.setTotalStories(iteration.getStories().size());
      ns.setTotalTasks(totalTasks);
      ns.setTotalValue(totalValue);
      return ns;
   }

   public void update(final Iteration iteration, final IterationStatistics stat)
   {
      IterationStatistics ns = calculate(iteration);

      stat.setCompletedPoints(ns.getCompletedPoints());
      stat.setCompletedStories(ns.getCompletedStories());
      stat.setCompletedTasks(ns.getCompletedTasks());
      stat.setCompletedValue(ns.getCompletedValue());

      stat.setHoursRemain(ns.getHoursRemain());
      stat.setTotalPoints(ns.getTotalPoints());
      stat.setTotalStories(ns.getTotalStories());
      stat.setTotalTasks(ns.getTotalTasks());
      stat.setTotalValue(ns.getTotalValue());
   }
}
