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

import java.math.BigInteger;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.ocpsoft.socialpm.model.project.Feature;
import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.project.story.Story;
import com.ocpsoft.socialpm.services.PersistenceUtil;

@TransactionAttribute
public class FeatureService extends PersistenceUtil
{
   private static final long serialVersionUID = -3555718237042388593L;

   @Override
   public void setEntityManager(final EntityManager em)
   {
      this.em = em;
   }

   public void removeFeature(final Feature from, final Feature to)
   {
      Project project = from.getProject();

      List<Story> stories = from.getStories();
      for (Story s : stories)
      {
         s.getFeatures().remove(from);
         s.getFeatures().add(to);
      }
      save(project);

      from.getProject().getFeatures().remove(from);
      delete(from);
   }

   public Feature findByProjectAndNumber(final Project project, final int featureNumber)
   {
      return findUniqueByNamedQuery("Feature.byProjectAndNumber", project, featureNumber);
   }

   public int getFeatureNumber(final Feature created)
   {
      Query query = em.createNativeQuery(
               "(SELECT count(f.id) + 1 FROM features f WHERE f.project_id = :pid AND f.id < :fid)");
      query.setParameter("pid", created.getProject().getId());
      query.setParameter("fid", created.getId());
      return ((BigInteger) query.getSingleResult()).intValue();
   }

   public Feature create(final Project p, final Feature f)
   {
      f.setProject(p);
      p.getFeatures().add(f);

      super.create(f);
      return f;
   }

   public void save(final Feature f)
   {
      super.save(f);
   }

}
