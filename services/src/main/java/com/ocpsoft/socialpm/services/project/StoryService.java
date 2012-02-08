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
package com.ocpsoft.socialpm.services.project;

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

import java.math.BigInteger;

import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.ocpsoft.socialpm.model.project.Points;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.project.iteration.Iteration;
import com.ocpsoft.socialpm.model.project.story.Story;
import com.ocpsoft.socialpm.model.project.story.ValidationCriteria;
import com.ocpsoft.socialpm.model.user.Profile;
import com.ocpsoft.socialpm.services.PersistenceUtil;
import com.ocpsoft.socialpm.util.Dates;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public class StoryService extends PersistenceUtil
{
   private static final long serialVersionUID = 1L;

   @Override
   public void setEntityManager(final EntityManager em)
   {
      this.em = em;
   }

   @TransactionAttribute
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

      Iteration iteration = p.getDefaultIteration();
      if (s.getIteration() == null)
      {
         s.setIteration(iteration);
         iteration.getStories().add(s);
      }

      super.create(s);
      return s;
   }

   public Story findByProjectAndNumber(final Project p, final int storyNumber) throws NoResultException
   {
      Story s = findUniqueByNamedQuery("Story.byProjectAndNumber", p, storyNumber);
      return s;
   }

   public Story findById(final Long id) throws NoResultException
   {
      return em.find(Story.class, id);
   }

   @TransactionAttribute
   public Story save(final Story story)
   {
      super.save(story);
      return story;
   }

   @TransactionAttribute
   public void refresh(final Story story)
   {
      super.refresh(story);
   }

   public int getStoryNumber(final Story created)
   {
      Query query = em.createNativeQuery(
               "(SELECT count(st.id) + 1 FROM stories st WHERE st.project_id = :pid AND st.id < :sid)");
      query.setParameter("pid", created.getProject().getId());
      query.setParameter("sid", created.getId());
      return ((BigInteger) query.getSingleResult()).intValue();
   }

   @TransactionAttribute
   public void closeStory(final Story story, final Profile profile)
   {
      story.setClosedBy(profile);
      story.setClosedOn(Dates.now());
      save(story);
   }

   @TransactionAttribute
   public void openStory(final Story story, final Profile profile)
   {
      story.setClosedBy(null);
      story.setClosedOn(null);
      save(story);
   }
}
