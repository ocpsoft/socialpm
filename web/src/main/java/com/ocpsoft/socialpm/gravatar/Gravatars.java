/*
 * Copyright 2011 <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ocpsoft.socialpm.gravatar;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Named;

import com.ocpsoft.socialpm.util.Assert;
import com.ocpsoft.socialpm.util.StringValidations;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
@Named
public class Gravatars
{
   private static final String GRAVATAR_BASE_URL = "http://www.gravatar.com/avatar/";

   public String getURLFor(final String email)
   {
      return getURLFor(email, 140, GravatarDefault.MYSTERY_MAN, GravatarRating.G);
   }

   public String getURLFor(final String email, final int size)
   {
      return getURLFor(email, size, GravatarDefault.MYSTERY_MAN, GravatarRating.G);
   }

   public String getURLFor(final String email, final int size, final GravatarDefault deflt, final GravatarRating rating)
   {
      Assert.isTrue((size >= 1) && (size <= 200), "The Gravatar must be anywhere from 1px to 200px in size");
      Assert.notNull(email != null, "The email address provided was null");
      Assert.notNull(deflt, "Must supply a Gravatar default for when the email address is not registered");
      Assert.notNull(rating, "Must supply a Gravatar rating");
      Assert.isTrue(StringValidations.isEmailAddress(email), "The email address provided was invalid");
      try
      {
         String hash = getMD5(email);

         String url = GRAVATAR_BASE_URL + hash + "?s=" + size + "&d=" + deflt + "&r=" + rating;
         return url;
      }
      catch (Exception e)
      {
         throw new RuntimeException("Could not build MD5 checksum for User email [" + email + "]", e);
      }
   }

   private String getMD5(final String message)
   {
      try
      {
         MessageDigest md =
                  MessageDigest.getInstance("MD5");
         return hex(md.digest(message.getBytes("CP1252")));
      }
      catch (NoSuchAlgorithmException e)
      {
      }
      catch (UnsupportedEncodingException e)
      {
      }
      return null;
   }

   private String hex(final byte[] array)
   {
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < array.length; ++i)
      {
         sb.append(Integer.toHexString((array[i]
                  & 0xFF) | 0x100).substring(1, 3));
      }
      return sb.toString();
   }

}
