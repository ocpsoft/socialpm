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

import com.ocpsoft.rewrite.bind.El;
import com.ocpsoft.rewrite.config.Configuration;
import com.ocpsoft.rewrite.config.ConfigurationBuilder;
import com.ocpsoft.rewrite.config.Direction;
import com.ocpsoft.rewrite.config.Invoke;
import com.ocpsoft.rewrite.faces.config.PhaseAction;
import com.ocpsoft.rewrite.servlet.config.DispatchType;
import com.ocpsoft.rewrite.servlet.config.Forward;
import com.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import com.ocpsoft.rewrite.servlet.config.Path;
import com.ocpsoft.rewrite.servlet.config.Redirect;
import com.ocpsoft.rewrite.servlet.config.rule.Join;
import com.ocpsoft.rewrite.servlet.config.rule.TrailingSlash;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public class URLRewriteConfiguration extends HttpConfigurationProvider
{
   private static final String PROJECT = "[a-zA-Z0-9-]+";

   @Override
   public Configuration getConfiguration(final ServletContext context)
   {
      return ConfigurationBuilder
               .begin()

               .addRule(TrailingSlash.remove())

               // Application mappings
               .addRule(Join.path("/").to("/pages/home.xhtml").withId("home"))
               .addRule(Join.path("/guest").to("/pages/loggedOffHome.xhtml").withId("loggedOffHome"))
               .addRule(Join.path("/bootstrap").to("/bootstrap.xhtml"))

               // Canonicalize project name
               .defineRule()
               .when(Path.matches("/p/{project}").where("project")
                        .constrainedBy(new RegexConstraint("(?=.*[A-Z]+.*).*"))
                        .transformedBy(new ToLowerCase()))
               .perform(Redirect.permanent(context.getContextPath() + "/p/{project}"))

               .defineRule()
               .when(Path.matches("/p/{project}{tail}").where("project")
                        .constrainedBy(new RegexConstraint("(?=.*[A-Z]+.*).*"))
                        .transformedBy(new ToLowerCase())
                        .where("tail").matches("/.*"))
               .perform(Redirect.permanent(context.getContextPath() + "/p/{project}{tail}"))

               // Bind project value to EL & Load current Project
               .defineRule()
               .when(Path.matches("/p/{project}.*").where("project").matches(PROJECT)
                        .bindsTo(El.property("projects.current.slug")))
               .perform(PhaseAction.retrieveFrom(El.retrievalMethod("projects.loadCurrent")))

               .addRule(Join.path("/p/{project}").where("project").matches(PROJECT).to("/pages/project/view.xhtml"))
               .addRule(Join.path("/p/{project}/issues").where("project").matches(PROJECT)
                        .to("/pages/project/backlog.xhtml"))

               // Story pages
               .addRule(Join.path("/p/{project}/stories/new").where("project").matches(PROJECT)
                        .to("/pages/story/create.xhtml"))
               .addRule(Join.path("/p/{project}-{story}").where("project").matches(PROJECT)
                        .to("/pages/story/view.xhtml"))

               .addRule(Join.path("/projects/new").to("/pages/project/create.xhtml"))

               // 404 and Error
               .addRule(Join.path("/404").to("/pages/404.xhtml"))
               .addRule(Join.path("/error").to("/pages/error.xhtml"))

               // Authentication
               .defineRule()
               .when(Direction.isInbound().and(Path.matches("/logout")))
               .perform(Invoke.binding(El.retrievalMethod("authentication.logout")).and(
                        Redirect.temporary(context.getContextPath() + "/")))

               .defineRule().when(Direction.isInbound().and(Path.matches("/register/callback")))
               .perform(Invoke.binding(El.retrievalMethod("registration.callback")))

               // Catch all rules
               .addRule(Join.path("/{page}").to("/pages/{page}.xhtml").where("page").matches("(?!RES_NOT_FOUND)[^/]+"))

               .defineRule().when(DispatchType.isRequest()
                        .and(Direction.isInbound())
                        .and(SocialPMResources.excluded()))
               .perform(Forward.to("/404"));
   }

   @Override
   public int priority()
   {
      return 10;
   }
}
