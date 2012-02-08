package com.ocpsoft.socialpm.security.authorization;

import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;

import com.ocpsoft.socialpm.cdi.Current;
import com.ocpsoft.socialpm.model.project.Project;

public class SecurityRules {
	@Secures
	@Owner
	public boolean isProjectOwner(Identity identity, @Current Project project) {
		if (project == null || identity.getUser() == null) {
			return false;
		} else {
			return project.getOwner().getIdentityKeys().contains(identity.getUser().getKey());
		}
	}
}