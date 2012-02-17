package com.ocpsoft.socialpm.gwt.client.shared.rpc;

import org.jboss.errai.bus.server.annotations.Remote;

import com.ocpsoft.socialpm.model.user.Profile;

@Remote
public interface AuthenticationService
{
   /**
    * Sign in the current user, using the given username / password combination.
    * @return
    */
   public Profile login(String username, String password);
   
   /**
    * @return the logged in {@link Profile}, or null if the current user needs to re-authenticate.
    */
   public Profile getLoggedInProfile();
}