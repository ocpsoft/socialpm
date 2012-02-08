package com.ocpsoft.socialpm.gwt.client.shared;

import org.jboss.errai.common.client.api.annotations.Portable;

/**
 * A marshallable bean that's used as the event object when sending user-entered text from the client to the server.
 * 
 * @author Jonathan Fuerth <jfuerth@gmail.com>
 */
@Portable
public class HelloMessage
{
   private int id;
   private String message;

   public HelloMessage()
   {}

   public HelloMessage(String message)
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