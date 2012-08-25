package com.ocpsoft.socialpm.gwt.server.rewrite;

import javax.servlet.ServletContext;

import org.ocpsoft.logging.Logger;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.param.Transform;
import org.ocpsoft.rewrite.servlet.config.Forward;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.Path;

public class StylesheetsRewriteConfiguration extends HttpConfigurationProvider
{
   public static Logger log = Logger.getLogger(StylesheetsRewriteConfiguration.class);

   @Override
   public Configuration getConfiguration(ServletContext context)
   {
      return null;
   }

   @Override
   public int priority()
   {
      return -1;
   }
}
