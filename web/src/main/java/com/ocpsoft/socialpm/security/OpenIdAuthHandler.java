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

   @Override
   @Transactional
   public void loginSucceeded(final OpenIdPrincipal principal, final ResponseHolder responseHolder)
   {
      try {
         openIdAuthenticator.success(principal);
         deferredAuthentication.fire(new DeferredAuthenticationEvent(true));

         // TODO figure out how best and where best to redirect user after OpenID signup/login
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