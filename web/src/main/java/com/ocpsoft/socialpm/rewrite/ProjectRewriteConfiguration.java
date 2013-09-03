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

import javax.servlet.ServletContext;

import org.ocpsoft.common.services.NonEnriching;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.config.Direction;
import org.ocpsoft.rewrite.config.Not;
import org.ocpsoft.rewrite.config.Operation;
import org.ocpsoft.rewrite.context.EvaluationContext;
import org.ocpsoft.rewrite.el.El;
import org.ocpsoft.rewrite.event.Rewrite;
import org.ocpsoft.rewrite.faces.config.PhaseAction;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.Path;
import org.ocpsoft.rewrite.servlet.config.Redirect;
import org.ocpsoft.rewrite.servlet.config.Resource;
import org.ocpsoft.rewrite.servlet.config.ServletMapping;
import org.ocpsoft.rewrite.servlet.config.rule.Join;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public class ProjectRewriteConfiguration extends HttpConfigurationProvider implements NonEnriching
{
   private static final String PROJECT = "[a-zA-Z0-9-]+";

   @Override
   public Configuration getConfiguration(final ServletContext context)
   {
      return ConfigurationBuilder
               .begin()

               // Canonicalize project name TODO support outbound
               // .addRule()
               // .when(Direction.isInbound()
               // .and(Path.matches("/{profile}/{project}"))
               // .andNot(ServletMapping.includes("/{profile}/{project}")))
               //
               // .perform(Redirect.permanent(context.getContextPath() + "/{profile}/{project}"))
               // .where("project")
               // .constrainedBy(new RegexConstraint("(?=.*[A-Z]+.*).*"))
               // .transposedBy(new ToLowerCase())

               /*
                * Transform Profile Name and Project Slug
                */
               // .addRule()
               // .when(Direction.isInbound()
               // .and(Path.matches("/{profile}/{project}{**}"))
               // .andNot(ServletMapping.includes("/{profile}/{project}")))
               // .perform(Redirect.permanent(context.getContextPath() + "/{profile}/{project}{**}"))
               // .where("profile").constrainedBy(new RegexConstraint("(?=.*[A-Z]+.*).*"))
               // .transposedBy(new ToLowerCase())

               /*
                * Bind profile and project value to EL & Load current Project
                */
               .addRule()
               .when(Direction.isInbound()
                        .andNot(Path.matches("{**}.xhtml"))
                        .and(Path.matches("/{profile}{**}")))
               .perform(new Operation()
               {
                  @Override
                  public void perform(final Rewrite event, final EvaluationContext context)
                  {
                  }
               })
               .where("profile").bindsTo(El.property("params.profileUsername"))
               .where("**").matches("/.*")
               /**
                * 
                * <p/>
                * TODO we need a better way to figure out if a profile does not exist, then to show something else
                * perhaps a database validation or constraint? on the above profile property injection?
                */
               .addRule(Join.path("/{profile}").to("/pages/profile.xhtml"))
               .when(Not.any(Resource.exists("/pages/{profile}.xhtml")))
               .perform(PhaseAction.retrieveFrom(El.retrievalMethod("profiles.verifyExists")))

               .addRule()
               .when(Direction.isInbound()
                        .andNot(Path.matches("{**}.xhtml"))
                        .and(Direction.isInbound())
                        .and(Path.matches("/{profile}/{project}{**}"))
               )

               .perform(PhaseAction.retrieveFrom(El.retrievalMethod("projects.loadCurrent")))
               .where("profile").matches(PROJECT)
               .where("project").matches(PROJECT).bindsTo(El.property("params.projectSlug"))
               .where("**").matches("/.*")

               /*
                * Project Pages
                */
               .addRule(Join.path("/{profile}/{project}").to("/pages/project/view.xhtml"))
               .when(SocialPMResources.excluded()).perform(null)
               .where("project").matches(PROJECT)
               .where("**").matches(".*")

               .addRule(Join.path("/{profile}/{project}/issues").to("/pages/project/backlog.xhtml"))
               .when(SocialPMResources.excluded())
               .where("project").matches(PROJECT)
               .where("**").matches(".*")

               /*
                * Story pages
                */
               .addRule(Join.path("/{profile}/{project}/stories/new")
                        .to("/pages/story/create.xhtml")
               )
               .when(SocialPMResources.excluded())
               .where("project").matches(PROJECT)
               .where("**").matches(".*")

               /*
                * Bind story number to EL and Load current story
                */
               .addRule()
               .when(
                        Direction.isInbound()
                                 .andNot(Path.matches("{**}.xhtml"))
                                 .and(Direction.isInbound())
                                 .and(Path.matches("/{profile}/{project}/story/{story}{**}")
                                 ))
               .perform(PhaseAction.retrieveFrom(El.retrievalMethod("stories.loadCurrent")))
               .where("profile").matches(PROJECT)
               .where("project").matches(PROJECT)
               .where("story").matches(PROJECT)
               .bindsTo(El.property("params.storyNumber"))
               .where("**").matches("/.*")

               .addRule(Join.path("/{profile}/{project}/story/{story}")
                        .to("/pages/story/view.xhtml")
               )
               .when(SocialPMResources.excluded())
               .where("project").matches(PROJECT)
               .where("**").matches(".*")

               /*
                * Iteration pages
                */
               /*
                * Bind iteration number to EL and Load current story
                */
               .addRule()
               .when(Direction.isInbound()
                        .andNot(Path.matches("{**}.xhtml"))
                        .and(Direction.isInbound())
                        .and(Path.matches("/{profile}/{project}/iteration/{iteration}{**}")
                        ))
               .perform(PhaseAction.retrieveFrom(El.retrievalMethod("iterations.loadCurrent")))
               .where("profile").matches(PROJECT)
               .where("project").matches(PROJECT)
               .where("iteration").matches(PROJECT)
               .constrainedBy(new IntegerConstraint(1, null))
               .bindsTo(El.property("params.iterationNumber"))
               .where("**").matches("/.*")

               .addRule(Join.path("/{profile}/{project}/iteration/{iteration}")
                        .to("/pages/iteration/sorter.xhtml")
               )

               .when(SocialPMResources.excluded()).withId("iteration-view")
               .where("project").matches(PROJECT)
               .where("iteration").matches("\\d+").constrainedBy(new IntegerConstraint(1, null))
               .where("**").matches(".*")

      ;

   }

   @Override
   public int priority()
   {
      return 20;
   }
}
