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
import java.util.Collections;
import java.util.List;

public enum MemberRole
{
    MEMBER, ADMIN, OWNER, INVITED, REQUESTED, NOT_MEMBER;
   private static List<MemberRole> activeRoles;
   private static List<MemberRole> inactiveRoles;

   static
   {
      List<MemberRole> activeRoles = new ArrayList<MemberRole>();
      activeRoles.add(MEMBER);
      activeRoles.add(ADMIN);
      activeRoles.add(OWNER);
      MemberRole.activeRoles = Collections.unmodifiableList(activeRoles);

      List<MemberRole> inactiveRoles = new ArrayList<MemberRole>();
      inactiveRoles.add(INVITED);
      inactiveRoles.add(REQUESTED);
      MemberRole.inactiveRoles = Collections.unmodifiableList(inactiveRoles);
   }

   public static List<MemberRole> getActiveMemberRoles()
   {
      return activeRoles;
   }

   public static List<MemberRole> getInactiveMemberRoles()
   {
      return inactiveRoles;
   }

   public static boolean isActiveMemberRole(final MemberRole role)
   {
      return activeRoles.contains(role);
   }

   public static boolean isInactiveMemberRole(final MemberRole role)
   {
      return inactiveRoles.contains(role);
   }
}
