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
package com.ocpsoft.socialpm.rewrite;

import javax.servlet.ServletContext;

import com.ocpsoft.common.services.NonEnriching;
import com.ocpsoft.rewrite.bind.El;
import com.ocpsoft.rewrite.config.Configuration;
import com.ocpsoft.rewrite.config.ConfigurationBuilder;
import com.ocpsoft.rewrite.config.Direction;
import com.ocpsoft.rewrite.config.Operation;
import com.ocpsoft.rewrite.context.EvaluationContext;
import com.ocpsoft.rewrite.event.Rewrite;
import com.ocpsoft.rewrite.faces.config.PhaseAction;
import com.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import com.ocpsoft.rewrite.servlet.config.Path;
import com.ocpsoft.rewrite.servlet.config.Redirect;
import com.ocpsoft.rewrite.servlet.config.rule.Join;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public class ProjectRewriteConfiguration extends HttpConfigurationProvider implements NonEnriching
{
   private static final String PROJECT = "[a-zA-Z0-9-]+";

   @Override
   public Configuration getConfiguration(final ServletContext context)
   {
      return ConfigurationBuilder.begin()

               // Canonicalize project name TODO support outbound
               .defineRule()
               .when(Direction.isInbound().andNot(Path.matches(".*xhtml")).and(
                        Path.matches("/{profile}/{project}")

                                 .where("profile")
                                 .constrainedBy(new RegexConstraint("(?=.*[A-Z]+.*).*"))
                                 .transformedBy(new ToLowerCase())
                                 // TODO this creates an implicit AND operator... probably need an alternative
                                 .where("project")
                                 .constrainedBy(new RegexConstraint("(?=.*[A-Z]+.*).*"))
                                 .transformedBy(new ToLowerCase())))
               .perform(Redirect.permanent(context.getContextPath() + "/{profile}/{project}"))

               /*
                * Transform Profile Name and Project Slug
                */
               .defineRule()
               .when(Direction.isInbound().andNot(Path.matches(".*xhtml")).and(
                        Path.matches("/{profile}/{project}{tail}")
                                 .where("profile")
                                 .constrainedBy(new RegexConstraint("(?=.*[A-Z]+.*).*"))
                                 .transformedBy(new ToLowerCase())

                                 .where("project")
                                 .constrainedBy(new RegexConstraint("(?=.*[A-Z]+.*).*"))
                                 .transformedBy(new ToLowerCase())
                                 .where("tail").matches("/.*")))
               .perform(Redirect.permanent(context.getContextPath() + "/{profile}/{project}{tail}"))

               /*
                *  Bind profile and project value to EL & Load current Project
                */
               .defineRule()
               .when(Direction.isInbound()
                        .andNot(Path.matches(".*xhtml"))
                        .and(Path.matches("/{profile}.*").where("profile")
                                 .bindsTo(El.property("params.profileUsername"))))
               .perform(new Operation() {
                  @Override
                  public void perform(final Rewrite event, final EvaluationContext context)
                  {}
               })

               .defineRule()
               .when(
                        Direction.isInbound()
                                 .andNot(Path.matches(".*xhtml"))
                                 .and(Direction.isInbound())
                                 .and(Path.matches("/{profile}/{project}.*")
                                          .where("profile").matches(PROJECT)
                                          .where("project").matches(PROJECT)
                                          .bindsTo(El.property("params.projectSlug")))
               )
               .perform(PhaseAction.retrieveFrom(El.retrievalMethod("projects.loadCurrent")))

               /*
                *  Project Pages
                */
               .addRule(Join.path("/{profile}/{project}")
                        .to("/pages/project/view.xhtml")
                        .where("project").matches(PROJECT)
                        .when(SocialPMResources.excluded()).perform(null)
               )

               .addRule(Join.path("/{profile}/{project}/issues")
                        .to("/pages/project/backlog.xhtml")
                        .where("project").matches(PROJECT)
                        .when(SocialPMResources.excluded())
               )

               /*
                *  Story pages
                */
               .addRule(Join.path("/{profile}/{project}/stories/new")
                        .to("/pages/story/create.xhtml")
                        .where("project").matches(PROJECT)
                        .when(SocialPMResources.excluded())
               )

               /*
                * Bind story number to EL and Load current story
                */
               .defineRule()
               .when(
                        Direction.isInbound()
                                 .andNot(Path.matches(".*xhtml"))
                                 .and(Direction.isInbound())
                                 .and(Path.matches("/{profile}/{project}/story/{story}.*")
                                          .where("profile").matches(PROJECT)
                                          .where("project").matches(PROJECT)
                                          .where("story").matches(PROJECT)
                                          .bindsTo(El.property("params.storyNumber")))
               )
               .perform(PhaseAction.retrieveFrom(El.retrievalMethod("stories.loadCurrent")))

               .addRule(Join.path("/{profile}/{project}/story/{story}")
                        .to("/pages/story/view.xhtml")
                        .where("project").matches(PROJECT)
                        .when(SocialPMResources.excluded())
               )

      ;

   }

   @Override
   public int priority()
   {
      return 20;
   }
}
