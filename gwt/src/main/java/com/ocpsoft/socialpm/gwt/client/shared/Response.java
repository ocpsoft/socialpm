package com.ocpsoft.socialpm.gwt.client.shared;

import org.jboss.errai.common.client.api.annotations.Portable;

/**
 * A marshallable bean that carries the response event from the server back to the client.
 */
@Portable
public class Response
{
   private int id;
   private String message;

   public Response()
   {}

   public Response(String message)
   {
      this.message = message;
   }

   public int getId()
   {
      return id;
   }

   public void setId(int id)
   {
      this.id = id;
   }

   public String getMessage()
   {
      return message;
   }

   public void setMessage(String message)
   {
      this.message = message;
   }
}