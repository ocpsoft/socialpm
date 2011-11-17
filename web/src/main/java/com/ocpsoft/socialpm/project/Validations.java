/*
 * Copyright 2011 <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ocpsoft.socialpm.project;

import java.io.Serializable;

import javax.ejb.TransactionAttribute;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.ocpsoft.socialpm.cdi.Web;
import com.ocpsoft.socialpm.domain.project.stories.ValidationCriteria;
import com.ocpsoft.socialpm.domain.user.Profile;
import com.ocpsoft.socialpm.model.ProfileService;
import com.ocpsoft.socialpm.model.project.ValidationService;
import com.ocpsoft.socialpm.security.Profiles;

/**
 * @author <a href="mailto:bleathem@gmail.com">Brian Leathem</a>
 * 
 */
@Named
@ConversationScoped
public class Validations implements Serializable
{
   private static final long serialVersionUID = -6828711689148386870L;

   private Stories stories;

   private Profiles profiles;

   private ProfileService prs;

   private ValidationService vs;

   private EntityManager em;

   public Validations()
   {}

   @Inject
   public Validations(final @Web EntityManager em, final ValidationService vs, final ProfileService prs,
            final Profiles profiles,
            final Stories stories)
   {
      this.profiles = profiles;
      this.stories = stories;
      this.prs = prs;
      this.vs = vs;
      this.em = em;
      this.vs.setEntityManager(em);
      this.prs.setEntityManager(em);
   }

   @TransactionAttribute
   public void createAjax()
   {
      vs.create(stories.getCurrent(), current);
      current = new ValidationCriteria();
   }

   @TransactionAttribute
   public void acceptAjax(final ValidationCriteria v)
   {
      profiles.setEntityManager(em);
      Profile profile = profiles.getLoggedIn();
      vs.accept(profile, v);
   }

   @TransactionAttribute
   public void rejectAjax(final ValidationCriteria v)
   {
      profiles.setEntityManager(em);
      Profile profile = profiles.getLoggedIn();
      vs.reject(profile, v);
   }

   private ValidationCriteria current = new ValidationCriteria();

   @Produces
   @Named("validation")
   @RequestScoped
   public ValidationCriteria getCurrent()
   {
      return current;
   }

   public void setCurrent(final ValidationCriteria current)
   {
      this.current = current;
   }
}
