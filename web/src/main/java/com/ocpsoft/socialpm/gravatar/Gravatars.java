/**
 * This file is part of OCPsoft SocialPM: Agile Project Management Tools (SocialPM) 
 *
 * Copyright (c)2011 Lincoln Baxter, III <lincoln@ocpsoft.com> (OCPsoft)
 * Copyright (c)2011 OCPsoft.com (http://ocpsoft.com)
 * 
 * If you are developing and distributing open source applications under 
 * the GNU General Public License (GPL), then you are free to re-distribute SocialPM 
 * under the terms of the GPL, as follows:
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
 * For individuals or entities who wish to use SocialPM privately, or
 * internally, the following terms do not apply:
 *  
 * For OEMs, ISVs, and VARs who wish to distribute SocialPM with their 
 * products, or host their product online, OCPsoft provides flexible 
 * OEM commercial licenses.
 * 
 * Optionally, Customers may choose a Commercial License. For additional 
 * details, contact an OCPsoft representative (sales@ocpsoft.com)
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
