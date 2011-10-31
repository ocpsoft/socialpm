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
package com.ocpsoft.socialpm.model.project;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;

import com.ocpsoft.socialpm.domain.PersistenceUtil;
import com.ocpsoft.socialpm.domain.feed.ProjectCreated;
import com.ocpsoft.socialpm.domain.project.Feature;
import com.ocpsoft.socialpm.domain.project.Project;
import com.ocpsoft.socialpm.domain.project.iteration.Iteration;
import com.ocpsoft.socialpm.domain.user.Profile;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public class ProjectService extends PersistenceUtil
{
   private static final long serialVersionUID = 1403645951285144409L;

   @Override
   public void setEntityManager(final EntityManager em)
   {
      this.em = em;
   }

   @TransactionAttribute
   public Project create(final Profile owner, final Project p)
   {
      p.setOwner(owner);
      super.create(p);

      Iteration unassigned = new Iteration();
      unassigned.setProject(p);
      unassigned.setTitle("Unassigned");
      p.getIterations().add(unassigned);

      Feature bugFixes = new Feature();
      bugFixes.setName("Bug Fixes");
      bugFixes.setProject(p);
      p.getFeatures().add(bugFixes);
      bugFixes.setProject(p);

      Feature enhancements = new Feature();
      enhancements.setName("Enhancements");
      enhancements.setProject(p);
      p.getFeatures().add(enhancements);
      enhancements.setProject(p);

      Feature unclassified = new Feature();
      unclassified.setName("Unclassified");
      unclassified.setProject(p);
      p.getFeatures().add(unclassified);
      unclassified.setProject(p);

      super.create(p);

      // p.getMemberships().add(new Membership(p, owner, MemberRole.OWNER));

      super.create(new ProjectCreated(owner, p));
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

   public long getProjectCount()
   {
      return count(Project.class);
   }

}
