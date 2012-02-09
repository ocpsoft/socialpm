package com.ocpsoft.socialpm.gwt.server.rpc;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.errai.bus.server.annotations.Service;
import org.jboss.seam.security.Authenticator.AuthenticationStatus;
import org.jboss.seam.security.CredentialsImpl;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.events.DeferredAuthenticationEvent;
import org.jboss.seam.security.events.LoggedInEvent;
import org.jboss.seam.security.events.LoginFailedEvent;
import org.jboss.seam.security.external.openid.OpenIdAuthenticator;
import org.jboss.seam.security.management.IdmAuthenticator;
import org.picketlink.idm.api.User;

import com.ocpsoft.logging.Logger;
import com.ocpsoft.socialpm.gwt.client.shared.rpc.LoginService;
import com.ocpsoft.socialpm.model.user.Profile;
import com.ocpsoft.socialpm.services.ProfileService;

@RequestScoped
@Service
public class LoginServiceImpl implements LoginService
{
   static Logger logger = Logger.getLogger(LoginServiceImpl.class);

   @PersistenceContext
   private EntityManager em;

   @Inject
   private Identity identity;

   @Inject
   private CredentialsImpl credential;

   @Inject
   private ProfileService profiles;

   @PostConstruct
   public void setup()
   {
      profiles.setEntityManager(em);
   }

   @Override
   public Profile login(String username, String password)
   {
      Profile result = null;

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

      if (!Identity.RESPONSE_LOGIN_SUCCESS.equals(outcome))
      {
         try {
            Thread.sleep(500);
         }
         catch (InterruptedException e) {
            throw new RuntimeException(e);
         }
      }
      else
      {
         result = profiles.getProfileByIdentityKey(identity.getUser().getKey());
      }

      return result;
   }

   public void logout()
   {
      identity.setAuthenticatorClass(IdmAuthenticator.class);
      identity.logout();
   }

   public static void loginSuccess(@Observes final LoggedInEvent event) throws IOException
   {
      User user = event.getUser();
      logger.info("User logged in [{}, {}]", user.getId(), user.getKey());
   }

   /*
    * This is called outside of the JSF lifecycle.
    */
   public static void openLoginSuccess(@Observes final DeferredAuthenticationEvent event)
   {
      if (event.isSuccess())
      {
         logger.info("User logged in with OpenID");
      }
      else
      {
         logger.info("User failed to login via OpenID, potentially due to cancellation");
      }
   }

   public static void loginFailed(@Observes final LoginFailedEvent event, Identity identity,
            OpenIdAuthenticator openAuth)
   {
      if (!(OpenIdAuthenticator.class.equals(identity.getAuthenticatorClass())
               && AuthenticationStatus.DEFERRED.equals(openAuth.getStatus())))
      {
         Exception exception = event.getLoginException();
         if (exception != null)
         {
            logger.error(
                     "Login failed due to exception" + identity.getAuthenticatorName() + ", "
                              + identity.getAuthenticatorClass()
                              + ", " + identity); // TODO , exception );
         }
         try {
            Thread.sleep(500);
         }
         catch (InterruptedException e) {
            throw new RuntimeException(e);
         }
      }
   }

}