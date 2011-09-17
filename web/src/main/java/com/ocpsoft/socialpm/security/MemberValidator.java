package com.ocpsoft.socialpm.security;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.events.PostAuthenticateEvent;
import org.jboss.seam.security.external.openid.OpenIdUser;
import org.jboss.seam.transaction.Transactional;
import org.picketlink.idm.api.IdentitySession;
import org.picketlink.idm.api.User;

import com.ocpsoft.socialpm.model.UserService;

public @RequestScoped
class MemberValidator
{
   @Inject
   private UserService userService;

   @Inject
   private Credentials credentials;

   @Inject
   private Identity identity;

   @Inject
   private IdentitySession identitySession;

   public @Transactional
   void observePostAuthenticate(@Observes final PostAuthenticateEvent event)
   {
      User user = identity.getUser();

      if (user instanceof OpenIdUser) {
         OpenIdUser oid = (OpenIdUser) user;
         credentials.setUsername(oid.getAttribute("firstName") + " " + oid.getAttribute("lastName"));
      }
   }
}