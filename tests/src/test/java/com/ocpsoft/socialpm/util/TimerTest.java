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
