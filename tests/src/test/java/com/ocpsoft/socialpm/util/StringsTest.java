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
