/*
 * Copyright 2010 - Lincoln Baxter, III (lincoln@ocpsoft.com) - Licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 - Unless required by applicable
 * law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.ocpsoft.socialpm.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PersistenceException;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@MappedSuperclass
public abstract class PersistentObject<E extends PersistentObject<?>> implements Serializable
{
   private static final long serialVersionUID = -1272280183658745494L;

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private long id = 0;

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
   public void prePersist()
   {
      createdOn = new Date();
   }

   @PreUpdate
   public void preUpdate()
   {
      lastUpdate = new Date();
   }

   protected static boolean getBooleanValue(final Boolean value)
   {
      return Boolean.valueOf(String.valueOf(value));
   }

   public long getId()
   {
      return id;
   }

   public boolean isPersistent()
   {
      return getId() > 0;
   }

   @SuppressWarnings("unchecked")
   public E setId(final long id)
   {
      if (this.id != 0)
      {
         throw new PersistenceException("Cannot alter immutable ID of persistent object with id: " + id);
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
