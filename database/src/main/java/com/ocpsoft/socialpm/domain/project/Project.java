package com.ocpsoft.socialpm.domain.project;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import com.ocpsoft.socialpm.domain.PersistentObject;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * @author <a href="mailto:bleathem@gmail.com">Brian Leathem</a>
 * 
 */
@Entity
@NamedQueries({
         @NamedQuery(name = "project.byName", query = "from Project p where p.name = ?"),
         @NamedQuery(name = "project.bySlug", query = "from Project p where p.slug = ?")
})
public class Project extends PersistentObject<Project>
{
   private static final long serialVersionUID = -7146731072443151820L;

   @Size(min = 3, max = 10)
   @Column(length = 10, nullable = false, unique = true)
   private String slug;

   @Column(length = 64, nullable = false)
   private String name;

   @Column(length = 256)
   private String vision;

   @OneToMany(fetch = FetchType.EAGER, mappedBy = "project")
   private List<Story> stories = new ArrayList<Story>();

   public String getSlug()
   {
      return slug;
   }

   public void setSlug(final String slug)
   {
      this.slug = slug;
      if (this.slug != null)
         this.slug = this.slug.toUpperCase();
   }

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

   public void setStories(final List<Story> stories)
   {
      this.stories = stories;
   }
}
