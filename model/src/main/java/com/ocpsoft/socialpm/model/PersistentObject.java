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
package com.ocpsoft.socialpm.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@MappedSuperclass
public abstract class PersistentObject<E extends PersistentObject<E>> implements Serializable
{
   private static final long serialVersionUID = -1272280183658745494L;

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;

   @Version
   @Column(name = "version")
   private int version = 0;

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "lastUpdate")
   private Date lastUpdate;

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "createdOn", updatable = false, nullable = false)
   private Date createdOn;

   @PrePersist
   void prePersist()
   {
      createdOn = new Date();
   }

   @PreUpdate
   void preUpdate()
   {
      lastUpdate = new Date();
   }

   protected static boolean getBooleanValue(final Boolean value)
   {
      return Boolean.valueOf(String.valueOf(value));
   }

   public Long getId()
   {
      return id;
   }

   public boolean isPersistent()
   {
      return getId() != null;
   }

   @SuppressWarnings("unchecked")
   public E setId(final Long id)
   {
      if (this.id != null)
      {
         throw new IllegalStateException("Cannot alter immutable ID of persistent object with id: " + id);
      }
      this.id = id;
      return (E) this;
   }

   public int getVersion()
   {
      return version;
   }

   @SuppressWarnings("unused")
   private void setVersion(final int version)
   {
      this.version = version;
   }

   public Date getLastUpdate()
   {
      return lastUpdate;
   }

   @SuppressWarnings("unchecked")
   public E setLastUpdate(final Date lastUpdate)
   {
      this.lastUpdate = lastUpdate;
      return (E) this;
   }

   public Date getCreatedOn()
   {
      return createdOn;
   }

   @SuppressWarnings("unchecked")
   public E setCreatedOn(final Date createdOn)
   {
      this.createdOn = createdOn;
      return (E) this;
   }
}
