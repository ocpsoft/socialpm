package com.ocpsoft.socialpm.gwt.client.local.view;

import com.google.gwt.place.shared.Place;



public interface NewProjectView extends FixedLayout
{
   public interface Presenter
   {
      void goTo(Place place);

      void createProject(String text);

      void verifyProject(String text);
   }
   
   Presenter getPresenter();
   
   void setPresenter(Presenter presenter);

   void focusProjectName();

   void setSubmitEnabled(boolean b);

   void clearInputs();
}
