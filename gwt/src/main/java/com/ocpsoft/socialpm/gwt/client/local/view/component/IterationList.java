package com.ocpsoft.socialpm.gwt.client.local.view.component;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.model.feed.IterationCreated;
import com.ocpsoft.socialpm.model.feed.IterationEvent;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.project.iteration.Iteration;

@Dependent
public class IterationList extends Composite
{
   interface IterationListBinder extends UiBinder<Widget, IterationList>
   {
   }

   private static IterationListBinder binder = GWT.create(IterationListBinder.class);

   @UiField
   UnorderedList list;

   @UiField
   SpanElement iterationCount;

   @UiField
   NavLink manageIterations;

   private Project project;

   private List<Iteration> iterations;

   public IterationList()
   {
      initWidget(binder.createAndBindUi(this));
      manageIterations.setTargetHistoryToken(HistoryConstants.NEW_PROJECT());
   }

   public void setIterations(List<Iteration> iterations)
   {
      list.clear();
      this.iterations = new ArrayList<Iteration>();
      for (Iteration iter : iterations) {
         addIteration(iter);
      }
   }

   public List<Iteration> getIterations()
   {
      return iterations;
   }

   public void handleIterationCreated(@Observes IterationEvent event)
   {
      if (event instanceof IterationCreated)
      {
         if (this.project != null && this.project.equals(event.getProject()))
         {
            addIteration(event.getIteration());
         }
      }
   }

   private void addIteration(Iteration iter)
   {
      Div row = new Div();
      row.setStyleName("row");

      Div left = new Div();
      left.setStyleName("span1 cols");
      Div right = new Div();
      right.setStyleName("span4 cols");

      row.add(left);
      row.add(right);

      iterations.add(iter);

      IterationLink link = new IterationLink(iter);
      link.setWidth("100%");
      link.addStyleName("label " + iter.getStatus().name());
      link.addStyleDependentName("clickable");
      left.add(link);

      ProgressBar bar = new ProgressBar();
      right.add(bar);

      list.add(row);

      int taskHoursCommitment = iter.getTaskHoursCommitment();
      int percent = 0;
      if (taskHoursCommitment == -1)
         bar.setPercentComplete(8);
      else
      {
         percent = (int) ((taskHoursCommitment - iter.getTaskHoursRemain())
                  / (taskHoursCommitment + 0.0));
         bar.setPercentComplete(percent <= 5 ? 5 : percent);
      }

      iterationCount.setInnerText(String.valueOf(iterations.size()));

      Div progress = new Div(percent + "%");
      progress.addStyleName("percent pull-right");
      bar.setLabel(progress);
   }

   public void setProject(Project project)
   {
      this.project = project;
   }

   public NavLink getManageIterationsLink()
   {
      return manageIterations;
   }
}
