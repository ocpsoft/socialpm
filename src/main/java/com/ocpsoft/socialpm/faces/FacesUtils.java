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

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.jboss.seam.international.status.Messages;

import com.ocpsoft.socialpm.exceptions.SiteRuntimeException;

public class FacesUtils implements Serializable
{
   private static final long serialVersionUID = -4522419864285064540L;

   @Inject
   FacesContext context;

   @Inject
   Messages messages;

   public static FacesUtils getInstance()
   {
      return new FacesUtils();
   }

   private FacesUtils()
   {
   }

   /*
    * Application Control Methods
    */
   public void doForward(final String url)
   {
      try
      {
         context.getExternalContext().dispatch(url);
      }
      catch (IOException e)
      {
         throw new SiteRuntimeException(e);
      }
   }

   /**
    * Send a client HTTP redirect and halt further Faces Life-cycle processing
    */
   public void doRedirect(final String url)
   {
      try
      {
         context.getExternalContext().redirect(url);
         context.responseComplete();
      }
      catch (IOException e)
      {
         throw new SiteRuntimeException(e);
      }
   }

   public void show404()
   {
      try
      {
         HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
         response.sendError(HttpServletResponse.SC_NOT_FOUND);
         context.responseComplete();
      }
      catch (IOException e)
      {
         throw new RuntimeException(e);
      }
   }

   /*
    * PrettyFaces Utility Methods
    */
   /**
    * Convert a PrettyFaces action name, e.g.: "homeAction" into a PrettyFaces enabled navigation action, e.g.:
    * "pretty:homeAction"
    */
   public String beautify(final String action)
   {
      return "pretty:" + action;
   }

   /*
    * Application Helper Methods
    */
   public String getContextRoot()
   {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      return facesContext.getExternalContext().getRequestContextPath();
   }

   public UIComponent getComponent(final String clientId)
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

   private UIComponent findChildComponent(final FacesContext facesContext, final UIComponent component,
            final String clientId)
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

   /*
    * Faces Message Helper Methods
    */
   public void addInfoMessage(final String summary)
   {
      messages.info(summary);
   }

   public void addInfoMessage(final UIComponent component, final String summary)
   {
      messages.info(summary).targets(component.getClientId());
   }

   public void addWarningMessage(final String summary)
   {
      messages.warn(summary);
   }

   public void addErrorMessage(final String summary)
   {
      messages.error(summary);
   }

   public void addFatalMessage(final String summary)
   {
      messages.fatal(summary);
   }

   /**
    * Returns true if the provided clientId has messages in the current FacesContext
    * 
    * @param clientId
    * @return
    */
   public boolean hasMessages(final String clientId)
   {
      Iterator<String> iterator = context.getClientIdsWithMessages();
      while (iterator.hasNext())
      {
         String id = iterator.next();
         if (id.equals(clientId))
         {
            return true;
         }
      }
      return false;
   }

}
