package com.ocpsoft.socialpm.security;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.jboss.seam.security.management.picketlink.IdentitySessionProducer;
import org.jboss.seam.transaction.Transactional;
import org.jboss.solder.servlet.WebApplication;
import org.jboss.solder.servlet.event.Initialized;
import org.picketlink.idm.api.IdentitySession;
import org.picketlink.idm.api.IdentitySessionFactory;
import org.picketlink.idm.api.User;
import org.picketlink.idm.common.exception.IdentityException;

import com.ocpsoft.socialpm.domain.security.IdentityObjectCredentialType;
import com.ocpsoft.socialpm.domain.security.IdentityObjectType;
import com.ocpsoft.socialpm.domain.user.Profile;

/**
 * Validates that the database contains the minimum required entities to function
 * 
 * @author Shane Bryzak
 */
public class InitializeDatabase
{
   @PersistenceContext(type = PersistenceContextType.EXTENDED)
   private EntityManager entityManager;

   @Inject
   private IdentitySessionFactory identitySessionFactory;

   @Transactional
   public void validate(@Observes @Initialized final WebApplication webapp) throws IdentityException
   {
      validateIdentityObjectTypes();
      validateSecurity();
   }

   private void validateIdentityObjectTypes()
   {
      if (entityManager.createQuery("select t from IdentityObjectType t where t.name = :name")
               .setParameter("name", "USER")
               .getResultList().size() == 0) {

         IdentityObjectType user = new IdentityObjectType();
         user.setName("USER");
         entityManager.persist(user);
      }

      if (entityManager.createQuery("select t from IdentityObjectType t where t.name = :name")
               .setParameter("name", "GROUP")
               .getResultList().size() == 0) {

         IdentityObjectType group = new IdentityObjectType();
         group.setName("GROUP");
         entityManager.persist(group);
      }
   }

   private void validateSecurity() throws IdentityException
   {
      // Validate credential types
      if (entityManager.createQuery("select t from IdentityObjectCredentialType t where t.name = :name")
               .setParameter("name", "PASSWORD")
               .getResultList().size() == 0) {

         IdentityObjectCredentialType PASSWORD = new IdentityObjectCredentialType();
         PASSWORD.setName("PASSWORD");
         entityManager.persist(PASSWORD);
      }

      Map<String, Object> sessionOptions = new HashMap<String, Object>();
      sessionOptions.put(IdentitySessionProducer.SESSION_OPTION_ENTITY_MANAGER, entityManager);

      IdentitySession session = identitySessionFactory.createIdentitySession("default", sessionOptions);

      /*
       * Create our test user (me!)
       */
      if (session.getPersistenceManager().findUser("lincoln") == null) {
         User u = session.getPersistenceManager().createUser("lincoln");
         session.getAttributesManager().updatePassword(u, "password");
         session.getAttributesManager().addAttribute(u, "email", "lincoln@ocpsoft.com");

         Profile p = new Profile();
         p.setEmail("lincoln@ocpsoft.com");
         p.setUsername("lincoln");
         p.getKeys().add(u.getKey());
         p.setUsernameConfirmed(true);
         entityManager.persist(p);
      }
   }
}
