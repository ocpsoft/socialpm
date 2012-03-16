package com.ocpsoft.socialpm.gwt.client.local.view;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.ocpsoft.socialpm.gwt.client.local.EventsFactory;
import com.ocpsoft.socialpm.gwt.client.local.ServiceFactory;
import com.ocpsoft.socialpm.gwt.client.local.view.component.HeroPanel;

@ApplicationScoped
public class NewProjectViewImpl extends FixedLayoutView implements NewProjectView
{
   @Inject
   private ServiceFactory serviceFactory;

   @Inject
   private EventsFactory eventFactory;

   final TextBox name = new TextBox();
   final TextBox slug = new TextBox();
   private final Anchor create = new Anchor("Next »");

   private Presenter presenter;

   public NewProjectViewImpl()
   {
      super();
   }

   @Override
   public void setup()
   {
      name.getElement().setAttribute("placeholder", "Project name...");

      HeroPanel hero = new HeroPanel();
      hero.setHeading("Start a new Project");
      hero.setContent("What do you call your project?");

      HorizontalPanel panel = new HorizontalPanel();
      panel.add(name);
      hero.getUnder().add(panel);

      create.addStyleName("btn btn-primary btn-large");
      hero.addAction(create);
      content.add(hero);
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

}
