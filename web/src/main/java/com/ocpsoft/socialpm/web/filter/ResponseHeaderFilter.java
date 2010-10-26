/*
 * Copyright 2010 - Lincoln Baxter, III (lincoln@ocpsoft.com) - Licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 - Unless required by applicable
 * law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.ocpsoft.socialpm.web.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class ResponseHeaderFilter implements Filter
{
   FilterConfig fc;

   public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) throws IOException, ServletException
   {
      HttpServletResponse response = (HttpServletResponse) res;

      for (Enumeration<?> e = this.fc.getInitParameterNames(); e.hasMoreElements();)
      {
         String headerName = (String) e.nextElement();
         response.addHeader(headerName, this.fc.getInitParameter(headerName));
      }

      chain.doFilter(req, response);
   }

   public void init(final FilterConfig filterConfig)
   {
      this.fc = filterConfig;
   }

   public void destroy()
   {
      this.fc = null;
   }
}