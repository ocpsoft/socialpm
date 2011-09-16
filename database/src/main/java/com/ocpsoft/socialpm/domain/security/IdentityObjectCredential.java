package com.ocpsoft.socialpm.domain.security;

import static org.jboss.seam.security.annotations.management.EntityType.IDENTITY_CREDENTIAL;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.jboss.seam.security.annotations.management.IdentityEntity;
import org.jboss.seam.security.annotations.management.IdentityProperty;
import org.jboss.seam.security.annotations.management.PropertyType;

@Entity @IdentityEntity(IDENTITY_CREDENTIAL)
public class IdentityObjectCredential implements Serializable
{
   private static final long serialVersionUID = -6949125764384113657L;

   @Id
   @GeneratedValue
   private Long id;

   @ManyToOne
   @JoinColumn(name = "CREDENTIAL_IDENTITY_OBJECT_ID")
   private IdentityObject identityObject;

   @ManyToOne
   @IdentityProperty(PropertyType.TYPE)
   @JoinColumn(name = "CREDENTIAL_TYPE_ID")
   private IdentityObjectCredentialType type;

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

   public IdentityObjectCredentialType getType()
   {
      return type;
   }

   public void setType(final IdentityObjectCredentialType type)
   {
      this.type = type;
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