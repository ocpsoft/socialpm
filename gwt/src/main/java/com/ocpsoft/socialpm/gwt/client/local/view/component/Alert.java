package com.ocpsoft.socialpm.gwt.client.local.view.component;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class Alert extends Composite
{
   public enum AlertType
   {
      SUCCESS("alert-success"),
      INFO("alert-info"),
      WARN(""),
      ERROR("alert-error");

      private String style;

      AlertType(String style)
      {
         this.style = style;
      }

      public String getStyle()
      {
         return style;
      }
   }

   interface AlertBinder extends UiBinder<Widget, Alert>
   {
   }

   private static AlertBinder binder = GWT.create(AlertBinder.class);

   @UiField
   Div alert;

   @UiField
   Anchor close;

   @UiField
   Span contents;

   /*
    * Static builders
    */

   public static Alert success()
   {
      return new Alert(AlertType.SUCCESS);
   }

   public static Alert info()
   {
      return new Alert(AlertType.INFO);
   }

   public static Alert warn()
   {
      return new Alert(AlertType.WARN);
   }

   public static Alert error()
   {
      return new Alert(AlertType.ERROR);
   }

   /*
    * Constructors
    */
   public Alert()
   {
      initWidget(binder.createAndBindUi(this));
      close.setInnerHTML("×");
      close.setAttribute("data-dismiss", "alert");
      setType(AlertType.WARN);
   }

   public Alert(AlertType type)
   {
      this();
      setType(type);
   }

   public Alert(boolean closable)
   {
      this();
      setCloseable(closable);
   }

   public Alert(AlertType type, boolean closable)
   {
      this(closable);
      setType(type);
   }

   /*
    * Methods
    */

   public Alert setCloseable(boolean closable)
   {
      close.setVisible(closable);
      return this;
   }

   public void setType(AlertType type)
   {
      this.setStyleName("alert " + type.getStyle());
   }

   public Alert setInnerHTML(String html)
   {
      contents.getElement().setInnerHTML(html);
      return this;
   }
}
