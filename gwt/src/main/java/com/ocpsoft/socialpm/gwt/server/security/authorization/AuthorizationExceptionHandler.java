package com.ocpsoft.socialpm.gwt.server.security.authorization;

import org.jboss.seam.security.AuthorizationException;
import org.jboss.solder.exception.control.CaughtException;
import org.jboss.solder.exception.control.Handles;
import org.jboss.solder.exception.control.HandlesExceptions;
import org.ocpsoft.logging.Logger;

@HandlesExceptions
public class AuthorizationExceptionHandler
{
   static Logger logger = Logger.getLogger(AuthorizationExceptionHandler.class);
   public void handleAuthorizationException(@Handles CaughtException<AuthorizationException> evt)
   {
      logger.info("Access Denied", evt);
      evt.handled();
   }
}