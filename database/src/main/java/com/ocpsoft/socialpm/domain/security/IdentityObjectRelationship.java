package com.ocpsoft.socialpm.domain.security;

import static org.jboss.seam.security.annotations.management.EntityType.IDENTITY_RELATIONSHIP;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.jboss.seam.security.annotations.management.IdentityEntity;
import org.jboss.seam.security.annotations.management.IdentityProperty;
import org.jboss.seam.security.annotations.management.PropertyType;

@Entity @IdentityEntity(IDENTITY_RELATIONSHIP)
public class IdentityObjectRelationship implements Serializable
{
   private static final long serialVersionUID = 4571679866526917176L;
   @Id
   @GeneratedValue
   private Long id;

   @IdentityProperty(PropertyType.NAME)
   private String name;

   @ManyToOne
   @IdentityProperty(PropertyType.TYPE)
   @JoinColumn(name = "RELATIONSHIP_TYPE_ID")
   private IdentityObjectRelationshipType relationshipType;

   @ManyToOne
   @IdentityProperty(PropertyType.RELATIONSHIP_FROM)
   @JoinColumn(name = "FROM_IDENTITY_ID")
   private IdentityObject from;

   @ManyToOne
   @IdentityProperty(PropertyType.RELATIONSHIP_TO)
   @JoinColumn(name = "TO_IDENTITY_ID")
   private IdentityObject to;

   public String getName()
   {
      return name;
   }

   public void setName(final String name)
   {
      this.name = name;
   }

   public IdentityObjectRelationshipType getRelationshipType()
   {
      return relationshipType;
   }

   public void setRelationshipType(final IdentityObjectRelationshipType relationshipType)
   {
      this.relationshipType = relationshipType;
   }

   public IdentityObject getFrom()
   {
      return from;
   }

   public void setFrom(final IdentityObject from)
   {
      this.from = from;
   }

   public IdentityObject getTo()
   {
      return to;
   }

   public void setTo(final IdentityObject to)
   {
      this.to = to;
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