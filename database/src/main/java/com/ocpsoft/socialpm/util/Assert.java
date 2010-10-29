package com.ocpsoft.socialpm.util;

public abstract class Assert
{

   public static void isTrue(final boolean expression)
   {
      isTrue(expression, "Expected [true], but was [false]");
   }

   public static void isTrue(final boolean test, final String message)
   {
      if (!test)
      {
         throw new IllegalArgumentException(message);
      }
   }

   public static void notNull(final Object o, final String message)
   {
      if (o == null)
      {
         throw new IllegalArgumentException(message);
      }
   }

   public static void notNull(final Object o)
   {
      notNull(o, "Expected [not null], but was [null]");
   }
}
