/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.ocpsoft.socialpm.domain.project;

import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import com.ocpsoft.socialpm.domain.PersistenceUtil;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
@Stateful
@ConversationScoped
public class ProjectService extends PersistenceUtil
{
   private static final long serialVersionUID = 1403645951285144409L;

   @PersistenceContext(type = PersistenceContextType.EXTENDED)
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public Project create(final Project p)
   {
      save(p);
      return p;
   }

   public Project findByName(final String name)
   {
      return findUniqueByNamedQuery("project.byName", name);
   }

   public Project findBySlug(final String slug)
   {
      return findUniqueByNamedQuery("project.bySlug", slug);
   }

   public List<Project> findAll()
   {
      return findAll(Project.class);
   }

}
