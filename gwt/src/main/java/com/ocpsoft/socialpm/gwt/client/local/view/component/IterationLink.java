package com.ocpsoft.socialpm.gwt.client.local.view.component;

import com.ocpsoft.socialpm.gwt.client.local.view.project.view.ProjectPlace;
import com.ocpsoft.socialpm.model.project.iteration.Iteration;

public class IterationLink extends NavLink
{
   public IterationLink()
   {}

   public IterationLink(Iteration iter)
   {
      super(iter.getNumber() + "");
      setIteration(iter);
   }

   public IterationLink setIteration(Iteration iteration)
   {
      // TODO this needs to link to the iteration page
      setTargetHistoryToken(new ProjectPlace.Tokenizer().getToken(new ProjectPlace(iteration.getProject().getOwner()
               .getUsername(),
               iteration.getProject().getSlug())));
      setText(iteration.getNumber() + "");
      return this;
   }
}
