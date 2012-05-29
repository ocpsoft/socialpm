package com.ocpsoft.socialpm.gwt.server.security;

import javax.inject.Inject;

import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;

import com.ocpsoft.socialpm.gwt.client.shared.rpc.ProfileService;
import com.ocpsoft.socialpm.gwt.server.security.authorization.ProfileBinding;
import com.ocpsoft.socialpm.gwt.server.security.authorization.ProjectAdmin;
import com.ocpsoft.socialpm.gwt.server.security.authorization.SameProfileLoggedIn;
import com.ocpsoft.socialpm.model.user.Profile;

public class SecurityRules
{
   @Secures
   @ProjectAdmin
   public boolean isSomething(Identity identity, @ProfileBinding Profile profile)
   {
      return identity.isLoggedIn();
   }

   // public boolean isProjectAdmin(@ProjectBinding Project project, Identity identity)
   // {
   // return identity.hasRole("ADMIN", project.getId().toString(), "PROJECT_MEMBERSHIP");
   // }

   @Secures
   @SameProfileLoggedIn
   public boolean isSameProfileLoggedIn(@ProfileBinding Profile profile, Identity identity)
   {
      return identity.isLoggedIn() && profile != null && profile.getUsername() != null
               && profile.getUsername().equals(identity.getUser().getKey());
   }

}