package com.ocpsoft.socialpm.gwt.client.shared.rpc;

import java.util.List;

import org.jboss.errai.bus.server.annotations.Remote;

import com.ocpsoft.socialpm.model.project.Project;

@Remote
public interface ProjectService
{
   public List<Project> getProjectsByOwner(String username);
}