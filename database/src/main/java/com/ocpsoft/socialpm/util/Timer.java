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
package com.ocpsoft.socialpm.util;

/**
 * @author lb3
 */
public class Timer
{
   private Long startTime = null;
   private Long lapTime = null;
   private Long stopTime = null;

   private Timer()
   {}

   public static Timer getTimer()
   {
      return new Timer();
   }

   public Timer start()
   {
      if (this.startTime != null)
      {
         throw new IllegalStateException("Must reset Timer before starting again");
      }
      this.startTime = System.currentTimeMillis();
      this.lapTime = this.startTime;
      return this;
   }

   public Timer stop()
   {
      this.stopTime = System.currentTimeMillis();
      return this;
   }

   public Timer lap()
   {
      if (this.startTime == null)
      {
         throw new IllegalStateException("Timer must be started before lapping.");
      }
      this.lapTime = this.getFinalTime();
      return this;
   }

   public Timer reset()
   {
      this.stopTime = null;
      this.startTime = null;
      this.lapTime = null;
      return this;
   }

   public long getElapsedMilliseconds()
   {
      if (this.startTime != null)
      {
         return this.getFinalTime() - this.startTime;
      }
      return 0;
   }

   public long getLapMilliseconds()
   {
      if (this.lapTime != null)
      {
         return this.getFinalTime() - this.lapTime;
      }
      return 0;
   }

   private long getFinalTime()
   {
      if (this.stopTime != null)
      {
         return this.stopTime;
      }
      return System.currentTimeMillis();
   }
}
