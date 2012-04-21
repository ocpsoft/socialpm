package com.ocpsoft.socialpm.gwt.client.local.view.component;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ocpsoft.socialpm.gwt.client.local.view.events.DisplayEvent;
import com.ocpsoft.socialpm.gwt.client.local.view.events.DisplayHandler;
import com.ocpsoft.socialpm.gwt.client.local.view.events.HideEvent;
import com.ocpsoft.socialpm.gwt.client.local.view.events.OnHideHandler;

/**
 * A splash screen
 */
public class ModalDialog extends Composite
{
   interface ModalDialogBinder extends UiBinder<Widget, ModalDialog>
   {
   }

   private static ModalDialogBinder binder = GWT.create(ModalDialogBinder.class);

   @UiField
   HTMLPanel modal;

   @UiField
   Span header;

   @UiField
   HTMLPanel content;

   @UiField
   HTMLPanel footer;

   private final List<OnHideHandler> onHideHandlers = new ArrayList<OnHideHandler>();

   private final List<DisplayHandler> onDisplayHandlers = new ArrayList<DisplayHandler>();

   public ModalDialog()
   {
      initWidget(binder.createAndBindUi(this));
      this.setVisible(false);
      getWidget().getElement().setId(DOM.createUniqueId());
   }

   public String getId()
   {
      return getWidget().getElement().getId();
   }

   public ModalDialog addHeader(Widget w)
   {
      header.add(w);
      return this;
   }

   public ModalDialog addContent(Widget w)
   {
      content.add(w);
      return this;
   }

   public ModalDialog addFooter(Widget w)
   {
      footer.add(w);
      return this;
   }

   public void display()
   {
      activate(getId());
      display(getId());
   }

   public void hide()
   {
      hide(getId());
   }

   public ModalDialog addOnHideHandler(OnHideHandler handler)
   {
      onHideHandlers.add(handler);
      return this;
   }

   public ModalDialog addDisplayHandler(DisplayHandler handler)
   {
      onDisplayHandlers.add(handler);
      return this;
   }

   private void handleOnDisplay()
   {
      DisplayEvent event = new DisplayEvent(this);
      for (DisplayHandler handler : onDisplayHandlers) {
         handler.handleOnDisplay(event);
      }
   }

   private void handleOnHide()
   {
      HideEvent event = new HideEvent(this);
      for (OnHideHandler handler : onHideHandlers) {
         handler.handleOnHide(event);
      }
   }

   /*
    * Native Methods
    */
   private native void display(String id) /*-{
		$wnd.$('#' + id).modal('show')
	}-*/;

   private native void hide(String id) /*-{
      $wnd.$('#' + id).modal('hide')
   }-*/;

   private native void activate(String id) /*-{
      var modal = this;

      $wnd.$("#" + id).on('shown', function () {
			modal.@com.ocpsoft.socialpm.gwt.client.local.view.component.ModalDialog::handleOnDisplay()();
      });

		$wnd.$("#" + id).on('hidden', function () {
		   modal.@com.ocpsoft.socialpm.gwt.client.local.view.component.ModalDialog::handleOnHide()();
		});

   }-*/;
}
