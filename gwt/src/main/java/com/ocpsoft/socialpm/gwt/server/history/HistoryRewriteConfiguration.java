package com.ocpsoft.socialpm.gwt.server.history;

import javax.servlet.ServletContext;

import com.ocpsoft.rewrite.config.Configuration;
import com.ocpsoft.rewrite.config.ConfigurationBuilder;
import com.ocpsoft.rewrite.servlet.config.DispatchType;
import com.ocpsoft.rewrite.servlet.config.Forward;
import com.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import com.ocpsoft.rewrite.servlet.config.Path;

public class HistoryRewriteConfiguration extends HttpConfigurationProvider
{

   @Override
   public Configuration getConfiguration(ServletContext context)
   {
      return ConfigurationBuilder.begin().defineRule()
               .when(DispatchType.isRequest()
                        .and(Path.matches("{path}").where("path").matches(".*"))
                        .andNot(Resource.exists("{path}"))
                        .andNot(ServletMapping.includes("{path}")))
               .perform(Forward.to("/index.html"));
   }

   @Override
   public int priority()
   {
      return 0;
   }

}
