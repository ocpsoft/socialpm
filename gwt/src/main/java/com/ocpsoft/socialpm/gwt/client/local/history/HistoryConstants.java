package com.ocpsoft.socialpm.gwt.client.local.history;

import org.ocpsoft.rewrite.gwt.client.history.HistoryStateImpl;

import com.ocpsoft.socialpm.gwt.client.local.view.project.view.ProjectPlace;
import com.ocpsoft.socialpm.gwt.client.local.view.story.create.NewStoryPlace;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.user.Profile;

public class HistoryConstants
{
   public static String DELIMETER()
   {
      return "/";
   }

   public static final String HOME()
   {
      String contextPath = HistoryStateImpl.getContextPath();
      System.out.println("HOME constant retrieved when value of contextPath was [" + contextPath + "]");
      return contextPath;
   }

   public static final String SIGNUP()
   {
      return "signup";
   }

   public static final String LOGIN()
   {
      return "login";
   }

   public static String LOGOUT()
   {
      return "logout";
   }

   public static final String NEW_PROJECT()
   {
      return "new-project";
   }

   public static final String PROJECT(Project project)
   {
      return new ProjectPlace.Tokenizer()
      .getToken(new ProjectPlace(project.getOwner().getUsername(), project.getSlug()));
   }

   public static final String NEW_STORY(Project project)
   {
      return new NewStoryPlace.Tokenizer()
               .getToken(new NewStoryPlace(project.getOwner().getUsername(), project.getSlug()));
   }

   public static final String PROFILE(Profile profile)
   {
      return profile.getUsername();
   }

}
