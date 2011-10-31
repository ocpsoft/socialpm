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
package com.ocpsoft.socialpm;

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
