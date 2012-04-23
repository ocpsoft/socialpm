package com.ocpsoft.socialpm.gwt.server.security;

import javax.inject.Inject;

import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;

import com.ocpsoft.socialpm.gwt.client.shared.rpc.ProfileService;
import com.ocpsoft.socialpm.gwt.server.security.authorization.ProfileBinding;
import com.ocpsoft.socialpm.gwt.server.security.authorization.ProjectAdmin;
import com.ocpsoft.socialpm.gwt.server.security.authorization.ProjectBinding;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.user.Profile;

public class SecurityRules
{

   @Inject
   private ProfileService profileService;

   @Secures
   @ProjectAdmin
   public boolean isSomething(Identity identity, @ProfileBinding Profile profile)
   {
      return isLoggedIn(identity);
   }

   public boolean isProjectAdmin(@ProjectBinding Project project, Identity identity)
   {
      return identity.hasRole("ADMIN", project.getId().toString(), "PROJECT_MEMBERSHIP");
   }

   private boolean isLoggedIn(Identity identity)
   {
      return identity.isLoggedIn();
   }
}