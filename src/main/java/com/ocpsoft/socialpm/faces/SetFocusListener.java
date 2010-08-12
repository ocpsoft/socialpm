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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 * Sets style class <b>errorHighlight</b> on components that have validation
 * errors. Sets the request scope <b>#{focus}</b> variable to the first
 * component with validation failure.
 */
public class SetFocusListener implements PhaseListener
{
    private static final long serialVersionUID = -2048380656307977019L;

    public PhaseId getPhaseId()
    {
        return PhaseId.RENDER_RESPONSE;
    }

    public void beforePhase(final PhaseEvent event)
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

                UIComponent component = FacesUtils.getInstance().getComponent(clientId);
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

    public void afterPhase(final PhaseEvent event)
    {}
}