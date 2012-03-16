package com.ocpsoft.socialpm.gwt.client.shared.rpc;

import java.util.List;

import org.jboss.errai.bus.server.annotations.Remote;

import com.ocpsoft.socialpm.model.project.Project;
import com.ocpsoft.socialpm.model.user.Profile;

@Remote
public interface ProjectService
{
   public List<Project> getByOwner(Profile owner);
   
   public Project getByOwnerAndSlug(Profile owner, String slug);

   public Project create(Profile owner, String projectName);

   public boolean verifyAvailable(Profile owner, String projectName);
}