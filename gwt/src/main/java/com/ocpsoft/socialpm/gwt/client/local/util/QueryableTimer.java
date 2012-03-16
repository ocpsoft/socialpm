package com.ocpsoft.socialpm.gwt.client.local.util;

import com.google.gwt.user.client.Timer;

public abstract class QueryableTimer extends Timer
{
   boolean running = false;

   public abstract void performTask();

   @Override
   public void run()
   {
      running = true;
      performTask();
   }

   @Override
   public void cancel()
   {
      super.cancel();
      running = false;
   }
   
   public boolean isRunning()
   {
      return running;
   }
}
