/**
 * This file is part of SocialPM: Agile Project Management Tools (SocialPM) 
 *
 * Copyright (c)2010 Lincoln Baxter, III <lincoln@ocpsoft.com> (OcpSoft)
 * 
 * If you are developing and distributing open source applications under 
 * the GPL Licence, then you are free to use SocialPM under the GPL 
 * License:
 *
 * SocialPM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SocialPM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SocialPM.  If not, see <http://www.gnu.org/licenses/>.
 *  
 * For OEMs, ISVs, and VARs who distribute SocialPM with their products, 
 * host their product online, OcpSoft provides flexible OEM commercial 
 * Licences. 
 * 
 * Optionally, customers may choose a Commercial License. For additional 
 * details, contact OcpSoft (http://ocpsoft.com)
 */

package com.ocpsoft.socialpm.constants;

import javax.inject.Named;

@Named
public class UrlConstants
{
   public static final String ADMIN = "admin";
   public static final String HOME = "home";
   public static final String BROWSE_PROJECTS = "browseProjects";
   public static final String LOGIN = "login";
   public static final String LOGOUT = "logout";
   public static final String REGISTER = "register";
   public static final String RECOVER = "recover";
   public static final String JOIN_PROJECT = "joinProject";
   public static final String VIEW_PROJECT = "viewProject";
   public static final String VIEW_ITERATION = "viewIteration";
   public static final String BACK_BURNER = "backBurner";
   public static final String CREATE_PROJECT = "createProject";
   public static final String ADD_STORY = "addStory";
   public static final String EDIT_STORY = "editStory";
   public static final String MANAGE_FEATURES = "manageFeatures";
   public static final String REMOVE_FEATURE = "removeFeature";
   public static final String MANAGE_USERS = "manageUsers";
   public static final String MILESTONES = "manageMilestones";
   public static final String ITERATIONS = "manageIterations";
   public static final String BACKLOG = "backlog";
   public static final String EDIT_RELEASE = "editRelease";
   public static final String VIEW_STORY = "viewStory";
   public static final String VIEW_STORY_HISTORY = "viewStoryHistory";
   public static final String SEARCH = "search";
   public static final String SEARCH_QUERY = "searchQuery";
   public static final String USER_ACCOUNT = "userAccount";
   public static final String USER_PROFILE = "userProfile";
   public static final String USER_PROJECTS = "userProjects";
   public static final String REFRESH = "";
   public static final String VERIFY = "verify";

   public String getADMIN()
   {
      return ADMIN;
   }

   public String getVERIFY()
   {
      return VERIFY;
   }

   public String getMANAGE_USERS()
   {
      return MANAGE_USERS;
   }

   public String getBACK_BURNER()
   {
      return BACK_BURNER;
   }

   public String getHOME()
   {
      return HOME;
   }

   public String getLOGIN()
   {
      return LOGIN;
   }

   public String getVIEW_PROJECT()
   {
      return VIEW_PROJECT;
   }

   public String getCREATE_PROJECT()
   {
      return CREATE_PROJECT;
   }

   public String getCREATE_STORY()
   {
      return ADD_STORY;
   }

   public String getVIEW_STORY()
   {
      return VIEW_STORY;
   }

   public String getVIEW_STORY_HISTORY()
   {
      return VIEW_STORY_HISTORY;
   }

   public String getEDIT_STORY()
   {
      return EDIT_STORY;
   }

   public String getMANAGE_FEATURES()
   {
      return MANAGE_FEATURES;
   }

   public String getSEARCH()
   {
      return SEARCH;
   }

   public String getLOGOUT()
   {
      return LOGOUT;
   }

   public String getREGISTER()
   {
      return REGISTER;
   }

   public String getRECOVER()
   {
      return RECOVER;
   }

   public String getSEARCH_QUERY()
   {
      return SEARCH_QUERY;
   }

   public String getMILESTONES()
   {
      return MILESTONES;
   }

   public String getEDIT_RELEASE()
   {
      return EDIT_RELEASE;
   }

   public String getREFRESH()
   {
      return REFRESH;
   }

   public String getBACKLOG()
   {
      return BACKLOG;
   }

   public String getITERATIONS()
   {
      return ITERATIONS;
   }

   public String getREMOVE_FEATURE()
   {
      return REMOVE_FEATURE;
   }

   public String getUSER_ACCOUNT()
   {
      return USER_ACCOUNT;
   }

   public String getUSER_PROFILE()
   {
      return USER_PROFILE;
   }

   public String getVIEW_ITERATION()
   {
      return VIEW_ITERATION;
   }

   public String getUSER_PROJECTS()
   {
      return USER_PROJECTS;
   }

   public String getBROWSE_PROJECTS()
   {
      return BROWSE_PROJECTS;
   }

   public String getJOIN_PROJECT()
   {
      return JOIN_PROJECT;
   }
}
