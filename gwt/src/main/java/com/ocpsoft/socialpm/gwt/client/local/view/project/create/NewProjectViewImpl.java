package com.ocpsoft.socialpm.gwt.client.local.view.project.create;

import javax.enterprise.context.ApplicationScoped;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.ocpsoft.socialpm.gwt.client.local.util.QueryableTimer;
import com.ocpsoft.socialpm.gwt.client.local.view.FixedLayoutView;
import com.ocpsoft.socialpm.gwt.client.local.view.component.HeroPanel;

@ApplicationScoped
public class NewProjectViewImpl extends FixedLayoutView implements NewProjectView
{
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

      FormPanel form = new FormPanel();
      HeroPanel hero = new HeroPanel();
      hero.setHeading("Start a new Project");
      hero.setContent("What do you call your project?");

      HorizontalPanel panel = new HorizontalPanel();
      panel.add(name);
      hero.getUnder().add(panel);

      create.addStyleName("btn btn-primary btn-large");
      hero.addAction(create);
      form.add(hero);
      content.add(form);
      
      setupInputs();
   }

   private void setupInputs()
   {
      final QueryableTimer t = new QueryableTimer() {

         String last = null;

         @Override
         public void performTask()
         {
            String current = name.getText();
            if ((last == null && current != null) || (last != null && current != null && !last.equals(current)))
            {
               last = current;
               presenter.verifyProject(current);
            }
         }
      };

      name.addKeyUpHandler(new KeyUpHandler() {
         @Override
         public void onKeyUp(KeyUpEvent event)
         {
            if (!t.isRunning())
            {
               t.scheduleRepeating(500);
            }
         }
      });

      name.addKeyPressHandler(new KeyPressHandler() {
         @Override
         public void onKeyPress(KeyPressEvent event)
         {
            if (KeyCodes.KEY_ENTER == event.getCharCode())
            {
               event.preventDefault();
               t.cancel();
               presenter.createProject(name.getText());
            }
         }
      });

      create.addClickHandler(new ClickHandler() {
         @Override
         public void onClick(ClickEvent event)
         {
            event.preventDefault();
            t.cancel();
            presenter.createProject(name.getText());
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
   public void focusProjectName()
   {
      name.setFocus(true);
   }
   
   @Override
   public void setSubmitEnabled(boolean enabled)
   {
      create.setEnabled(enabled);
   }
   
   @Override
   public void clearInputs()
   {
      name.setText("");
   }

}
