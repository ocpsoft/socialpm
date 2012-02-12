package com.ocpsoft.socialpm.gwt.client.local.view.events;

import com.google.gwt.event.shared.EventHandler;
import com.ocpsoft.socialpm.model.user.Profile;

public interface SignedInHandler extends EventHandler
{
   public void handleSignedIn(Profile profile);
}
