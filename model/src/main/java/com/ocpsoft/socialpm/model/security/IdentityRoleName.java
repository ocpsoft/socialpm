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

import static org.jboss.seam.security.annotations.management.EntityType.IDENTITY_ROLE_NAME;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.jboss.seam.security.annotations.management.IdentityEntity;

@Entity @IdentityEntity(IDENTITY_ROLE_NAME)
@Table(name = "identity_roles")
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