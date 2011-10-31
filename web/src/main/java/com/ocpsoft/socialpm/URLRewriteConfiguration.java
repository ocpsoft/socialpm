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
               .when(Direction.isInbound().and(
                        Path.matches("/p/{project}").where("project")
                                 .constrainedBy(new RegexConstraint("(?=.*[A-Z]+.*).*"))
                                 .transformedBy(new ToLowerCase())))
               .perform(Redirect.permanent(context.getContextPath() + "/p/{project}"))

               .defineRule()
               .when(Direction.isInbound().and(
                        Path.matches("/p/{project}{tail}").where("project")
                                 .constrainedBy(new RegexConstraint("(?=.*[A-Z]+.*).*"))
                                 .transformedBy(new ToLowerCase())
                                 .where("tail").matches("/.*")))
               .perform(Redirect.permanent(context.getContextPath() + "/p/{project}{tail}"))

               // Bind project value to EL & Load current Project
               .defineRule()
               .when(Direction.isInbound().and(
                        Path.matches("/p/{project}.*").where("project").matches(PROJECT)
                                 .bindsTo(El.property("params.projectSlug"))))
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
