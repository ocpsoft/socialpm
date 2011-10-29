package com.ocpsoft.socialpm.security;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.seam.security.Identity;
import org.jboss.seam.security.events.PostAuthenticateEvent;
import org.jboss.seam.security.external.openid.OpenIdUser;
import org.jboss.seam.transaction.Transactional;
import org.picketlink.idm.common.exception.IdentityException;

@RequestScoped
public class PostAuthenticateObserver
{
   @Inject
   private Identity identity;

   public @Transactional
   void observePostAuthenticate(@Observes final PostAuthenticateEvent event) throws IdentityException
   {
      Object user = identity.getUser();

      if (user instanceof OpenIdUser) {
         OpenIdUser oid = (OpenIdUser) user;
      }
      else
      {

      }
   }
}