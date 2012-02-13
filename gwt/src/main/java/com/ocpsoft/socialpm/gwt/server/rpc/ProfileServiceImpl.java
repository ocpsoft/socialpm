package com.ocpsoft.socialpm.gwt.server.rpc;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

import org.jboss.errai.bus.server.annotations.Service;

import com.ocpsoft.logging.Logger;
import com.ocpsoft.socialpm.gwt.client.shared.rpc.ProfileService;
import com.ocpsoft.socialpm.gwt.server.util.PersistenceUtil;
import com.ocpsoft.socialpm.model.user.Profile;

@RequestScoped
@Service
public class ProfileServiceImpl extends PersistenceUtil implements Serializable, ProfileService
{
   private static final long serialVersionUID = -4202553658870274615L;
   static Logger logger = Logger.getLogger(ProfileServiceImpl.class);

   @PersistenceContext(type = PersistenceContextType.EXTENDED)
   protected EntityManager em;

   @Override
   public EntityManager getEntityManager()
   {
      return em;
   }

   @Override
   public Profile getProfileByUsername(String username)
   {
      System.out.println("Query profile " + username);
      TypedQuery<Profile> query = em.createQuery("SELECT p FROM Profile p WHERE p.username = :username", Profile.class);
      query.setParameter("username", username);

      Profile result = query.getSingleResult();
      return result;
   }

   @Override
   public Profile getProfileByIdentityKey(final String key) throws NoResultException
   {
      TypedQuery<Profile> query = em.createQuery(
               "SELECT p FROM Profile p JOIN p.identityKeys k WHERE k = :identityKey", Profile.class);
      query.setParameter("identityKey", key);

      Profile result = query.getSingleResult();
      return result;
   }

}