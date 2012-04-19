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

package com.ocpsoft.socialpm.model.project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.jboss.errai.common.client.api.annotations.Portable;

import com.ocpsoft.socialpm.model.DeletableObject;
import com.ocpsoft.socialpm.model.project.iteration.Iteration;
import com.ocpsoft.socialpm.model.project.story.Story;
import com.ocpsoft.socialpm.model.user.Profile;

@Portable
@Entity
@Table(name = "projects", uniqueConstraints = { @UniqueConstraint(columnNames = { "owner_id", "slug" }) })
@NamedQueries({
         @NamedQuery(name = "Project.byProfileAndSlug", query = "FROM Project WHERE owner.username = ? AND slug = ?"),
         @NamedQuery(name = "Project.byProfile", query = "FROM Project WHERE owner.username = ?"),
         @NamedQuery(name = "Project.byProfileName", query = "FROM Project WHERE owner.username = ?"),
         @NamedQuery(name = "Project.count", query = "select count(*) from Project"),
         @NamedQuery(name = "Project.list", query = "from Project p order by p.slug asc") })
public class Project extends DeletableObject<Project>
{
   private static final long serialVersionUID = 719438791700341079L;

   @Fetch(FetchMode.JOIN)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @OneToMany(fetch = FetchType.EAGER, mappedBy = "project")
   private List<Membership> memberships = new ArrayList<Membership>();

   @Fetch(FetchMode.SUBSELECT)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @OneToMany(fetch = FetchType.EAGER, mappedBy = "project")
   private List<Feature> features = new ArrayList<Feature>();

   @OrderBy("priority")
   @Fetch(FetchMode.SELECT)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @OneToMany(fetch = FetchType.EAGER, mappedBy = "project")
   private List<Story> stories = new ArrayList<Story>();

   @Fetch(FetchMode.SUBSELECT)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @OneToMany(fetch = FetchType.EAGER, mappedBy = "project")
   private List<Iteration> iterations = new ArrayList<Iteration>();

   @Fetch(FetchMode.SUBSELECT)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @OneToMany(fetch = FetchType.EAGER, mappedBy = "project")
   private List<Milestone> milestones = new ArrayList<Milestone>();

   @Index(name = "projectNameIndex")
   @Column(length = 48, nullable = false)
   private String name;

   @Index(name = "projectSlugIndex")
   @Column(length = 48, nullable = false)
   private String slug;

   @Column(length = 128)
   private String vision;

   @ManyToOne(optional = false, cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
   private Profile owner;

   private PrivacyLevel privacyLevel = PrivacyLevel.PUBLIC;

   @Override
   public String toString()
   {
      return name;
   }

   public Velocity getVelocity()
   {
      Velocity velocity = new Velocity();
      velocity.setHours(25);
      return velocity;
   }

   public boolean isIterationActive()
   {
      Iteration currentIteration = getCurrentIteration();
      if ((currentIteration != null) && (currentIteration.getStartDate() != null))
      {
         return true;
      }
      return false;
   }

   public Iteration getCurrentIteration() throws IllegalStateException
   {
      if ((iterations == null) || (iterations.size() == 0))
      {
         throw new IllegalStateException("Project does not have any iterations");
      }

      Iteration result = null;
      for (Iteration iteration : iterations)
      {
         if (iteration.isDefault())
         {
            result = iteration;
         }
         else if (iteration.isInProgress())
         {
            result = iteration;
            break;
         }
      }

      return result;
   }

   public Iteration getDefaultIteration()
   {
      Iteration result = null;
      for (Iteration iteration : iterations)
      {
         if (iteration.isDefault())
         {
            result = iteration;
            break;
         }
      }
      return result;
   }

   public Set<Iteration> getAvailableIterations()
   {
      Set<Iteration> availableIterations = new HashSet<Iteration>();

      for (Iteration iteration : iterations)
      {
         if (iteration.isDefault() || !iteration.isEnded())
         {
            availableIterations.add(iteration);
         }
      }

      return availableIterations;
   }

   public Iteration getIteration(final String iteration)
   {
      for (Iteration i : iterations)
      {
         if (i.getTitle().equals(iteration))
         {
            return i;
         }
      }
      throw new IllegalArgumentException("No such Iteration in project[" + name + "]:" + iteration);
   }

   public boolean hasMemberInRole(final Profile user, final MemberRole role)
   {
      List<Profile> members = getMembersByRoles(role);
      for (Profile u : members)
      {
         if (u.equals(user))
         {
            return true;
         }
      }
      return false;
   }

   public Membership getMembership(final Profile user)
   {
      List<Membership> members = getMemberships();
      for (Membership membership : members)
      {
         if (membership.getUser().equals(user))
         {
            return membership;
         }
      }
      return new Membership(this, user, MemberRole.NOT_MEMBER);
   }

   public boolean hasActiveMember(final Profile user)
   {
      for (Membership member : memberships)
      {
         if (MemberRole.isActiveMemberRole(member.getRole()))
         {
            return true;
         }
      }
      return false;
   }

   public List<Profile> getOwners()
   {
      return getMembersByRoles(MemberRole.OWNER);
   }

   public List<Profile> getAdmins()
   {
      return getMembersByRoles(MemberRole.ADMIN);
   }

   public List<Profile> getMembers()
   {
      return getMembersByRoles(MemberRole.MEMBER);
   }

   public List<Profile> getAllMembers()
   {
      return getMembersByRoles(MemberRole.values());
   }

   public List<Profile> getActiveMembers()
   {
      return getMembersByRoles(MemberRole.OWNER, MemberRole.ADMIN, MemberRole.MEMBER);
   }

   public List<Profile> getPendingMembers()
   {
      return getMembersByRoles(MemberRole.REQUESTED, MemberRole.INVITED);
   }

   public List<Profile> getMembersByRoles(final MemberRole... roles)
   {
      List<MemberRole> roleList = Arrays.asList(roles);
      List<Profile> result = new ArrayList<Profile>();
      for (Membership member : memberships)
      {
         if (roleList.contains(member.getRole()))
         {
            result.add(member.getUser());
         }
      }
      return result;
   }

   public boolean hasMember(final Profile user)
   {
      for (Membership member : memberships)
      {
         if (member.getUser().equals(user))
         {
            return true;
         }
      }
      return false;
   }

   public boolean hasMemberInRoles(final Profile user, final MemberRole... roles)
   {
      List<Profile> members = getMembersByRoles(roles);
      for (Profile u : members)
      {
         if (u.equals(user))
         {
            return true;
         }
      }
      return false;
   }

   public Feature getFeature(final String feature)
   {
      for (Feature f : features)
      {
         if (f.getName().equals(feature))
         {
            return f;
         }
      }
      throw new IllegalArgumentException("No such Feature in project[" + feature + "]:" + feature);
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = (prime * result) + (name == null ? 0 : name.hashCode());
      return result;
   }

   @Override
   public boolean equals(final Object obj)
   {
      if (this == obj)
      {
         return true;
      }
      if (obj == null)
      {
         return false;
      }
      if (!(obj instanceof Project))
      {
         return false;
      }
      Project other = (Project) obj;
      if (name == null)
      {
         if (other.name != null)
         {
            return false;
         }
      }
      else if (!name.equals(other.name))
      {
         return false;
      }
      return true;
   }

   public List<Feature> getFeatures()
   {
      return features;
   }

   public void setFeatures(final List<Feature> features)
   {
      this.features = features;
   }

   public Feature getDefaultFeature()
   {
      Feature result = null;
      for (Feature f : getFeatures()) {
         if (f.getName() == null)
         {
            result = f;
         }
      }
      return result;
   }

   public List<Story> getStories()
   {
      return stories;
   }

   public List<Story> getOpenStories()
   {
      List<Story> result = new ArrayList<Story>();
      for (Story s : stories)
      {
         if (s.isOpen())
         {
            result.add(s);
         }
      }
      return result;
   }

   public int getNumOpenStories()
   {
      return getOpenStories().size();
   }

   public void setStories(final List<Story> stories)
   {
      this.stories = stories;
   }

   public List<Iteration> getIterations()
   {
      return iterations;
   }

   public List<Iteration> getOpenIterations()
   {
      List<Iteration> result = new ArrayList<Iteration>();

      for (Iteration iteration : iterations) {
         if (!iteration.isEnded())
         {
            result.add(iteration);
         }
      }

      return result;
   }

   public void setIterations(final List<Iteration> iterations)
   {
      this.iterations = iterations;
   }

   public Milestone getMilestone(final String milestone)
   {
      for (Milestone r : milestones)
      {
         if (r.getName().equals(milestone))
         {
            return r;
         }
      }
      throw new IllegalArgumentException("No such Milestone in project[" + name + "]:" + milestone);
   }

   public List<Milestone> getMilestones()
   {
      return milestones;
   }

   public List<Milestone> getUpcomingMilestones()
   {
      List<Milestone> result = new ArrayList<Milestone>();
      for (Milestone milestone : milestones)
      {
         if (milestone.isUpcoming())
         {
            result.add(milestone);
         }
      }
      return result;
   }

   public void setMilestones(final List<Milestone> releases)
   {
      milestones = releases;
   }

   public String getName()
   {
      return name;
   }

   public void setName(final String name)
   {
      this.name = name;
   }

   public String getVision()
   {
      return vision;
   }

   public void setVision(final String vision)
   {
      this.vision = vision;
   }

   public List<Membership> getMemberships()
   {
      return memberships;
   }

   public void setMemberships(final List<Membership> members)
   {
      memberships = members;
   }

   public boolean hasMilestone(final long id)
   {
      for (Milestone m : milestones)
      {
         if (m.getId() == id)
         {
            return true;
         }
      }
      return false;
   }

   public Milestone getMilestone(final long id)
   {
      for (Milestone m : milestones)
      {
         if (m.getId() == id)
         {
            return m;
         }
      }
      throw new IllegalArgumentException("No such Release in project[" + name + "]:" + name);
   }

   public PrivacyLevel getPrivacyLevel()
   {
      return privacyLevel;
   }

   public void setPrivacyLevel(final PrivacyLevel privacyLevel)
   {
      this.privacyLevel = privacyLevel;
   }

   public String getSlug()
   {
      return slug;
   }

   public void setSlug(String slug)
   {
      if (slug != null)
      {
         slug = canonicalize(slug);
         slug = slug.toLowerCase();
      }
      this.slug = slug;
   }

   public Profile getOwner()
   {
      return owner;
   }

   public void setOwner(final Profile owner)
   {
      this.owner = owner;
   }

   public static String canonicalize(final String name)
   {
      String result = null;
      if (name != null)
      {
         result = name.toLowerCase().replace(' ', '-').replaceAll("[^a-z0-9-]*", "").replaceAll("-+", "-");
      }
      return result;
   }

}
