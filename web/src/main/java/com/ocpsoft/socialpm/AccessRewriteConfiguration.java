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

import com.ocpsoft.common.services.NonEnriching;
import com.ocpsoft.rewrite.bind.El;
import com.ocpsoft.rewrite.config.Configuration;
import com.ocpsoft.rewrite.config.ConfigurationBuilder;
import com.ocpsoft.rewrite.config.Direction;
import com.ocpsoft.rewrite.config.Invoke;
import com.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import com.ocpsoft.rewrite.servlet.config.Path;
import com.ocpsoft.rewrite.servlet.config.Redirect;
import com.ocpsoft.rewrite.servlet.config.rule.Join;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public class AccessRewriteConfiguration extends HttpConfigurationProvider implements NonEnriching
{
   @Override
   public Configuration getConfiguration(final ServletContext context)
   {
      return ConfigurationBuilder.begin()

               .addRule(Join.path("/").to("/pages/home.xhtml"))
               .addRule(Join.path("/projects/new").to("/pages/project/create.xhtml"))

               .addRule(Join.path("/signup").to("/pages/signup.xhtml"))

               // 404 and Error
               .addRule(Join.path("/404").to("/pages/404.xhtml"))
               .addRule(Join.path("/error").to("/pages/error.xhtml"))

               // Authentication
               .defineRule()
               .when(Direction.isInbound().and(Path.matches("/logout")))
               .perform(Invoke.binding(El.retrievalMethod("authentication.logout"))
                        .and(Redirect.temporary(context.getContextPath() + "/")))

      ;
   }

   @Override
   public int priority()
   {
      return 10;
   }
}
