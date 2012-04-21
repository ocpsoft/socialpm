package com.ocpsoft.socialpm.gwt.client.local.view.forms;

import com.google.gwt.text.shared.AbstractRenderer;
import com.ocpsoft.socialpm.model.user.Profile;

public class ProfileRenderer extends AbstractRenderer<Profile>
{
   @Override
   public String render(Profile object)
   {
      if (object == null)
         return "";
      return object.getUsername();
   }

}
