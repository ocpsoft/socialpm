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

public class Strings
{
   /**
    * Replace all instances of camel case naming standard with the underscore equivalent. E.g. "someString" becomes
    * "some_string". This method does not perform any validation and will not throw any exceptions.
    * 
    * @param input unformatted string
    * @return modified result
    */
   public static String camelToUnderscore(final String input)
   {
      String result = input;
      if (input instanceof String)
      {
         result = input.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
      }
      return result;
   }

   /**
    * Join all strings, using the delim character as the delimeter. E.g.: a delim of "-", and strings of "foo", "bar"
    * would produce "foo-bar"
    * 
    * @param buf
    * @param strings
    * @return
    */
   public static String join(final String delim, final String... strings)
   {
      String result = "";
      for (String string : strings)
      {
         result += delim + string;
      }

      if (delim != null)
      {
         result = result.substring(delim.length());
      }
      return result;
   }

   public static String canonicalize(final String name)
   {
      String result = "";
      if (name != null)
      {
         result = name.toLowerCase().replace(' ', '-').replaceAll("[^a-z0-9-]*", "").replaceAll("-+", "-");
      }
      return result;
   }
}
