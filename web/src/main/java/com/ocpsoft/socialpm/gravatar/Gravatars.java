/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
