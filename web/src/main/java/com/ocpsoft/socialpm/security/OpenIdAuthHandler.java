package com.ocpsoft.socialpm.security;

import java.io.IOException;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.jboss.seam.security.events.DeferredAuthenticationEvent;
import org.jboss.seam.security.external.api.ResponseHolder;
import org.jboss.seam.security.external.openid.OpenIdAuthenticator;
import org.jboss.seam.security.external.openid.api.OpenIdPrincipal;
import org.jboss.seam.security.external.spi.OpenIdRelyingPartySpi;
import org.jboss.seam.transaction.Transactional;

public class OpenIdAuthHandler implements OpenIdRelyingPartySpi
{
   @Inject
   private ServletContext servletContext;

   @Inject
   private OpenIdAuthenticator openIdAuthenticator;

   @Inject
   private Event<DeferredAuthenticationEvent> deferredAuthentication;

   @Inject
   private ProfileManager profiles;

   @Override
   @Transactional
   public void loginSucceeded(final OpenIdPrincipal principal, final ResponseHolder responseHolder)
   {
      try {
         openIdAuthenticator.success(principal);
         deferredAuthentication.fire(new DeferredAuthenticationEvent(true));

         /*
          * If this is a new registration, we need to create a profile for this OpenId.
          */
         profiles.attachProfile(principal);

         responseHolder.getResponse().sendRedirect(servletContext.getContextPath() + "/");
      }
      catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   @Override
   public void loginFailed(final String message, final ResponseHolder responseHolder)
   {
      try {
         responseHolder.getResponse().sendRedirect(servletContext.getContextPath() + "/signup");
      }
      catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}