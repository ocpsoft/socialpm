package com.ocpsoft.socialpm.domain.project;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.ocpsoft.socialpm.domain.PersistentObject;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * @author <a href="mailto:bleathem@gmail.com">Brian Leathem</a>
 * 
 */
@Entity
@NamedQueries({
         @NamedQuery(name = "project.byName", query = "from Project p where p.name = ?")
})
public class Project extends PersistentObject<Project>
{
   private static final long serialVersionUID = -7146731072443151820L;

   @Column(nullable = false)
   private String name;

   private String vision;
   
   @OneToMany(fetch=FetchType.EAGER, mappedBy="project")
   private List<Story> stories;

   public String getName()
   {
      return name;
   }

   public void setName(final String name)
   {
      this.name = name;
   }

   public String getVision()
   {
      return vision;
   }

   public void setVision(final String vision)
   {
      this.vision = vision;
   }

   public List<Story> getStories() 
   {
      return stories;
   }
    
   public void setStories(List<Story> stories) {
      this.stories = stories;
   }
}
