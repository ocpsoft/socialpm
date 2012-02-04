/**
 * This file is part of OCPsoft SocialPM: Agile Project Management Tools (SocialPM)
 *
 * Copyright (c)2011 Lincoln Baxter, III <lincoln@ocpsoft.com> (OCPsoft)
 * Copyright (c)2011 OCPsoft.com (http://ocpsoft.com)
 * 
 * If you are developing and distributing open source applications under
 * the GNU General Public License (GPL), then you are free to re-distribute SocialPM
 * under the terms of the GPL, as follows:
 *
 * SocialPM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SocialPM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SocialPM.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * For individuals or entities who wish to use SocialPM privately, or
 * internally, the following terms do not apply:
 * 
 * For OEMs, ISVs, and VARs who wish to distribute SocialPM with their
 * products, or host their product online, OCPsoft provides flexible
 * OEM commercial licenses.
 * 
 * Optionally, Customers may choose a Commercial License. For additional
 * details, contact an OCPsoft representative (sales@ocpsoft.com)
 */
package com.ocpsoft.socialpm.model.security;

import static org.jboss.seam.security.annotations.management.EntityType.IDENTITY_RELATIONSHIP;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.jboss.seam.security.annotations.management.IdentityEntity;
import org.jboss.seam.security.annotations.management.IdentityProperty;
import org.jboss.seam.security.annotations.management.PropertyType;

@Entity @IdentityEntity(IDENTITY_RELATIONSHIP)
@Table(name = "identity_object_relationships")
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