/*
 * Copyright 2011 <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ocpsoft.socialpm.faces;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;

import com.ocpsoft.logging.Logger;

public class CacheControlPhaseListener implements PhaseListener
{
   private static final long serialVersionUID = 3470377662512577653L;
   private static final Logger log = Logger.getLogger(CacheControlPhaseListener.class);

   public CacheControlPhaseListener()
   {
      log.info("CacheControlPhaseListener is ACTIVE");
   }

   @Override
   public PhaseId getPhaseId()
   {
      return PhaseId.RENDER_RESPONSE;
   }

   @Override
   public void afterPhase(final PhaseEvent event)
   {}

   @Override
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