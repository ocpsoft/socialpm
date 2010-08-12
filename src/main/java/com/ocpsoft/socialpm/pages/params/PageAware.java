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

package com.ocpsoft.socialpm.pages.params;

import java.io.Serializable;

import javax.inject.Inject;

import org.jboss.seam.international.status.Messages;

import com.ocpsoft.socialpm.faces.FacesUtils;
import com.ocpsoft.socialpm.model.FeedService;
import com.ocpsoft.socialpm.model.ProjectService;
import com.ocpsoft.socialpm.model.StoryService;
import com.ocpsoft.socialpm.model.UserService;

/*
 * Params & Beans MUST be accessed in a request scoped fashion
 */
public abstract class PageAware implements Serializable
{
   private static final long serialVersionUID = 8063523092078033882L;

   @Inject
   protected Messages messages;

   @Inject
   protected ParamsBean params;

   @Inject
   protected ProjectService ps;

   @Inject
   protected StoryService ss;

   @Inject
   protected UserService us;

   @Inject
   protected FeedService fs;

   @Inject
   protected FacesUtils facesUtils;
}
