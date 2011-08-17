package com.ocpsoft.socialpm.domain.project;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.ocpsoft.socialpm.domain.PersistentObject;

/**
 * @author <a href="mailto:bleathem@gmail.com">Brian Leathem</a>
 * 
 */
@Entity
public class Story extends PersistentObject<Story>
{
   private static final long serialVersionUID = -8830409283399931943L;

   @ManyToOne
   @JoinColumn(name = "project")
   private Project project;

   private String text;

   @OneToMany(fetch = FetchType.LAZY, mappedBy = "story")
   private Set<Task> tasks = new LinkedHashSet<Task>();

   public Project getProject()
   {
      return project;
   }

   public void setProject(final Project project)
   {
      this.project = project;
   }

   public String getText()
   {
      return text;
   }

   public void setText(final String text)
   {
      this.text = text;
   }

   public Set<Task> getTasks()
   {
      return tasks;
   }

   public void setTasks(final Set<Task> tasks)
   {
      this.tasks = tasks;
   }

}
