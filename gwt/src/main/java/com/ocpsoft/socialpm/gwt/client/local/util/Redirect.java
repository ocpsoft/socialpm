package com.ocpsoft.socialpm.gwt.client.local.util;

import com.google.gwt.user.client.Window;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public class Redirect
{
   public static void to(String url)
   {
      Window.Location.assign(url);
   }

}