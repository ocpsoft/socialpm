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

package com.ocpsoft.socialpm.web.constants;

import javax.faces.application.ProjectStage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
public class ApplicationConfig
{
   public static final String GUEST_ACCOUNT_NAME = "guest";

   private String siteName = "SocialPM";
   private String blogUrl = "http://ocpsoft.com/";
   private boolean analyticsEnabled = false;
   private String analyticsId = "";

   public String getBlogUrl()
   {
      return blogUrl;
   }

   public void setBlogUrl(final String blogUrl)
   {
      this.blogUrl = blogUrl;
   }

   public String getSiteName()
   {
      return siteName;
   }

   public void setSiteName(final String siteName)
   {
      this.siteName = siteName;
   }

   public boolean isAnalyticsEnabled()
   {
      return analyticsEnabled;
   }

   public void setAnalyticsEnabled(final boolean analyticsEnabled)
   {
      this.analyticsEnabled = analyticsEnabled;
   }

   public String getAnalyticsId()
   {
      return analyticsId;
   }

   public void setAnalyticsId(final String analyticsId)
   {
      this.analyticsId = analyticsId;
   }

   public boolean isDebugMode()
   {
      return ProjectStage.Development.equals(FacesContext.getCurrentInstance().getApplication().getProjectStage());
   }
}
