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

import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;

import org.jboss.seam.faces.event.qualifier.Before;
import org.jboss.seam.faces.event.qualifier.RenderResponse;

/**
 * Sets style class <b>errorHighlight</b> on components that have validation errors. Sets the request scope
 * <b>#{focus}</b> variable to the first component with validation failure.
 */
@RequestScoped
public class InvalidFieldHighlightListener
{
   public void setFocus(@Observes @Before @RenderResponse PhaseEvent event)
   {
      final FacesContext facesContext = event.getFacesContext();

      facesContext.getViewRoot().visitTree(VisitContext.createVisitContext(facesContext), new VisitCallback() {
         boolean focused = false;

         @Override
         public VisitResult visit(VisitContext context, UIComponent target)
         {
            if (target instanceof UIInput)
            {
               if (!((UIInput) target).isValid())
               {
                  if (focused == false)
                  {
                     focused = true;
                     facesContext.getExternalContext().getRequestMap()
                              .put("focus", target.getId().replaceAll(":", "\\\\\\\\:"));
                  }

                  Map<String, Object> attributes = target.getAttributes();
                  String styleClass = (String) attributes.get("styleClass");
                  if ((styleClass == null) || "".equals(styleClass))
                  {
                     attributes.put("styleClass", "error");
                  }
                  else if (!styleClass.contains("error"))
                  {
                     attributes.put("styleClass", styleClass + " error");
                  }
               }

               else
               {
                  Map<String, Object> attributes = target.getAttributes();
                  String styleClass = (String) attributes.get("styleClass");
                  if (styleClass != null && styleClass.contains("error"))
                  {
                     styleClass = styleClass.replaceFirst("\\berror\\b", "");
                     attributes.put("styleClass", styleClass);
                  }
               }
            }
            return VisitResult.ACCEPT;
         }
      });

   }
}