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

import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;

import com.ocpsoft.socialpm.domain.PersistenceUtil;
import com.ocpsoft.socialpm.domain.project.Points;
import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.domain.project.stories.Story;
import com.ocpsoft.socialpm.domain.project.stories.ValidationCriteria;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
@TransactionAttribute
public class StoryService extends PersistenceUtil
{
   private static final long serialVersionUID = 1L;

   @Override
   public void setEntityManager(EntityManager em)
   {
      this.em = em;
   }

   public Story create(final Project p, final Story s)
   {
      s.setProject(p);
      p.getStories().add(s);

      for (ValidationCriteria v : s.getValidations())
      {
         v.setStory(s);
         s.getValidations().add(v);
      }

      if (s.getStoryPoints() == null)
      {
         s.setStoryPoints(Points.NOT_POINTED);
      }

      if (s.getBusinessValue() == null)
      {
         s.setBusinessValue(Points.NOT_POINTED);
      }

      if (s.getIteration() == null)
      {
         s.setIteration(p.getDefaultIteration());
      }

      super.create(s);
      return s;
   }

   public Story findByProjectAndNumber(final Project p, final int storyNumber)
   {
      Story s = findUniqueByNamedQuery("Story.byProjectAndNumber", p, storyNumber);
      return s;
   }

   public Story findById(final Long id)
   {
      return em.find(Story.class, id);
   }
}
