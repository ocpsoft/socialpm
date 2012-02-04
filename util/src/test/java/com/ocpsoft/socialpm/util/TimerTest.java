/**
 * This file is part of OCPsoft SocialPM: Agile Project Management Tools (SocialPM) 
 *
 * Copyright (c)2011 Lincoln Baxter, III <lincoln@ocpsoft.com> (OCPsoft)
 * Copyright (c)2011 OCPsoft.com (http://ocpsoft.com)
 * 
 * If you are developing and distributing open source applications under 
 * the GPL License, then you are free to re-distribute SocialPM under the
 * terms of the GPL License:
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
 * For OEMs, ISVs, and VARs who wish to distribute SocialPM with their 
 * products, or host their product online, OCPsoft provides flexible 
 * OEM commercial licenses. 
 * 
 * Optionally, customers may choose a Commercial License. For additional 
 * details, contact an OCPsoft representative (sales@ocpsoft.com)
 */
package com.ocpsoft.socialpm.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ocpsoft.socialpm.util.Timer;

public class TimerTest
{

   @Test
   public void testStart() throws InterruptedException
   {
      Timer timer = Timer.getTimer();
      assertEquals(0, timer.getElapsedMilliseconds());
      assertEquals(0, timer.getLapMilliseconds());
      timer.start();
      Thread.sleep(1);
      assertTrue(timer.getElapsedMilliseconds() > 0);
   }

   @Test(expected = IllegalStateException.class)
   public void testStartThrowsExceptionIfNotReset() throws InterruptedException
   {
      Timer timer = Timer.getTimer();
      timer.start();
      timer.start();
   }

   @Test
   public void testStartSucceedsAfterReset() throws InterruptedException
   {
      Timer timer = Timer.getTimer();
      timer.start();
      timer.reset();
      timer.start();
   }

   @Test
   public void testLap() throws InterruptedException
   {
      Timer timer = Timer.getTimer();
      timer.start();
      Thread.sleep(1);
      timer.lap();
      Thread.sleep(1);
      assertTrue(timer.getLapMilliseconds() > 0);
      assertTrue(timer.getElapsedMilliseconds() > timer.getLapMilliseconds());
   }

   @Test(expected = IllegalStateException.class)
   public void testLapThrowsExceptionIfTimerNotStarted()
   {
      Timer timer = Timer.getTimer();
      timer.lap();
   }

   @Test
   public void testReset() throws InterruptedException
   {
      Timer timer = Timer.getTimer();
      timer.start();
      timer.lap();
      Thread.sleep(1);
      timer.reset();
      assertEquals(0, timer.getElapsedMilliseconds());
      assertEquals(0, timer.getLapMilliseconds());
   }

   @Test
   public void testGetElapsedMilliseconds() throws InterruptedException
   {
      Timer timer = Timer.getTimer();
      assertEquals(0, timer.getElapsedMilliseconds());
      timer.start();
      timer.lap();
      Thread.sleep(1);
      assertTrue(timer.getLapMilliseconds() > 0);
   }

   @Test
   public void testGetLapMilliseconds() throws InterruptedException
   {
      Timer timer = Timer.getTimer();
      assertEquals(0, timer.getElapsedMilliseconds());
      timer.start();
      timer.lap();
      Thread.sleep(1);
      assertTrue(timer.getElapsedMilliseconds() > 0);
   }

}
