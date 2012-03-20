package com.ocpsoft.socialpm.gwt.server.security.authorization;

import javax.inject.Inject;

import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.LoggedIn;
import org.jboss.seam.security.annotations.Secures;

import com.ocpsoft.socialpm.gwt.client.shared.rpc.ProfileService;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.user.Profile;

public class SecurityRules {

   @Inject
   private ProfileService profileService;
   
   @Secures
   @LoggedIn
   public boolean isLoggedIn(Identity identity, @ProfileBinding Profile profile)
   {
      return identity.isLoggedIn() && profile.getIdentityKeys().contains(identity.getUser().getKey());
   }

   @Secures
   @ProjectAdmin
   public boolean isSomething(Identity identity, @ProfileBinding Profile profile)
   {
      return identity.isLoggedIn() && profile.getIdentityKeys().contains(identity.getUser().getKey());
   }
   
   public boolean isProjectAdmin(@ProjectBinding Project project, Identity identity)
   {
      return identity.hasRole("ADMIN", project.getId().toString(), "PROJECT_MEMBERSHIP");
   }
}