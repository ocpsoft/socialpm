package com.ocpsoft.socialpm.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.jboss.seam.security.Identity;
import org.jboss.seam.security.events.DeferredAuthenticationEvent;
import org.jboss.seam.security.external.api.ResponseHolder;
import org.jboss.seam.security.external.openid.OpenIdAuthenticator;
import org.jboss.seam.security.external.openid.api.OpenIdPrincipal;
import org.jboss.seam.security.external.spi.OpenIdRelyingPartySpi;
import org.jboss.seam.security.management.picketlink.IdentitySessionProducer;
import org.jboss.seam.transaction.Transactional;

import com.ocpsoft.socialpm.domain.user.Profile;
import com.ocpsoft.socialpm.model.ProfileService;

public class OpenIdAuthHandler implements OpenIdRelyingPartySpi
{
   @PersistenceContext(type = PersistenceContextType.EXTENDED)
   private EntityManager em;

   @Inject
   private Identity identity;

   @Inject
   private ProfileService profileService;

   @Inject
   private ServletContext servletContext;

   @Inject
   private OpenIdAuthenticator openIdAuthenticator;

   @Inject
   private Event<DeferredAuthenticationEvent> deferredAuthentication;

   @Inject
   private HttpServletRequest request;

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
         if (attachProfile(principal, responseHolder))
            responseHolder.getResponse().sendRedirect(servletContext.getContextPath() + "/");
      }
      catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   @PostConstruct
   public void init()
   {
      profileService.setEntityManager(em);
   }

   private boolean attachProfile(OpenIdPrincipal principal, ResponseHolder responseHolder)
   {
      try
      {
         Map<String, Object> sessionOptions = new HashMap<String, Object>();
         sessionOptions.put(IdentitySessionProducer.SESSION_OPTION_ENTITY_MANAGER, em);

         String key = identity.getUser().getKey();
         String email = principal.getAttribute("email");

         try {
            profileService.getProfileByEmail(email);
            identity.logout();

            responseHolder.getResponse().sendRedirect(
                     servletContext.getContextPath()
                              + "/signup?error=The email address used by your OpenID account is already " +
                              "registered with a username on this website. " +
                              "Please log in with that account and visit the " +
                              "Accounts page to merge your profiles.");
         }
         catch (NoResultException e) {
            if (!profileService.hasProfileByIdentityKey(key))
            {
               Profile p = new Profile();
               p.getKeys().add(key);
               p.setEmail(email);
               p.setFullName(principal.getAttribute("firstName") + " " + principal.getAttribute("lastName"));
               p.setUsername(profileService.getRandomUsername(p.getFullName()));
               profileService.create(p);
               return true;
            }
         }
      }
      catch (Exception e) {
         try {
            identity.logout();
         }
         catch (Exception e2) {}

         throw new RuntimeException(e);
      }

      return false;
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