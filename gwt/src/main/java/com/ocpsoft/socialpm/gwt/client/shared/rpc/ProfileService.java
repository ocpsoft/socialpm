package com.ocpsoft.socialpm.gwt.client.shared.rpc;

import org.jboss.errai.bus.server.annotations.Remote;

import com.ocpsoft.socialpm.model.user.Profile;

@Remote
public interface ProfileService
{
   public Profile getProfileByUsername(String username);

   public Profile getProfileByIdentityKey(String key);

   public Profile save(Profile profile);
}