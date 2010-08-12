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

package com.ocpsoft.socialpm.pages.params;

import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.domain.project.iteration.Iteration;
import com.ocpsoft.socialpm.domain.project.stories.Story;

@Named
@Stateful
@RequestScoped
public class CurrentIterationBean extends PageAware
{
   private static final long serialVersionUID = -8311754020636258612L;

   private Iteration iteration;
   private List<Story> frontBurnerStories;

   @Inject
   private CurrentProjectBean currentProjectBean;

   public boolean isValued()
   {
      try
      {
         return (getIteration() instanceof Iteration);
      }
      catch (Exception e)
      {
         return false;
      }
   }

   @Produces
   @Current
   public Iteration getIteration()
   {
      Project project = currentProjectBean.getProject();
      if ((iteration == null) && (params.getIterationName() != null))
      {
         iteration = project.getIteration(params.getIterationName());
      }
      else if (iteration == null)
      {
         iteration = project.getCurrentIteration();
      }
      return iteration;
   }

   public List<Story> getFrontBurnerStories(final Iteration iteration)
   {
      if (frontBurnerStories == null)
      {
         frontBurnerStories = ps.getFrontBurnerStories(currentProjectBean.getProject().getId(), iteration.getId());
      }
      return frontBurnerStories;
   }

}
