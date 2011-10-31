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

import org.junit.Test;

import com.ocpsoft.socialpm.util.Strings;

public class StringsTest
{

   @Test
   public void testCamelToUnderscore()
   {
      String input = "myCamelCaseTest";
      String expected = "my_camel_case_test";
      String actual = Strings.camelToUnderscore(input);
      assertEquals(expected, actual);
   }

   @Test
   public void testCamelToUnderscoreSucceedsOnEmptyString() throws Exception
   {
      String input = "";
      String expected = "";
      String actual = Strings.camelToUnderscore(input);
      assertEquals(expected, actual);
   }

   @Test
   public void testCamelToUnderscorePassesThroughNull() throws Exception
   {
      String input = null;
      String output = Strings.camelToUnderscore(input);
      assertEquals(input, output);
   }

   @Test
   public void testCamelToUnderscoreConvertsConsecutiveCapitalsToLowercase() throws Exception
   {
      String input = "mySQLTest";
      String expected = "my_sqltest";
      String actual = Strings.camelToUnderscore(input);
      assertEquals(expected, actual);
   }

   @Test
   public void testCanonicalize()
   {
      String input = "Project#$% Name";
      String expected = "project-name";
      String actual = Strings.canonicalize(input);
      assertEquals(expected, actual);
   }

   @Test
   public void testCanonicalizeStripsExtraDashes()
   {
      String input = "Project#$%--- Name";
      String expected = "project-name";
      String actual = Strings.canonicalize(input);
      assertEquals(expected, actual);
   }

   @Test
   public void testCanonicalizeLeavesDigits()
   {
      String input = "Project#$%--- Name99";
      String expected = "project-name99";
      String actual = Strings.canonicalize(input);
      assertEquals(expected, actual);
   }

}
