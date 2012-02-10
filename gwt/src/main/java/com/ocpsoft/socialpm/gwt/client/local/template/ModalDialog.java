package com.ocpsoft.socialpm.gwt.client.local.template;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

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

   public ModalDialog()
   {
      initWidget(binder.createAndBindUi(this));
      getWidget().getElement().setId(DOM.createUniqueId());
      activate(getId());
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
      display(getId());
   }

   /*
    * Native Methods
    */
   private native void display(String id) /*-{
		$wnd.$('#' + id).modal('show')
   }-*/;

   private native void activate(String id) /*-{
		$wnd.$('#' + id).modal({
			keyboard : false
		})
   }-*/;

}
