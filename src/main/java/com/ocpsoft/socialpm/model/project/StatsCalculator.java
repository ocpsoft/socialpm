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

package com.ocpsoft.socialpm.model.project;

import com.ocpsoft.socialpm.domain.project.iteration.Iteration;
import com.ocpsoft.socialpm.domain.project.iteration.IterationStatistics;
import com.ocpsoft.socialpm.domain.project.stories.Story;
import com.ocpsoft.socialpm.domain.project.stories.StoryStatus;
import com.ocpsoft.socialpm.domain.project.stories.Task;
import com.ocpsoft.socialpm.domain.project.stories.TaskStatus;

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
      int totalHours = 0;
      int totalPoints = 0;
      int totalTasks = 0;
      int totalValue = 0;

      for (Story story : iteration.getStories())
      {
         if (StoryStatus.CLOSED.equals(story.getStatus()))
         {
            completedStories++;
            completedPoints += story.getStoryPoints().getValue();
            completedValue += story.getBusinessValue().getValue();
         }

         for (Task t : story.getTasks())
         {
            if (TaskStatus.DONE.equals(t.getStatus()))
            {
               completedTasks++;
            }
            totalTasks++;
         }

         totalHours += story.getTotalTaskHoursRemaining();
         totalPoints += story.getStoryPoints().getValue();
         totalValue += story.getBusinessValue().getValue();
      }

      ns.setCompletedPoints(completedPoints);
      ns.setCompletedStories(completedStories);
      ns.setCompletedTasks(completedTasks);
      ns.setCompletedValue(completedValue);

      ns.setTotalHours(totalHours);
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

      stat.setTotalHours(ns.getTotalHours());
      stat.setTotalPoints(ns.getTotalPoints());
      stat.setTotalStories(ns.getTotalStories());
      stat.setTotalTasks(ns.getTotalTasks());
      stat.setTotalValue(ns.getTotalValue());
   }
}
