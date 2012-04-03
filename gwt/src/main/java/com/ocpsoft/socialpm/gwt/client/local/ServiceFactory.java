package com.ocpsoft.socialpm.gwt.client.local;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.errai.ioc.client.api.Caller;

import com.ocpsoft.socialpm.gwt.client.shared.rpc.AuthenticationService;
import com.ocpsoft.socialpm.gwt.client.shared.rpc.ProfileService;
import com.ocpsoft.socialpm.gwt.client.shared.rpc.ProjectService;
import com.ocpsoft.socialpm.gwt.client.shared.rpc.StoryService;

@ApplicationScoped
public class ServiceFactory
{
   @Inject
   private Caller<AuthenticationService> authService;

   @Inject
   private Caller<ProfileService> profileService;

   @Inject
   private Caller<ProjectService> projectService;

   @Inject
   private Caller<StoryService> storyService;

   public Caller<AuthenticationService> getAuthService()
   {
      return authService;
   }

   public Caller<ProfileService> getProfileService()
   {
      return profileService;
   }

   public Caller<ProjectService> getProjectService()
   {
      return projectService;
   }

   public Caller<StoryService> getStoryService()
   {
      return storyService;
   }

}
