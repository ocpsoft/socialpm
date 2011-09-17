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

public class OpenIdRelyingPartySpiImpl implements OpenIdRelyingPartySpi
{
   @Inject
   private ServletContext servletContext;

   @Inject
   OpenIdAuthenticator openIdAuthenticator;

   @Inject
   Event<DeferredAuthenticationEvent> deferredAuthentication;

   @Transactional
   public void loginSucceeded(final OpenIdPrincipal principal, final ResponseHolder responseHolder)
   {
      try {
         openIdAuthenticator.success(principal);
         deferredAuthentication.fire(new DeferredAuthenticationEvent(true));

         responseHolder.getResponse().sendRedirect(servletContext.getContextPath() + "/");
      }
      catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public void loginFailed(final String message, final ResponseHolder responseHolder)
   {
      try {
         responseHolder.getResponse().sendRedirect(servletContext.getContextPath() + "/error");
      }
      catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}