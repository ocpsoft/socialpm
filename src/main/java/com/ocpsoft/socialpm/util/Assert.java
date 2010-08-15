package com.ocpsoft.socialpm.util;

public abstract class Assert
{

   public static void isTrue(final boolean expression)
   {
      if (!expression)
      {
         throw new IllegalArgumentException("This expression must be true.");
      }
   }
}
