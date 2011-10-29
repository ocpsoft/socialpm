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

public class StringValidations
{
   public static final String EMAIL_REGEX = "(?:[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e"
            + "-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[A-Za-z0-9](?:[A-Za-z0-9-]"
            + "*[A-Za-z0-9])?\\.)+[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4]"
            + "[0-9]|[01]?[0-9][0-9]?|[A-Za-z0-9-]*[A-Za-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b"
            + "\\x0c\\x0e-\\x7f])+)\\])";

   public static boolean isAlphabetic(final String text)
   {
      if (text != null)
      {
         return text.matches("^[a-zA-Z]*$");
      }

      return false;
   }

   public static boolean isAlphanumeric(final String textWithDigits)
   {
      if (textWithDigits != null)
      {
         return textWithDigits.matches("^[a-zA-Z\\d]*$");
      }

      return false;
   }

   public static boolean isAlphanumericDash(String text)
   {
      return text.matches("^[a-zA-Z\\d-]*$");
   }

   public static boolean isAlphanumericSpaceUnderscore(final String textWithDigits)
   {
      if (textWithDigits != null)
      {
         return textWithDigits.matches("^[a-zA-Z\\d_ ]*$");
      }

      return false;
   }

   public static boolean isEmptyString(final String text)
   {
      if (text != null)
      {
         return text.matches("");
      }

      return false;
   }

   public static boolean isPunctuatedText(final String text)
   {
      if (text != null)
      {
         return text.matches("^[a-zA-Z/\\d\\s._?!,;':\"~`$%&()+=\\[\\]-]*$");
      }

      return false;
   }

   public static boolean isPunctuatedTextWithoutSpace(final String text)
   {
      if (text != null)
      {
         return text.matches("^[a-zA-Z/\\d._?!,;':\"~`$%&()+=\\[\\]-]*$");
      }

      return false;
   }

   public static boolean isDecimal(final String number)
   {
      if (number != null)
      {
         return number.matches("([0-9]+(\\.[0-9]+)?)|([0-9]*\\.[0-9]+)");
      }
      return false;
   }

   public static boolean isWholeNumber(final String number)
   {
      if (number != null)
      {
         return number.matches("[0-9]+");
      }
      return false;
   }

   public static boolean isDigit(final String digit)
   {
      if (digit != null)
      {
         return digit.matches("^\\d$");
      }

      return false;
   }

   public static boolean isEmailAddress(final String email)
   {
      if (email != null)
      {
         return email.matches("^" + EMAIL_REGEX + "$");
      }

      return false;
   }

   public static boolean isState(final String state)
   {
      if (state != null)
      {
         return state.toUpperCase().matches(
                  "^(AL|AK|AS|AZ|AR|CA|CO|CT|DE|DC|FM|FL|FA|GU|HI|ID|IL|IN|IA|KS|KY|LA|ME|MH|MD|"
                           + "MA|MI|MN|MS|MO|MT|NE|NV|NH|NJ|NM|NY|NC|ND|MP|OH|OK|OR|PW|PA|PR|RI|SC|SD|TN|TX|"
                           + "UT|VT|VI|VA|WA|WV|WI|WY)$");
      }

      return false;
   }

   public static boolean isZipCode(final String zipCode)
   {
      if (zipCode != null)
      {
         return zipCode.matches("\\d{5}((-| +)\\d{4})?$");
      }

      return false;
   }

   public static boolean isPhoneNumber(final String phoneNumber)
   {
      if (phoneNumber != null)
      {
         return phoneNumber.matches("^(?:\\([2-9]\\d{2}\\)\\ ?|[2-9]\\d{2}(?:\\-?|\\ ?))[2-9]\\d{2}[- ]?\\d{4}$");
      }

      return false;
   }

   public static boolean isPassword(final String password)
   {
      if (password != null)
      {
         return password.matches("^[a-zA-Z0-9!@#$%^&*\\s\\(\\)_\\+=-]{8,}$");
      }

      return false;
   }

   public static boolean isStrictPassword(final String password)
   {
      if (password != null)
      {
         return password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z\\d!@#$%^&*\\s\\(\\)_\\+=-]{8,}$");
      }

      return false;
   }

   public static boolean hasData(final String value)
   {
      return (value != null) && (value.length() > 0) && value.matches(".*\\S.*");
   }

   public static boolean length(final int length, final String value)
   {
      return (value != null) && (value.length() == length);
   }

   public static boolean minLength(final int length, final String value)
   {
      return (value != null) && (value.length() >= length);
   }

   public static boolean maxLength(final int length, final String value)
   {
      return (value != null) && (value.length() <= length);
   }
}
