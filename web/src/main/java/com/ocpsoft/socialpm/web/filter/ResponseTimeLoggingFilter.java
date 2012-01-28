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
package com.ocpsoft.socialpm.web.filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.ocpsoft.socialpm.util.Timer;

@WebFilter(urlPatterns = { "/*" })
public class ResponseTimeLoggingFilter implements Filter
{
   @Override
   public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException
   {
      if (DispatcherType.REQUEST.equals(request.getDispatcherType())
               && !((HttpServletRequest) request).getRequestURI().contains(".xhtml"))
      {
         Timer timer = Timer.getTimer().start();
         chain.doFilter(request, response);
         timer.stop();
         double time = timer.getElapsedMilliseconds();
         System.out.println("Request completed in [" + (time / 1000.0) + "] seconds: ["
                  + ((HttpServletRequest) request).getRequestURI() + "]");
      }
      else
         chain.doFilter(request, response);
   }

   @Override
   public void init(final FilterConfig filterConfig)
   {}

   @Override
   public void destroy()
   {}
}