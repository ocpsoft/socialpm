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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.ocpsoft.socialpm.util.Dates;

public class DatesTest
{

   @Test
   public void testIsSameDay() throws InterruptedException
   {
      Date now = new Date(System.currentTimeMillis());
      Thread.sleep(1);
      Date then = new Date(System.currentTimeMillis());
      assertTrue(Dates.isSameDay(now, then));
   }

   @Test
   public void testIsNotSameDay() throws Exception
   {
      Date now = new Date(System.currentTimeMillis());
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(now);
      calendar.add(Calendar.DAY_OF_WEEK, 2);
      now = calendar.getTime();

      Date then = new Date(System.currentTimeMillis());
      assertFalse(Dates.isSameDay(now, then));
   }

   @Test
   public void testIsInRange() throws Exception
   {
      Date start = new Date(1000);
      Date end = new Date(2000);
      Date before = new Date(500);
      Date during = new Date(1500);
      Date after = new Date(2500);
      assertFalse(Dates.isInRange(start, end, before));
      assertTrue(Dates.isInRange(start, end, start));
      assertTrue(Dates.isInRange(start, end, during));
      assertTrue(Dates.isInRange(start, end, end));
      assertFalse(Dates.isInRange(start, end, after));
   }

   @Test
   public void testIsInPrecisionRange() throws Exception
   {
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.YEAR, 2);
      Date before = cal.getTime();
      cal.add(Calendar.YEAR, 2);
      Date start = cal.getTime();
      cal.add(Calendar.YEAR, 2);
      Date during = cal.getTime();
      cal.add(Calendar.YEAR, 2);
      Date end = cal.getTime();
      cal.add(Calendar.YEAR, 2);
      Date after = cal.getTime();
      assertFalse(Dates.isInPrecisionRange(start, end, before, Calendar.YEAR));
      assertTrue(Dates.isInPrecisionRange(start, end, start, Calendar.YEAR));
      assertTrue(Dates.isInPrecisionRange(start, end, during, Calendar.YEAR));
      assertTrue(Dates.isInPrecisionRange(start, end, end, Calendar.YEAR));
      assertFalse(Dates.isInPrecisionRange(start, end, after, Calendar.YEAR));
   }

   @Test
   public void testIsDateInPast() throws Exception
   {
      Date pastDate = Dates.addDays(new Date(), -10);

      assertTrue(Dates.isDateInPast(pastDate));
   }

   @Test
   public void testIsDateInPastCurrentDate() throws Exception
   {
      Date pastDate = new Date();

      assertFalse(Dates.isDateInPast(pastDate));
   }

   @Test
   public void testIsDateInPastFutureDate() throws Exception
   {
      Date pastDate = Dates.addDays(new Date(), 10);

      assertFalse(Dates.isDateInPast(pastDate));
   }

   @Test
   public void testIsDateInFuture() throws Exception
   {
      Date futureDate = Dates.addDays(new Date(), 10);

      assertTrue(Dates.isDateInFuture(futureDate));
   }

   @Test
   public void testIsDateInFutureCurrentDate() throws Exception
   {
      Date futureDate = new Date();

      assertFalse(Dates.isDateInFuture(futureDate));
   }

   @Test
   public void testIsDateInFuturePastDate() throws Exception
   {
      Date futureDate = Dates.addDays(new Date(), -10);

      assertFalse(Dates.isDateInFuture(futureDate));
   }
}
