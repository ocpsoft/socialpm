/**
 * This file is part of OCPsoft SocialPM: Agile Project Management Tools (SocialPM) 
 *
 * Copyright (c)2011 Lincoln Baxter, III <lincoln@ocpsoft.com> (OCPsoft)
 * Copyright (c)2011 OCPsoft.com (http://ocpsoft.com)
 * 
 * If you are developing and distributing open source applications under 
 * the GPL License, then you are free to re-distribute SocialPM under the
 * terms of the GPL License:
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
 * For OEMs, ISVs, and VARs who wish to distribute SocialPM with their 
 * products, or host their product online, OCPsoft provides flexible 
 * OEM commercial licenses. 
 * 
 * Optionally, customers may choose a Commercial License. For additional 
 * details, contact an OCPsoft representative (sales@ocpsoft.com)
 */

package com.ocpsoft.socialpm.model.project.iteration;

import com.ocpsoft.socialpm.domain.project.iteration.Iteration;
import com.ocpsoft.socialpm.domain.project.iteration.IterationStatistics;
import com.ocpsoft.socialpm.util.Dates;

public class DefaultIteration implements DailyStatsUpdater
{
   public static DefaultIteration getInstance()
   {
      return new DefaultIteration();
   }

   @Override
   public boolean shouldUpdate(final Iteration iteration)
   {
      if (iteration.isDefault())
      {
         return true;
      }
      return false;
   }

   @Override
   public void update(final Iteration iteration)
   {
      IterationStatistics stat;
      try
      {
         stat = iteration.getStatistics(Dates.now());
         new StatsCalculator().update(iteration, stat);
      }
      catch (IllegalArgumentException e)
      {
         stat = new StatsCalculator().calculate(iteration);
         stat.setDate(Dates.now());
         stat.setIteration(iteration);
         iteration.getStatistics().add(stat);
      }
   }
}
