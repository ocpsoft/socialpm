package com.ocpsoft.socialpm.util;

import java.util.UUID;

public class RandomGenerator
{

   public static String makeString()
   {
      return UUID.randomUUID().toString();
   }

   public static int makeInt()
   {
      return UUID.randomUUID().hashCode();
   }

}
