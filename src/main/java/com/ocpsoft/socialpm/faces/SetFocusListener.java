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

import java.util.Iterator;
import java.util.Map;

import javax.enterprise.event.Observes;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;

import org.jboss.seam.faces.event.qualifier.Before;
import org.jboss.seam.faces.event.qualifier.RenderResponse;

/**
 * Sets style class <b>errorHighlight</b> on components that have validation
 * errors. Sets the request scope <b>#{focus}</b> variable to the first
 * component with validation failure.
 */
public class SetFocusListener
{
   public void setFocus(@Observes @Before @RenderResponse PhaseEvent event)
   {
      FacesContext facesContext = event.getFacesContext();
      String focus = "";

      Iterator<String> clientIdsWithMessages = facesContext.getClientIdsWithMessages();
      while (clientIdsWithMessages.hasNext())
      {
         String clientId = clientIdsWithMessages.next();
         if (clientId != null)
         {
            if ("".equals(focus))
            {
               focus = clientId;
            }

            UIComponent component = getComponent(clientId);
            if (component != null)
            {
               Map<String, Object> attributes = component.getAttributes();
               String styleClass = (String) attributes.get("styleClass");
               if ((styleClass == null) || "".equals(styleClass))
               {
                  attributes.put("styleClass", "errorHighlight");
               }
               else if (!styleClass.contains("errorHighlight"))
               {
                  attributes.put("styleClass", styleClass + " errorHighlight");
               }
            }
         }
      }
      facesContext.getExternalContext().getRequestMap().put("focus", focus.replaceAll(":", "\\\\\\\\:"));
   }

   private UIComponent getComponent(final String clientId)
   {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      UIViewRoot viewRoot = facesContext.getViewRoot();
      UIComponent component = viewRoot.findComponent(clientId);
      if (component == null)
      {
         component = findChildComponent(facesContext, viewRoot, clientId);
      }
      return component;
   }

   private UIComponent findChildComponent(final FacesContext facesContext, final UIComponent component, final String clientId)
   {
      if ((component == null) || (component.getChildCount() == 0))
      {
         return null;
      }

      UIComponent result = null;
      for (UIComponent c : component.getChildren())
      {
         if (c.getClientId(facesContext).equals(clientId))
         {
            result = c;
            break;
         }
         else
         {
            result = findChildComponent(facesContext, c, clientId);
            if ((result != null) && clientId.equals(result.getClientId(facesContext)))
            {
               break;
            }
         }
      }
      return result;
   }
}