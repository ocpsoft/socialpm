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


public enum Points
{
   NOT_POINTED, S1(1), S2(2), S3(3), S5(5), S8(8), S13(13), S20(20), S40(40), S100(100);

   private int value;

   @Override
   public String toString()
   {
      if (NOT_POINTED.equals(this))
      {
         return "None";
      }
      return String.valueOf(value);
   }

   private Points()
   {
   }

   Points(final int value)
   {
      this.value = value;
   }

   public int getValue()
   {
      return value;
   }

   public int getOrdinal()
   {
      return this.ordinal();
   }

   public String getName()
   {
      return this.name();
   }
}