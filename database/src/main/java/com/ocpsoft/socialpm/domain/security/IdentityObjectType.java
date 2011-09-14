package com.ocpsoft.socialpm.domain.security;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.jboss.seam.security.annotations.management.IdentityProperty;
import org.jboss.seam.security.annotations.management.PropertyType;

@Entity
public class IdentityObjectType implements Serializable
{
   private static final long serialVersionUID = 8194168713804082903L;

   @Id
   @GeneratedValue
   private Long id;

   @IdentityProperty(PropertyType.NAME)
   private String name;

   public String getName()
   {
      return name;
   }

   public void setName(final String name)
   {
      this.name = name;
   }

   public Long getId()
   {
      return id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }
}