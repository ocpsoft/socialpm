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

package com.ocpsoft.socialpm.faces;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;

import com.ocpsoft.shade.org.apache.commons.logging.Log;
import com.ocpsoft.shade.org.apache.commons.logging.LogFactory;

public class CacheControlPhaseListener implements PhaseListener
{
   private static final long serialVersionUID = 3470377662512577653L;
   private static final Log log = LogFactory.getLog(CacheControlPhaseListener.class);

   public CacheControlPhaseListener()
   {
      log.info("CacheControlPhaseListener is ACTIVE");
   }

   public PhaseId getPhaseId()
   {
      return PhaseId.RENDER_RESPONSE;
   }

   public void afterPhase(final PhaseEvent event)
   {
   }

   public void beforePhase(final PhaseEvent event)
   {
      HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
                .getResponse();
      response.addHeader("Pragma", "no-cache");
      response.addHeader("Cache-Control", "no-cache");
      response.addHeader("Cache-Control", "must-revalidate");
      response.addHeader("Expires", "Mon, 1 Aug 1999 10:00:00 GMT");
   }
}