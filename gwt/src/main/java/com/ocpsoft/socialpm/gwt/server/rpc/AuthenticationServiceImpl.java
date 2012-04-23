package com.ocpsoft.socialpm.gwt.server.rpc;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.jboss.errai.bus.server.annotations.Service;
import org.jboss.seam.security.CredentialsImpl;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.events.DeferredAuthenticationEvent;
import org.jboss.seam.security.events.LoggedInEvent;
import org.jboss.seam.security.events.LoginFailedEvent;
import org.jboss.seam.security.management.IdmAuthenticator;
import org.ocpsoft.logging.Logger;
import org.picketlink.idm.api.User;

import com.ocpsoft.socialpm.gwt.client.shared.rpc.AuthenticationService;
import com.ocpsoft.socialpm.gwt.client.shared.rpc.ProfileService;
import com.ocpsoft.socialpm.gwt.server.security.authentication.Authenticated;
import com.ocpsoft.socialpm.gwt.server.util.PersistenceUtil;
import com.ocpsoft.socialpm.model.user.Profile;

@SessionScoped
@Service
public class AuthenticationServiceImpl extends PersistenceUtil implements Serializable, AuthenticationService
{
   private static final long serialVersionUID = -4014251052694227076L;
   static Logger logger = Logger.getLogger(AuthenticationServiceImpl.class);

   @PersistenceContext(type = PersistenceContextType.EXTENDED)
   protected EntityManager em;

   @Override
   public EntityManager getEntityManager()
   {
      return em;
   }

   @Inject
   private Identity identity;

   @Inject
   private CredentialsImpl credential;

   @Inject
   private ProfileService profileService;

   private Profile loggedIn;

   @Override
   public Profile login(String username, String password)
   {
      if (loggedIn == null)
      {
         identity.setAuthenticatorClass(IdmAuthenticator.class);
         credential.setUsername(username);
         credential.setPassword(password);

         String outcome = Identity.RESPONSE_LOGIN_FAILED;
         try {
            outcome = identity.login();
         }
         catch (Exception e) {
            outcome = identity.login();
         }

         if (Identity.RESPONSE_LOGIN_SUCCESS.equals(outcome))
         {
            loggedIn = profileService.getProfileByIdentityKey(identity.getUser().getKey());
         }
      }
      return loggedIn;
   }

   @Override
   public void logout()
   {
      String userKey = identity.getUser().getKey();
      identity.setAuthenticatorClass(IdmAuthenticator.class);
      identity.logout();
      logger.info("User logged out [{}]", userKey);
   }

   public static void loginSuccess(@Observes final LoggedInEvent event) throws IOException
   {
      User user = event.getUser();
      logger.info("User logged in [{}, {}]", user.getId(), user.getKey());
   }

   public static void openLoginSuccess(@Observes final DeferredAuthenticationEvent event)
   {
      if (event.isSuccess())
         logger.info("User logged in with OpenID");
      else
         logger.info("User failed to login via OpenID, potentially due to cancellation");
   }

   public static void loginFailed(@Observes final LoginFailedEvent event, Identity identity)
   {
      Exception exception = event.getLoginException();
      if (exception != null)
      {
         logger.error(
                  "Login failed due to exception" + identity.getAuthenticatorName() + ", "
                           + identity.getAuthenticatorClass()
                           + ", " + identity);
      }
      try {
         Thread.sleep(500);
      }
      catch (InterruptedException e) {
         throw new RuntimeException(e);
      }
   }

   @Override
   @Produces
   @Authenticated
   public Profile getLoggedInProfile()
   {
      Profile result = null;
      if (identity.isLoggedIn() && loggedIn != null)
      {
         result = loggedIn;
      }
      else if (identity.isLoggedIn() && loggedIn == null)
      {
         result = profileService.getProfileByIdentityKey(identity.getUser().getKey());
      }
      return result;
   }

}