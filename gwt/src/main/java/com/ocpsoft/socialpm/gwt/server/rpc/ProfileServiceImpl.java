package com.ocpsoft.socialpm.gwt.server.rpc;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.errai.bus.server.annotations.Service;

import com.google.inject.servlet.SessionScoped;
import com.ocpsoft.logging.Logger;
import com.ocpsoft.socialpm.gwt.client.shared.rpc.ProfileService;
import com.ocpsoft.socialpm.model.user.Profile;

@SessionScoped
@Service
public class ProfileServiceImpl implements Serializable, ProfileService
{
   private static final long serialVersionUID = -4202553658870274615L;
   static Logger logger = Logger.getLogger(ProfileServiceImpl.class);

   @PersistenceContext
   private EntityManager em;

   @PostConstruct
   public void setup()
   {}

   @Override
   public Profile getProfile(String username)
   {
      TypedQuery<Profile> query = em.createQuery("SELECT p FROM Profile p WHERE p.username = :username", Profile.class);
      query.setParameter("username", username);

      Profile result = query.getSingleResult();
      return result;
   }

}