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

import javax.servlet.ServletContext;

import com.ocpsoft.rewrite.config.Configuration;
import com.ocpsoft.rewrite.config.ConfigurationBuilder;
import com.ocpsoft.rewrite.config.Direction;
import com.ocpsoft.rewrite.config.Invoke;
import com.ocpsoft.rewrite.servlet.config.DispatchType;
import com.ocpsoft.rewrite.servlet.config.Forward;
import com.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import com.ocpsoft.rewrite.servlet.config.Join;
import com.ocpsoft.rewrite.servlet.config.Path;
import com.ocpsoft.rewrite.servlet.config.Redirect;
import com.ocpsoft.rewrite.servlet.config.Substitute;
import com.ocpsoft.rewrite.servlet.config.bind.El;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
public class URLRewriteConfiguration extends HttpConfigurationProvider
{
   @Override
   public Configuration getConfiguration(final ServletContext context)
   {
      return ConfigurationBuilder.begin()

               // Rewrite
               .defineRule()
               .when(Direction.isInbound().and(Path.matches("/{path}/").where("path").matches(".*")))
               .perform(Redirect.permanent("/{path}"))
               .defineRule()
               .when(Direction.isOutbound().and(Path.matches("/{path}/").where("path").matches(".*")))
               .perform(Substitute.with("/{path}"))

               // Application mappings
               .add(Join.path("/").to("/pages/home.xhtml"))

               .add(Join.path("/p/{project}").to("/pages/project/view.xhtml")
                        .where("project").bindsTo(El.property("projects.current.name"))
                        .and(Invoke.retrieveFrom(El.retrievalMethod("projects.loadCurrent"))))

               .add(Join.path("/new-project").to("/pages/project/create.xhtml"))

               // 404 and Error
               .add(Join.path("/404").to("/pages/404.xhtml"))
               .add(Join.path("/error").to("/pages/error.xhtml"))

               .defineRule().when(
                        Direction.isInbound()
                                 .and(DispatchType.isRequest())
                                 .and(Path.matches(".*\\.xhtml"))
                                 .andNot(Path.matches(".*javax\\.faces\\.resource.*"))
                                 .andNot(Path.matches("/rfRes/.*")))
               .perform(Forward.to("/404"));
   }

   @Override
   public int priority()
   {
      return 0;
   }
}
