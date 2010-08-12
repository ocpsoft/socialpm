/*
 * Copyright 2010 - Lincoln Baxter, III (lincoln@ocpsoft.com) - Licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 - Unless required by applicable
 * law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.ocpsoft.util;

/**
 * @author lb3
 */
public class Timer
{
   private Long startTime = null;
   private Long lapTime = null;
   private Long stopTime = null;

   private Timer()
   {
   }

   public static Timer getTimer()
   {
      return new Timer();
   }

   public void start()
   {
      if (this.startTime != null)
      {
         throw new IllegalStateException("Must reset Timer before starting again");
      }
      this.startTime = System.currentTimeMillis();
      this.lapTime = this.startTime;
   }

   public void stop()
   {
      this.stopTime = System.currentTimeMillis();
   }

   public void lap()
   {
      if (this.startTime == null)
      {
         throw new IllegalStateException("Timer must be started before lapping.");
      }
      this.lapTime = this.getFinalTime();
   }

   public void reset()
   {
      this.stopTime = null;
      this.startTime = null;
      this.lapTime = null;
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
