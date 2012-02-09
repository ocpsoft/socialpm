package com.ocpsoft.socialpm.gwt.client.shared.rpc;

import org.jboss.errai.bus.server.annotations.Remote;

import com.ocpsoft.socialpm.model.user.Profile;

@Remote
public interface LoginService
{
   public Profile login(String username, String password);
}