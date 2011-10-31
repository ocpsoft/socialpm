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

import javax.inject.Named;

@Named
public class UrlConstants
{
   public static final String HOME = "/pages/home.xhtml?faces-redirect=true";
   public static final String PROJECT_VIEW = "/pages/project/view.xhtml?faces-redirect=true";
   public static final String PROJECT_CREATE = "/pages/project/create.xhtml?faces-redirect=true";
   public static final String REFRESH = "?faces-redirect=true";
   public static final String E404 = "/pages/404";
}
