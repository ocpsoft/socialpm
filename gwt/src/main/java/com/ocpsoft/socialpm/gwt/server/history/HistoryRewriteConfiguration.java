package com.ocpsoft.socialpm.gwt.server.history;

import javax.servlet.ServletContext;

import org.ocpsoft.logging.Logger;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.context.EvaluationContext;
import org.ocpsoft.rewrite.servlet.config.DispatchType;
import org.ocpsoft.rewrite.servlet.config.Forward;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.HttpOperation;
import org.ocpsoft.rewrite.servlet.config.Path;
import org.ocpsoft.rewrite.servlet.config.Resource;
import org.ocpsoft.rewrite.servlet.config.ServletMapping;
import org.ocpsoft.rewrite.servlet.config.rule.Join;
import org.ocpsoft.rewrite.servlet.http.event.HttpServletRewrite;

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

               .addRule(Join.path("/").to("/index.jsp").withInboundCorrection())

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
