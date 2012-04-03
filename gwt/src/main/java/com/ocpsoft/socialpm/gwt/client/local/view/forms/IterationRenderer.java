package com.ocpsoft.socialpm.gwt.client.local.view.forms;

import com.google.gwt.text.shared.AbstractRenderer;
import com.ocpsoft.socialpm.model.project.iteration.Iteration;

public class IterationRenderer extends AbstractRenderer<Iteration>
{
   @Override
   public String render(Iteration object)
   {
      if (object == null)
         return "";
      return object.getNumber() + " - " + object.getTitle();
   }

}
