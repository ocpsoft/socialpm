package com.ocpsoft.socialpm.gwt.client.local.view;

import com.google.gwt.place.shared.Place;



public interface NewProjectView extends FixedLayout
{
   public interface Presenter
   {
      void goTo(Place place);
   }
   
   Presenter getPresenter();
   
   void setPresenter(Presenter presenter);
}
