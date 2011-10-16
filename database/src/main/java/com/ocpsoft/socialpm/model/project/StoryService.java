package com.ocpsoft.socialpm.model.project;

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

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.seam.transaction.Transactional;

import com.ocpsoft.socialpm.domain.PersistenceUtil;
import com.ocpsoft.socialpm.domain.project.stories.Story;

/**
 * @author <a href="mailto:bleathem@gmail.com">Brian Leathem</a>
 * 
 */
@ConversationScoped
public class StoryService extends PersistenceUtil
{
   private static final long serialVersionUID = 1L;

   @Inject
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   @Transactional
   public Story create(final Story p)
   {
      super.create(p);
      em.flush();
      return p;
   }

   public Story findById(final Long id)
   {
      return em.find(Story.class, id);
   }
}
