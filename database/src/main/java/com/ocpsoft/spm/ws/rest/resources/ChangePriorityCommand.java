/**
 * This file is part of SocialPM: Agile Project Management Tools (SocialPM) 
 *
 * Copyright (c)2010 Lincoln Baxter, III <lincoln@ocpsoft.com> (OcpSoft)
 * 
 * If you are developing and distributing open source applications under 
 * the GPL Licence, then you are free to use SocialPM under the GPL 
 * License:
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
 * For OEMs, ISVs, and VARs who distribute SocialPM with their products, 
 * host their product online, OcpSoft provides flexible OEM commercial 
 * Licences. 
 * 
 * Optionally, customers may choose a Commercial License. For additional 
 * details, contact OcpSoft (http://ocpsoft.com)
 */

package com.ocpsoft.spm.ws.rest.resources;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.ocpsoft.socialpm.domain.PersistenceUtil;
import com.ocpsoft.socialpm.domain.project.stories.Story;

public class ChangePriorityCommand extends PersistenceUtil
{
   private static final long serialVersionUID = -3165198869580133953L;

   private Story story;
   private Integer newPriority;

   @Inject
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public ChangePriorityCommand(final Story story, final Integer newPriority)
   {
      super();
      this.story = story;
      this.newPriority = newPriority;
   }

   public Story perform()
   {
      // TODO lb3 This should be chain of responsibility... ART
      Story temp = story;
      Integer currentMax = getMaxPriority(temp);
      Integer newValue = newPriority;
      Integer oldValue = temp.getPriority();

      // ensure integer values >= 1
      if ((newValue != null) && (newValue <= 0))
      {
         newValue = 1;
      }

      // do nothing case
      if ((newValue == oldValue) || ((newValue != null) && newValue.equals(oldValue)))
      {
         return null;
      }

      // set first value case
      if (currentMax == null)
      {
         newValue = 1;
         temp.setPriority(newValue);
         save(temp);
         return null;
      }

      // set null to any greater value case
      if ((newValue != null) && (currentMax.compareTo(newValue) < 0) && (oldValue == null))
      {
         newValue = currentMax + 1;
         temp.setPriority(newValue);
         save(temp);
         return null;
      }

      Query query;

      // shift up case
      if ((oldValue != null) && (newValue != null) && (oldValue > newValue))
      {
         // shift >= newpriority && < oldpriority ++
         query = getEntityManager().createQuery("UPDATE Story s SET s.priority = s.priority + 1 "
                  + "WHERE s.project = :project AND s.priority >= :newPriority "
                  + "AND s.priority < :oldPriority");
         query.setParameter("newPriority", newValue);
         query.setParameter("oldPriority", oldValue);
         query.setParameter("project", temp.getProject());
         query.executeUpdate();

         temp.setPriority(newValue);
         save(temp);
         return null;
      }

      if ((oldValue == null) || ((newValue != null) && (oldValue > newValue)))
      {
         // shift >= newpriority ++
         query = getEntityManager().createQuery("UPDATE Story s SET s.priority = s.priority + 1 "
                  + "WHERE s.project = :project AND s.priority >= :newPriority");
         query.setParameter("newPriority", newValue);
         query.setParameter("project", temp.getProject());
         query.executeUpdate();

         temp.setPriority(newValue);
         save(temp);
         return null;
      }

      // set shift down case
      if (newValue == null)
      {
         // shift <= newpriority && > oldpriority--
         query = getEntityManager().createQuery("UPDATE Story s SET s.priority = s.priority - 1 "
                  + "WHERE s.project = :project AND s.priority > :oldPriority");
         query.setParameter("oldPriority", oldValue);
         query.setParameter("project", temp.getProject());
         query.executeUpdate();

         temp.setPriority(newValue);
         save(temp);
         return null;
      }

      if (oldValue < newValue)
      {
         // shift <= newpriority && > oldpriority--
         query = getEntityManager()
                  .createQuery("UPDATE Story s SET s.priority = s.priority - 1 "
                           + "WHERE s.project = :project AND s.priority <= :newPriority AND s.priority > :oldPriority");
         query.setParameter("newPriority", newValue);
         query.setParameter("oldPriority", oldValue);
         query.setParameter("project", temp.getProject());
         query.executeUpdate();

         temp.setPriority(newValue);
         save(temp);
         return null;
      }

      throw new IllegalStateException("Invalid priority change scenario.. missed case?");
   }

   private Integer getMaxPriority(final Story story)
   {
      Query query = getEntityManager().createQuery("SELECT MAX(s.priority) FROM Story s WHERE s.project = :project");
      query.setParameter("project", story.getProject());
      Integer uniqueResult = (Integer) query.getSingleResult();

      if (uniqueResult == null)
      {
         return null;
      }
      return uniqueResult.intValue();
   }

   public Story getStory()
   {
      return story;
   }

   public void setStory(final Story story)
   {
      this.story = story;
   }

   public Integer getNewPriority()
   {
      return newPriority;
   }

   public void setNewPriority(final Integer newPriority)
   {
      this.newPriority = newPriority;
   }
}
