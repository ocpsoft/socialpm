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

import org.junit.Test;

import com.ocpsoft.socialpm.util.StringValidations;

public class StringValidationsTest
{
   @Test
   public void testIsAlphabeticReturnsTrueForValidInput()
   {
      assertTrue(StringValidations.isAlphabetic("Test"));
   }

   @Test
   public void testIsAlphabeticReturnsFalseForNumericChar()
   {
      assertFalse(StringValidations.isAlphabetic("T3st"));
   }

   @Test
   public void testIsAlphabeticReturnsFalseForSpecialCharacter()
   {
      assertFalse(StringValidations.isAlphabetic("t5@a"));
   }

   @Test
   public void testIsAlphaNumericReturnsTrueForAlphanumericInput()
   {
      assertTrue(StringValidations.isAlphanumeric("T3st"));
   }

   @Test
   public void testIsAlphaNumericReturnsTrueForAlphabeticInput()
   {
      assertTrue(StringValidations.isAlphanumeric("Test"));
   }

   @Test
   public void testIsAlphaNumericReturnsFalseForSpecialChar()
   {
      assertFalse(StringValidations.isAlphanumeric("@#3st!"));
   }

   @Test
   public void testIsEmptyStringReturnsTrueForEmptyString()
   {
      assertTrue(StringValidations.isEmptyString(""));
   }

   @Test
   public void testIsEmptyStringReturnsFalseForNonEmptyString()
   {
      assertFalse(StringValidations.isEmptyString("asdWsonciw aski"));
   }

   @Test
   public void testIsEmptyStringReturnsFalseForSpecialChar()
   {
      assertFalse(StringValidations.isEmptyString("@#3st!"));
   }

   @Test
   public void testIsPunctuatedTextReturnsTrueForAlphabeticOnly()
   {
      assertTrue(StringValidations.isPunctuatedText("This is a test"));
   }

   @Test
   public void testIsPunctuatedTextReturnsTrueForTextWithPunctuation()
   {
      assertTrue(StringValidations.isPunctuatedText(",./?;':\"~`!$%&()-_+=[]"));
   }

   @Test
   public void testIsPunctuatedTextWithoutSpaceReturnsTrueForTextWithPunctuation()
   {
      assertTrue(StringValidations.isPunctuatedTextWithoutSpace(",./?;':\"~`!$%&()-_+=[]"));
   }

   @Test
   public void testIsPunctuatedTextReturnsTrueForTextWithSingleQuote()
   {
      assertTrue(StringValidations.isPunctuatedText("Quote's test"));
   }

   @Test
   public void testIsPunctuatedTextReturnsFalseForGreaterThanOrLessThan()
   {
      assertFalse(StringValidations.isPunctuatedText("BAD<Name>"));
   }

   @Test
   public void testIsPunctuatedTextWithoutSpaceReturnsTrueOneWord()
   {
      assertTrue(StringValidations.isPunctuatedTextWithoutSpace("text"));
   }

   @Test
   public void testIsPunctuatedTextWithoutSpaceReturnsTrueOneWordWithNumbers()
   {
      assertTrue(StringValidations.isPunctuatedTextWithoutSpace("C3PO"));
   }

   @Test
   public void testIsPunctuatedTextWithoutSpaceReturnsTrueConcatWords()
   {
      assertTrue(StringValidations.isPunctuatedTextWithoutSpace("WhatDoYouWant"));
   }

   @Test
   public void testIsPunctuatedTextWithoutSpaceReturnsTrueOnePunctuatedWord()
   {
      assertTrue(StringValidations.isPunctuatedTextWithoutSpace("name."));
   }

   @Test
   public void testIsPunctuatedTextWithoutSpaceReturnsFalseMoreThanOneWord()
   {
      assertFalse(StringValidations.isPunctuatedTextWithoutSpace("Who am I"));
   }

   @Test
   public void testIsPunctuatedTextWithoutSpaceReturnsFalseMoreThanOneWordWithNumbers()
   {
      assertFalse(StringValidations.isPunctuatedTextWithoutSpace("C3PO R2D2"));
   }

   @Test
   public void testIsPunctuatedTextWithoutSpaceReturnsFalseMoreThanOneWordPunctuated()
   {
      assertFalse(StringValidations.isPunctuatedTextWithoutSpace("How are you?"));
   }

   @Test
   public void testIsDecimalReturnsTrueForNumericInputWithDecimalPoint()
   {
      assertTrue(StringValidations.isDecimal("9.00"));
   }

   @Test
   public void testIsDecimalReturnsTrueForNumericInputWithoutDecimalPoint()
   {
      assertTrue(StringValidations.isDecimal("7"));
   }

   @Test
   public void testIsDecimalReturnsFalseForAlphabeticInput()
   {
      assertFalse(StringValidations.isDecimal("t"));
   }

   @Test
   public void testIsDigitReturnsTrueForSingleDigit()
   {
      assertTrue(StringValidations.isDigit("4"));
   }

   @Test
   public void testIsDigitReturnsFalseForMultipleDigit()
   {
      assertFalse(StringValidations.isDigit("43"));
   }

   @Test
   public void testIsDigitReturnsFalseForNumberWithDecimalPoint()
   {
      assertFalse(StringValidations.isDigit("4.0"));
   }

   @Test
   public void testIsDigitReturnsFalseForInputWithAlphabeticAndSpecial()
   {
      assertFalse(StringValidations.isDigit("ia*"));
   }

   @Test
   public void testIsEmailAddressReturnsTrueForValidEmail()
   {
      assertTrue(StringValidations.isEmailAddress("me@ocpsoft.com"));
   }

   @Test
   public void testIsEmailAddressReturnsTrueForValidEmailUppercase()
   {
      assertTrue(StringValidations.isEmailAddress("Me@OcpSoft.com"));
   }

   @Test
   public void testIsEmailAddressReturnsFalseForInvalidEmail()
   {
      assertFalse(StringValidations.isEmailAddress("..com"));
   }

   @Test
   public void testIsEmailAddressReturnsTrueForValidEmail2()
   {
      assertTrue(StringValidations.isEmailAddress("me@mail.ocpsoft.org"));
   }

   @Test
   public void testIsZipCodeReturnsTrueForShortZip()
   {
      assertTrue(StringValidations.isZipCode("28212"));
   }

   @Test
   public void testIsZipCodeReturnsTrueForLongZip()
   {
      assertTrue(StringValidations.isZipCode("78415 0158"));
   }

   @Test
   public void testIsZipCodeReturnsFalseForBadZip()
   {
      assertFalse(StringValidations.isZipCode("75126=0465"));
   }

   @Test
   public void testIsZipCodeReturnsFalseForBadZipAlphabetic()
   {
      assertFalse(StringValidations.isZipCode("7pe26=0A65"));
   }

   @Test
   public void testIsPhoneNumberReturnsTruePhoneNumberWithSpecialChars()
   {
      assertTrue(StringValidations.isPhoneNumber("(704)546-7542"));
   }

   @Test
   public void testIsPhoneNumberReturnsTrueWithoutSpecialChars()
   {
      assertTrue(StringValidations.isPhoneNumber("5038751238"));
   }

   @Test
   public void testIsPhoneNumberReturnsFalseInvalidInputShortLenght()
   {
      assertFalse(StringValidations.isPhoneNumber("555555"));
   }

   @Test
   public void testIsPhoneNumberReturnsFalseInvalidInput()
   {
      assertFalse(StringValidations.isPhoneNumber("(8e074d/874"));
   }

   @Test
   public void testIsPasswordReturnsTrueWithoutSpecialChars()
   {
      assertTrue(StringValidations.isPassword("Test1234"));
   }

   @Test
   public void testIsPasswordReturnsTrueWithSpecialChars()
   {
      assertTrue(StringValidations.isPassword("abc!DE&fj87"));
   }

   @Test
   public void testIsPasswordReturnsFalseShortLength()
   {
      assertFalse(StringValidations.isPassword("r$8"));
   }

   @Test
   public void testIsPasswordReturnsTrueLongLength()
   {
      assertTrue(StringValidations.isPassword("qwerty!io$%pasdasdfdsfsdfg8"));
   }

   @Test
   public void testIsPasswordReturnsTrueWithSpaces()
   {
      assertTrue(StringValidations.isPassword("qwerty!io$% pasdasd fdsfsdfg8"));
   }

   @Test
   public void testIsPasswordReturnsFalseInvalidCharacters()
   {
      assertFalse(StringValidations.isPassword("alphabeta}{"));
   }

   @Test
   public void testIsStrictPasswordReturnsFalseWithoutCapitalChars()
   {
      assertFalse(StringValidations.isStrictPassword("test1234"));
   }

   @Test
   public void testIsStrictPasswordReturnsFalseWithoutNumbers()
   {
      assertFalse(StringValidations.isStrictPassword("Testasdfd"));
   }

   @Test
   public void testIsStrictPasswordReturnsTrueWithoutSpecialChars()
   {
      assertTrue(StringValidations.isStrictPassword("Test1234"));
   }

   @Test
   public void testIsStrictPasswordReturnsTrueWithSpecialChars()
   {
      assertTrue(StringValidations.isStrictPassword("abc!DE&fj87"));
   }

   @Test
   public void testIsStrictPasswordReturnsFalseShortLength()
   {
      assertFalse(StringValidations.isStrictPassword("r$8"));
   }

   @Test
   public void testIsStrictPasswordReturnsTrueLongLength()
   {
      assertTrue(StringValidations.isStrictPassword("qwT4rty!io$%asdfasdfdspasdfg8"));
   }

   @Test
   public void testIsStrictPasswordReturnsWithSpace()
   {
      assertTrue(StringValidations.isStrictPassword("qwT4rty! io$%asdfasdfdspasdfg8"));
   }

   @Test
   public void testIsStrictPasswordReturnsFalseInvalidCharacters()
   {
      assertFalse(StringValidations.isStrictPassword("alphabeta}{"));
   }

   @Test
   public void testIsStateReturnsTrueForAllStateAbbreviations() throws Exception
   {
      String[] states = ("AL|AK|AS|AZ|AR|CA|CO|CT|DE|DC|FM|FL|FA|GU|HI|ID|IL|IN|IA|KS|KY|LA|ME|MH|MD|" + "MA|MI|MN|MS|MO|MT|NE|NV|NH|NJ|NM|NY|NC|ND|MP|OH|OK|OR|PW|PA|PR|RI|SC|SD|TN|TX|" + "UT|VT|VI|VA|WA|WV|WI|WY").split("\\|");

      for (String state : states)
      {
         assertTrue(StringValidations.isState(state));
      }
   }

   @Test
   public void testIsStateReturnsTrueForAllStateAbbreviationsLowerCase() throws Exception
   {
      String[] states = ("al|ak|as|az|ar|ca|co|ct|de|dc|fm|fl|fa|gu|hi|id|il|in|ia|ks|ky|la|me|mh|md|" + "ma|mi|mn|ms|mo|mt|ne|nv|nh|nj|nm|ny|nc|nd|mp|oh|ok|or|pw|pa|pr|ri|sc|sd|tn|tx|" + "ut|vt|vi|va|wa|wv|wi|wy").split("\\|");

      for (String state : states)
      {
         assertTrue(StringValidations.isState(state));
      }
   }

   @Test
   public void testIsStateReturnsFalseForNonStateAbbreviations() throws Exception
   {
      String[] states = ("sdf|North|S|E|W|MONT|MON|AF|34| |*|<>|`|Num3r1c|oMg").split("\\|");

      for (String state : states)
      {
         assertFalse(StringValidations.isState(state));
      }
   }

   @Test
   public void testIsAlphanumericSpaceUnderscoreReturnsTrueWithAlphanumericTextAndSpaces()
   {
      assertTrue(StringValidations.isAlphanumericSpaceUnderscore("This is plain text"));
   }

   @Test
   public void testIsAlphanumericSpaceUnderscoreReturnsTrueWithAlphanumericTextSpacesAndUnderscore()
   {
      assertTrue(StringValidations.isAlphanumericSpaceUnderscore("This is plain_text"));
   }

   @Test
   public void testIsAlphanumericSpaceUnderscoreReturnsFalseWithTabs()
   {
      assertFalse(StringValidations.isAlphanumericSpaceUnderscore("This\tis plain_text"));
   }

   @Test
   public void testIsAlphanumericSpaceUnderscoreReturnsFalseWithSpecialChars()
   {
      assertFalse(StringValidations.isAlphanumericSpaceUnderscore("This &^ is plain_text"));
   }

   @Test
   public void testIsAlphanumericSpaceUnderscoreReturnsFalseWithAltCharacters()
   {
      assertFalse(StringValidations.isAlphanumericSpaceUnderscore("This???????"));
   }

   @Test
   public void testHasDataReturnsTrueWithData() throws Exception
   {
      assertTrue(StringValidations.hasData("Yes"));
   }

   @Test
   public void testHasDataReturnsFalseWithNullValue() throws Exception
   {
      assertFalse(StringValidations.hasData(null));
   }

   @Test
   public void testHasDataReturnsFalseWithEmptyString() throws Exception
   {
      assertFalse(StringValidations.hasData(""));
   }

   @Test
   public void testIsDecimalReturnsTrueForWholeNumber() throws Exception
   {
      assertTrue(StringValidations.isDecimal("5"));
   }

   @Test
   public void testIsDecimalReturnsTrueForDecimalOnly() throws Exception
   {
      assertTrue(StringValidations.isDecimal(".5"));
   }

   @Test
   public void testIsDecimalReturnsFalseForPeriodOnly() throws Exception
   {
      assertFalse(StringValidations.isDecimal("."));
   }

   @Test
   public void testIsDecimalReturnsTrueForCompleteDecimal() throws Exception
   {
      assertTrue(StringValidations.isDecimal("34.178"));
   }

   @Test
   public void testIsDecimalReturnsFalseForEmpty() throws Exception
   {
      assertFalse(StringValidations.isDecimal(""));
   }

   @Test
   public void testIsWholeNumberReturnsTrueForValidInput() throws Exception
   {
      assertTrue(StringValidations.isWholeNumber("5"));
   }

   @Test
   public void testIsWholeNumberReturnsFalseForAlphabeticInput() throws Exception
   {
      assertFalse(StringValidations.isWholeNumber("as"));
   }

   @Test
   public void testIsWholeNumberReturnsFalseForNoInput() throws Exception
   {
      assertFalse(StringValidations.isWholeNumber(""));
   }

   @Test
   public void testIsWholeNumberReturnsFalseForDecimalInput() throws Exception
   {
      assertFalse(StringValidations.isWholeNumber("12.1234"));
   }

   @Test
   public void testLengthReturnsFalseIfNull() throws Exception
   {
      assertFalse(StringValidations.length(4, null));
   }

   @Test
   public void testLengthReturnsTrueIfLengthMatches() throws Exception
   {
      assertTrue(StringValidations.length(4, "four"));
   }

   @Test
   public void testLengthReturnsFalseIfShorter() throws Exception
   {
      assertFalse(StringValidations.length(4, "two"));
   }

   @Test
   public void testLengthReturnsFalseIfLonger() throws Exception
   {
      assertFalse(StringValidations.length(4, "seven"));
   }

   @Test
   public void testMinLengthReturnsFalseIfNull() throws Exception
   {
      assertFalse(StringValidations.minLength(4, null));
   }

   @Test
   public void testMinLengthReturnsTrueIfLengthMatches() throws Exception
   {
      assertTrue(StringValidations.minLength(4, "four"));
   }

   @Test
   public void testMinLengthReturnsFalseIfShorter() throws Exception
   {
      assertFalse(StringValidations.minLength(4, "two"));
   }

   @Test
   public void testMinLengthReturnsTrueIfLonger() throws Exception
   {
      assertTrue(StringValidations.minLength(4, "seven"));
   }

   @Test
   public void testMaxLengthReturnsFalseIfNull() throws Exception
   {
      assertFalse(StringValidations.maxLength(4, null));
   }

   @Test
   public void testMaxLengthReturnsTrueIfLengthMatches() throws Exception
   {
      assertTrue(StringValidations.maxLength(4, "four"));
   }

   @Test
   public void testMaxLengthReturnsTrueIfShorter() throws Exception
   {
      assertTrue(StringValidations.maxLength(4, "two"));
   }

   @Test
   public void testMaxLengthReturnsFalseIfLonger() throws Exception
   {
      assertFalse(StringValidations.maxLength(4, "seven"));
   }

}
