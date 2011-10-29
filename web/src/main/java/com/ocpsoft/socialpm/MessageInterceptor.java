/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
import com.ocpsoft.rewrite.servlet.config.RequestParameter;
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

               .defineRule()
               .when(
                        RequestParameter.exists("info")
                                 .or(RequestParameter.exists("warning"))
                                 .or(RequestParameter.exists("error"))
               )
               .perform(new HttpOperation() {
                  @Override
                  public void performHttp(HttpServletRewrite event, EvaluationContext context)
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
