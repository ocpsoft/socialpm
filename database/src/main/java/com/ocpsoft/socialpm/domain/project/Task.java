package com.ocpsoft.socialpm.domain.project;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ocpsoft.socialpm.domain.PersistentObject;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
@Entity
public class Task extends PersistentObject<Task>
{
   private static final long serialVersionUID = -8830409283399931943L;

   @ManyToOne
   private Story story;

   @Column(length = 512)
   private String text;

   @Temporal(TemporalType.TIMESTAMP)
   private Date completed;

   public String getText()
   {
      return text;
   }

   public void setText(final String text)
   {
      this.text = text;
   }

   public Story getStory()
   {
      return story;
   }

   public void setStory(final Story story)
   {
      this.story = story;
   }

   public Date getCompleted()
   {
      return completed;
   }

   public void setCompleted(final Date completed)
   {
      this.completed = completed;
   }

}
