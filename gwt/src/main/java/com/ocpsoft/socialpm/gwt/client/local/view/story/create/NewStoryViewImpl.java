package com.ocpsoft.socialpm.gwt.client.local.view.story.create;

import javax.enterprise.context.ApplicationScoped;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.ocpsoft.socialpm.gwt.client.local.view.FixedLayoutView;
import com.ocpsoft.socialpm.gwt.client.local.view.component.HeroPanel;
import com.ocpsoft.socialpm.gwt.client.local.view.forms.NewStoryForm;
import com.ocpsoft.socialpm.gwt.client.local.view.project.view.ProjectPlace;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.project.story.Story;

@ApplicationScoped
public class NewStoryViewImpl extends FixedLayoutView implements NewStoryView
{
   NewStoryForm form = new NewStoryForm();

   private Presenter presenter;
   private Project project;

   public NewStoryViewImpl()
   {
      super();
   }

   @Override
   public void setProject(Project project)
   {
      this.project = project;
      form.setProject(project);
   }

   @Override
   public void setup()
   {
      HeroPanel hero = new HeroPanel();
      hero.setHeading("Add a new Story");
      hero.getUnder().add(form);

      content.add(hero);

      setupInputs();
   }

   private void setupInputs()
   {

      form.getCreateButton().addClickHandler(new ClickHandler() {

         Story story = new Story();

         @Override
         public void onClick(ClickEvent event)
         {
            event.preventDefault();

            story.setRole(form.getRole().getText());
            story.setObjective(form.getObjective().getText());
            story.setResult(form.getResult().getText());
            story.setIteration(form.getIteration().getValue());

            presenter.createStory(project, story);
         }
      });

      form.getCancelButton().addClickHandler(new ClickHandler() {

         @Override
         public void onClick(ClickEvent event)
         {
            presenter.goTo(new ProjectPlace(project.getOwner().getUsername(), project.getSlug()));
         }
      });
   }

   @Override
   public Presenter getPresenter()
   {
      return presenter;
   }

   @Override
   public void setPresenter(Presenter presenter)
   {
      this.presenter = presenter;
   }

   @Override
   public void clearInputs()
   {
      // TODO
   }

}
