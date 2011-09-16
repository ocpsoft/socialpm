package com.ocpsoft.socialpm.domain.security;

import static org.jboss.seam.security.annotations.management.EntityType.IDENTITY_ATTRIBUTE;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.jboss.seam.security.annotations.management.IdentityEntity;
import org.jboss.seam.security.annotations.management.IdentityProperty;
import org.jboss.seam.security.annotations.management.PropertyType;

@Entity @IdentityEntity(IDENTITY_ATTRIBUTE)
public class IdentityObjectAttribute implements Serializable
{
   private static final long serialVersionUID = -1351668083235705417L;
   @Id
   @GeneratedValue
   private Long id;

   @ManyToOne
   @JoinColumn(name = "IDENTITY_OBJECT_ID")
   private IdentityObject identityObject;

   @IdentityProperty(PropertyType.NAME)
   private String name;

   @IdentityProperty(PropertyType.VALUE)
   private String value;

   public IdentityObject getIdentityObject()
   {
      return identityObject;
   }

   public void setIdentityObject(final IdentityObject identityObject)
   {
      this.identityObject = identityObject;
   }

   public String getName()
   {
      return name;
   }

   public void setName(final String name)
   {
      this.name = name;
   }

   public String getValue()
   {
      return value;
   }

   public void setValue(final String value)
   {
      this.value = value;
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