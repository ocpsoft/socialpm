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
package com.ocpsoft.socialpm.web;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
@Named("params")
@RequestScoped
public class ParamsBean
{
   private String projectSlug;
   private String profileUsername;

   public String getProjectSlug()
   {
      return projectSlug;
   }

   public void setProjectSlug(final String projectSlug)
   {
      this.projectSlug = projectSlug;
   }

   public String getProfileUsername()
   {
      return profileUsername;
   }

   public void setProfileUsername(final String profileUsername)
   {
      this.profileUsername = profileUsername;
   }
}
