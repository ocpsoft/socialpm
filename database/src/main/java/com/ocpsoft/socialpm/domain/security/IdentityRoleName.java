package com.ocpsoft.socialpm.domain.security;

import static org.jboss.seam.security.annotations.management.EntityType.IDENTITY_ROLE_NAME;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.jboss.seam.security.annotations.management.IdentityEntity;

@Entity @IdentityEntity(IDENTITY_ROLE_NAME)
public class IdentityRoleName implements Serializable
{
   private static final long serialVersionUID = 8775236263787825703L;

   private Long id;
   private String name;

   @Id
   @GeneratedValue
   public Long getId()
   {
      return id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   public String getName()
   {
      return name;
   }

   public void setName(final String name)
   {
      this.name = name;
   }
}