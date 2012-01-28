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
package com.ocpsoft.socialpm.rewrite;

import java.util.Arrays;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.jboss.seam.international.status.Messages;

import com.ocpsoft.rewrite.config.Configuration;
import com.ocpsoft.rewrite.config.ConfigurationBuilder;
import com.ocpsoft.rewrite.context.EvaluationContext;
import com.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import com.ocpsoft.rewrite.servlet.config.HttpOperation;
import com.ocpsoft.rewrite.servlet.config.QueryString;
import com.ocpsoft.rewrite.servlet.http.event.HttpServletRewrite;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public class MessageInterceptor extends HttpConfigurationProvider
{
   @Inject
   private Messages messages;

   @Override
   public Configuration getConfiguration(final ServletContext context)
   {
      return ConfigurationBuilder.begin()

               // TODO support empty {path} parameters
               // TODO encode and strip these messages

               .defineRule()
               .when(
                        QueryString.parameterExists("info")
                                 .or(QueryString.parameterExists("warning"))
                                 .or(QueryString.parameterExists("error"))
               )
               .perform(new HttpOperation() {
                  @Override
                  public void performHttp(final HttpServletRewrite event, final EvaluationContext context)
                  {
                     for (String type : Arrays.asList("info", "warning", "error")) {
                        String error = event.getRequest().getParameter(type);
                        if (error != null)
                        {
                           if ("info".equals(type))
                              messages.info(error);
                           if ("warn".equals(type))
                              messages.warn(error);
                           if ("error".equals(type))
                              messages.error(error);
                        }
                     }

                     // URLBuilder url = URLBuilder.begin();
                     // QueryStringBuilder query = url
                     // .addPathSegments(event.getRequestPath())
                     // .getQueryStringBuilder();
                     //
                     // query.removeParameter("info");
                     // query.removeParameter("warn");
                     // query.removeParameter("error");
                     //
                     // String target = url.toURL();
                     // Forward.to(target).perform(event, context);
                  }
               });

      // TODO remove the parameter once it has been added to messages
   }

   @Override
   public int priority()
   {
      return 0;
   }
}
