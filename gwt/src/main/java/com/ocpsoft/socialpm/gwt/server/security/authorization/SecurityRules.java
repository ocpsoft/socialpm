package com.ocpsoft.socialpm.gwt.server.security.authorization;

import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.LoggedIn;
import org.jboss.seam.security.annotations.Secures;

public class SecurityRules {

   @Secures
   @LoggedIn
   public boolean isLoggedIn(Identity identity)
   {
      return identity.isLoggedIn();
   }
}