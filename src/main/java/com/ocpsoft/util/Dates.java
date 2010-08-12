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

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

public class Dates
{
    public static boolean isSameDay(final Date one, final Date two)
    {
        return DateUtils.isSameDay(one, two);
    }

    public static Date now()
    {
        return new Date(System.currentTimeMillis());
    }

    public static Date addDays(final Date date, final int amount)
    {
        return DateUtils.addDays(date, amount);
    }

    public static long calculateNumberOfDaysBetween(final Date one, final Date two)
    {
        long milisOne = one.getTime();
        long milisTwo = two.getTime();
        long diff = milisTwo - milisOne;

        // Calculate difference in days
        long diffDays = diff / (24 * 60 * 60 * 1000);

        return diffDays;
    }

    /**
     * Perform an inclusive date range comparison to a specific field precision
     * 
     * @param field
     *            see <i>java.util.Calendar</i> Millisecond, Second, Minute,
     *            Hour, Day, Week, etc...
     */
    public static boolean isInPrecisionRange(Date start, Date end, Date date, final int field)
    {
        start = DateUtils.truncate(start, field);
        end = DateUtils.truncate(end, field);
        date = DateUtils.truncate(date, field);
        if ((date.compareTo(start) >= 0) && (date.compareTo(end) <= 0))
        {
            return true;
        }
        return false;
    }

    public static boolean isInRange(final Date start, final Date end, final Date date)
    {
        if ((date.compareTo(start) >= 0) && (date.compareTo(end) <= 0))
        {
            return true;
        }
        return false;
    }

    public static boolean isDateInPast(final Date pastDate)
    {
        Date today = new Date();
        return !isSameDay(today, pastDate) && (today.compareTo(pastDate) > 0);
    }

    public static boolean isDateInFuture(final Date futureDate)
    {
        Date today = new Date();
        return !isSameDay(today, futureDate) && (today.compareTo(futureDate) < 0);
    }

    public static boolean anyInRange(final Date start, final Date end, final Date... dates)
    {
        for (Date d : dates)
        {
            if (isInRange(start, end, d))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean anyInRange(final Date start, final Date end, final List<Date> dates)
    {
        for (Date d : dates)
        {
            if (isInRange(start, end, d))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean allInRange(final Date start, final Date end, final Date... dates)
    {
        for (Date d : dates)
        {
            if (!isInRange(start, end, d))
            {
                return false;
            }
        }
        return true;
    }
}
