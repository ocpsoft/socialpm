package com.ocpsoft.socialpm.gwt.client.local.view.project.create;

import com.ocpsoft.socialpm.gwt.client.local.view.FixedLayout;
import com.ocpsoft.socialpm.gwt.client.local.view.FixedLayout.FixedPresenter;




public interface NewProjectView extends FixedLayout
{
   public interface Presenter extends FixedPresenter
   {
      void createProject(String text);

      void verifyProject(String text);
   }

   Presenter getPresenter();

   void setPresenter(Presenter presenter);

   void focusProjectName();

   void setSubmitEnabled(boolean b);

   void clearInputs();
}
