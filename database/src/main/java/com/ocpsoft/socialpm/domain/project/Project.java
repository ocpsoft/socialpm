package com.ocpsoft.socialpm.domain.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.ocpsoft.socialpm.domain.PersistentObject;

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
}
