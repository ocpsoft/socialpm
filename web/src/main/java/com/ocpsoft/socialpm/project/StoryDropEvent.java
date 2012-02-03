package com.ocpsoft.socialpm.project;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.richfaces.event.DropEvent;
import org.richfaces.event.DropListener;

/**
 * @author <a href="http://community.jboss.org/people/bleathem">Brian Leathem</a>
 */
@Named
@RequestScoped
public class StoryDropEvent
{
   public void processDrop(DropEvent event) {
       System.out.println("We dropped a big one!");
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Item dropped"));
   }
}
