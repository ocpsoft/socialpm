package com.ocpsoft.socialpm.gwt.server.history;

import javax.servlet.ServletContext;

import com.ocpsoft.logging.Logger;
import com.ocpsoft.rewrite.config.Configuration;
import com.ocpsoft.rewrite.config.ConfigurationBuilder;
import com.ocpsoft.rewrite.context.EvaluationContext;
import com.ocpsoft.rewrite.servlet.config.DispatchType;
import com.ocpsoft.rewrite.servlet.config.Forward;
import com.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import com.ocpsoft.rewrite.servlet.config.HttpOperation;
import com.ocpsoft.rewrite.servlet.config.Path;
import com.ocpsoft.rewrite.servlet.config.Resource;
import com.ocpsoft.rewrite.servlet.config.ServletMapping;
import com.ocpsoft.rewrite.servlet.http.event.HttpServletRewrite;

public class HistoryRewriteConfiguration extends HttpConfigurationProvider
{
   public static Logger log = Logger.getLogger(HistoryRewriteConfiguration.class);

   @Override
   public Configuration getConfiguration(ServletContext context)
   {
      return ConfigurationBuilder
               .begin()

               .defineRule()
               .perform(new HttpOperation() {
                  @Override
                  public void performHttp(HttpServletRewrite event, EvaluationContext context)
                  {
                     log.info("Requested resource: " + event.getURL());
                  }
               })

               .defineRule()
               .when(Path.matches("/"))
               .perform(Forward.to("/index.jsp"))

               .defineRule()
               .when(DispatchType.isRequest()
                        .and(Path.matches("{path}").where("path").matches(".*"))
                        .andNot(Resource.exists("{path}"))
                        .andNot(ServletMapping.includes("{path}")))
               .perform(Forward.to("/index.jsp"));
   }

   @Override
   public int priority()
   {
      return 0;
   }
}
