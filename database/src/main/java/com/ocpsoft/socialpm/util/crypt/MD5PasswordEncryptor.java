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
package com.ocpsoft.socialpm.util.crypt;

import java.security.MessageDigest;

import javax.enterprise.inject.Typed;

import org.apache.commons.codec.binary.Base64;

@Typed(PasswordEncryptor.class)
public class MD5PasswordEncryptor implements PasswordEncryptor
{
   private static final long serialVersionUID = 1422059557145039442L;

   @Override
   public String encodePassword(final String password, final Object salt)
   {
      try
      {
         MessageDigest digest = MessageDigest.getInstance("MD5");
         digest.reset();
         digest.update(salt.toString().getBytes());
         byte[] passwordHash = digest.digest(password.getBytes());

         Base64 encoder = new Base64();
         byte[] encoded = encoder.encode(passwordHash);
         return new String(encoded);
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
   }

   @Override
   public boolean isPasswordValid(final String cypherPass, final String password, final Object salt)
   {
      return cypherPass.equals(this.encodePassword(password, salt));
   }

}