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
package com.ocpsoft.socialpm.stats;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.ocpsoft.socialpm.model.ProfileService;
import com.ocpsoft.socialpm.model.project.ProjectService;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
@Named
@RequestScoped
public class Statistics
{
   private ProfileService ps;
   private ProjectService projects;

   public Statistics()
   {}

   @Inject
   public Statistics(EntityManager em, ProfileService ps, ProjectService projects)
   {
      this.ps = ps;
      this.projects = projects;

      ps.setEntityManager(em);
      projects.setEntityManager(em);
   }

   public long getUserCount()
   {
      return ps.getProfileCount();
   }

   public long getProjectCount()
   {
      return projects.getProjectCount();
   }
}
