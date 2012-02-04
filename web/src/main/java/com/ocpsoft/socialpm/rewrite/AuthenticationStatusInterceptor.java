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

import javax.inject.Inject;
import javax.servlet.ServletContext;

import com.ocpsoft.rewrite.config.Configuration;
import com.ocpsoft.rewrite.config.ConfigurationBuilder;
import com.ocpsoft.rewrite.config.Direction;
import com.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import com.ocpsoft.rewrite.servlet.config.Path;
import com.ocpsoft.rewrite.servlet.config.Redirect;
import com.ocpsoft.rewrite.servlet.config.rule.Join;
import com.ocpsoft.socialpm.cdi.LoggedIn;
import com.ocpsoft.socialpm.model.user.Profile;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public class AuthenticationStatusInterceptor extends HttpConfigurationProvider
{
   @Inject
   @LoggedIn
   private Profile profile;

   @Override
   public Configuration getConfiguration(final ServletContext context)
   {
      ConfigurationBuilder config = ConfigurationBuilder.begin();
      if (!profile.isPersistent())
      {
         /*
          * If the user is not logged in, show them the guest home page instead of the dashboard.
          */
         return config.addRule(Join.path("/").to("/pages/loggedOffHome.xhtml"));
      }
      else
      {
         config.defineRule()
         .when(Direction.isInbound().and(Path.matches("/(signup|login)")))
         .perform(Redirect.temporary(context.getContextPath() + "/"));
      }
      return config;
   }

   @Override
   public int priority()
   {
      return 5;
   }
}
