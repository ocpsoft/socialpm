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

package com.ocpsoft.socialpm.domain.project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Index;

import com.ocpsoft.socialpm.domain.NoSuchObjectException;
import com.ocpsoft.socialpm.domain.PersistentObject;
import com.ocpsoft.socialpm.domain.project.iteration.Iteration;
import com.ocpsoft.socialpm.domain.project.stories.Story;
import com.ocpsoft.socialpm.domain.user.User;
import com.ocpsoft.socialpm.util.Strings;

@Entity
@Table(name = "projects")
@NamedQueries({ @NamedQuery(name = "project.byName", query = "from Project where name = ?"), @NamedQuery(name = "project.byCanonicalName", query = "from Project where canonicalName = ?"), @NamedQuery(name = "project.count", query = "select count(*) from Project"), @NamedQuery(name = "project.list", query = "from Project p order by p.canonicalName asc") })
public class Project extends PersistentObject<Project>
{
   private static final long serialVersionUID = 719438791700341079L;

   @Fetch(FetchMode.JOIN)
   @OneToMany(fetch = FetchType.EAGER, mappedBy = "project", orphanRemoval = true, cascade = CascadeType.ALL)
   private List<Membership> memberships = new ArrayList<Membership>();

   @Fetch(FetchMode.SUBSELECT)
   @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", orphanRemoval = true, cascade = CascadeType.ALL)
   private List<Feature> features = new ArrayList<Feature>();

   @Fetch(FetchMode.SELECT)
   @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", orphanRemoval = true, cascade = CascadeType.ALL)
   private List<Story> stories = new ArrayList<Story>();

   @Fetch(FetchMode.SUBSELECT)
   @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", orphanRemoval = true, cascade = CascadeType.ALL)
   private List<Iteration> iterations = new ArrayList<Iteration>();

   @Fetch(FetchMode.SUBSELECT)
   @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", orphanRemoval = true, cascade = CascadeType.ALL)
   private List<Milestone> milestones = new ArrayList<Milestone>();

   @Index(name = "projectNameIndex")
   @Column(length = 48, nullable = false, updatable = false, unique = true)
   private String name;

   @Index(name = "projectUrlIndex")
   @Column(length = 48, nullable = false, updatable = false, unique = true)
   private String canonicalName;

   @Column(length = 128)
   private String vision;

   @Column(length = 2048)
   private String goals;

   @Column(length = 2048)
   private String objectives;
   private PrivacyLevel privacyLevel = PrivacyLevel.PUBLIC;

   @Override
   public String toString()
   {
      return name;
   }

   public boolean hasCurrentIteration()
   {
      Iteration currentIteration = getCurrentIteration();
      if ((currentIteration != null) && (currentIteration.getStartDate() != null))
      {
         return true;
      }
      return false;
   }

   public Iteration getCurrentIteration() throws NoSuchObjectException
   {
      if ((iterations == null) || (iterations.size() == 0))
      {
         throw new NoSuchObjectException("Project does not have any iterations");
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
      throw new NoSuchObjectException("No such Iteration in project[" + name + "]:" + iteration);
   }

   public boolean hasMemberInRole(final User user, final MemberRole role)
   {
      List<User> members = getMembersByRoles(role);
      for (User u : members)
      {
         if (u.equals(user))
         {
            return true;
         }
      }
      return false;
   }

   public Membership getMembership(final User user)
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

   public boolean hasActiveMember(final User user)
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

   public List<User> getOwners()
   {
      return getMembersByRoles(MemberRole.OWNER);
   }

   public List<User> getAdmins()
   {
      return getMembersByRoles(MemberRole.ADMIN);
   }

   public List<User> getMembers()
   {
      return getMembersByRoles(MemberRole.MEMBER);
   }

   public List<User> getAllMembers()
   {
      return getMembersByRoles(MemberRole.values());
   }

   public List<User> getActiveMembers()
   {
      return getMembersByRoles(MemberRole.OWNER, MemberRole.ADMIN, MemberRole.MEMBER);
   }

   public List<User> getPendingMembers()
   {
      return getMembersByRoles(MemberRole.REQUESTED, MemberRole.INVITED);
   }

   public List<User> getMembersByRoles(final MemberRole... roles)
   {
      List<MemberRole> roleList = Arrays.asList(roles);
      List<User> result = new ArrayList<User>();
      for (Membership member : memberships)
      {
         if (roleList.contains(member.getRole()))
         {
            result.add(member.getUser());
         }
      }
      return result;
   }

   public boolean hasMember(final User user)
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

   public boolean hasMemberInRoles(final User user, final MemberRole... roles)
   {
      List<User> members = getMembersByRoles(roles);
      for (User u : members)
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
      throw new NoSuchObjectException("No such Feature in project[" + feature + "]:" + feature);
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + (name == null ? 0 : name.hashCode());
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

   public List<Story> getStories()
   {
      return stories;
   }

   public void setStories(final List<Story> stories)
   {
      this.stories = stories;
   }

   public List<Iteration> getIterations()
   {
      return iterations;
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
      throw new NoSuchObjectException("No such Milestone in project[" + name + "]:" + milestone);
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

   public String getGoals()
   {
      return goals;
   }

   public void setGoals(final String goals)
   {
      this.goals = goals;
   }

   public String getObjectives()
   {
      return objectives;
   }

   public void setObjectives(final String objectives)
   {
      this.objectives = objectives;
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
      throw new NoSuchObjectException("No such Release in project[" + name + "]:" + name);
   }

   public PrivacyLevel getPrivacyLevel()
   {
      return privacyLevel;
   }

   public void setPrivacyLevel(final PrivacyLevel privacyLevel)
   {
      this.privacyLevel = privacyLevel;
   }

   public String getCanonicalName()
   {
      return canonicalName == null ? Strings.canonicalize(name) : canonicalName;
   }

   public void setCanonicalName(final String canonicalName)
   {
      this.canonicalName = canonicalName;
   }

}
