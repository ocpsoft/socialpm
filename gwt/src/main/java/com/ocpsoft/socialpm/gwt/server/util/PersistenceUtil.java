package com.ocpsoft.socialpm.gwt.server.util;

import java.io.Serializable;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.ocpsoft.common.util.Assert;

import com.ocpsoft.socialpm.model.PersistentObject;

@TransactionAttribute
public abstract class PersistenceUtil implements Serializable
{
   private static final long serialVersionUID = -276417828563635020L;

   public abstract EntityManager getEntityManager();

   protected <T extends PersistentObject<?>> long count(final Class<T> type)
   {
      CriteriaBuilder qb = getEntityManager().getCriteriaBuilder();
      CriteriaQuery<Long> cq = qb.createQuery(Long.class);
      cq.select(qb.count(cq.from(type)));
      return getEntityManager().createQuery(cq).getSingleResult();
   }

   protected <T extends PersistentObject<?>> void create(final T entity)
   {
      getEntityManager().persist(entity);
   }

   protected <T extends PersistentObject<?>> void delete(final T entity) throws NoResultException
   {
      getEntityManager().remove(entity);
   }

   protected <T extends PersistentObject<?>> T deleteById(final Class<T> type, final Long id) throws NoResultException
   {
      T object = findById(type, id);
      delete(object);
      return object;
   }

   @SuppressWarnings("unchecked")
   protected <T extends PersistentObject<?>> T findById(final Class<T> type, final Long id) throws NoResultException
   {
      Class<?> clazz = getObjectClass(type);
      T result = (T) getEntityManager().find(clazz, id);
      if (result == null)
      {
         throw new NoResultException("No object of type: " + type + " with ID: " + id);
      }
      return result;
   }

   protected <T extends PersistentObject<?>> void save(final T entity)
   {
      if (getEntityManager() == null)
      {
         throw new IllegalStateException("Must initialize EntityManager before using Services!");
      }

      getEntityManager().merge(entity);
   }

   protected <T extends PersistentObject<?>> void refresh(final T entity)
   {
      getEntityManager().refresh(entity);
   }

   @SuppressWarnings("unchecked")
   protected <T extends PersistentObject<?>> T reload(final T entity)
   {
      Assert.notNull(entity.getId(), "Cannot reload @Entity with null ID [" + entity + "]");
      return (T) findById(entity.getClass(), entity.getId());
   }

   protected Class<?> getObjectClass(final Object type) throws IllegalArgumentException
   {
      Class<?> clazz = null;
      if (type == null)
      {
         throw new IllegalArgumentException("Null has no type. You must pass an Object");
      }
      else if (type instanceof Class<?>)
      {
         clazz = (Class<?>) type;
      }
      else
      {
         clazz = type.getClass();
      }
      return clazz;
   }

   @SuppressWarnings("unchecked")
   protected <T extends PersistentObject<?>> List<T> findByNamedQuery(final String namedQueryName)
   {
      return getEntityManager().createNamedQuery(namedQueryName).getResultList();
   }

   @SuppressWarnings("unchecked")
   protected <T extends PersistentObject<?>> List<T> findByNamedQuery(final String namedQueryName,
            final Object... params)
   {
      Query query = getEntityManager().createNamedQuery(namedQueryName);
      int i = 1;
      for (Object p : params)
      {
         query.setParameter(i++, p);
      }
      return query.getResultList();
   }

   protected <T extends PersistentObject<?>> List<T> findAll(final Class<T> type)
   {
      CriteriaQuery<T> query = getEntityManager().getCriteriaBuilder().createQuery(type);
      query.from(type);
      return getEntityManager().createQuery(query).getResultList();
   }

   @SuppressWarnings("unchecked")
   protected <T extends PersistentObject<?>> T findUniqueByNamedQuery(final String namedQueryName)
            throws NoResultException
   {
      return (T) getEntityManager().createNamedQuery(namedQueryName).getSingleResult();
   }

   @SuppressWarnings("unchecked")
   protected <T extends PersistentObject<?>> T findUniqueByNamedQuery(final String namedQueryName,
            final Object... params)
            throws NoResultException
   {
      Query query = getEntityManager().createNamedQuery(namedQueryName);
      int i = 1;
      for (Object p : params)
      {
         query.setParameter(i++, p);
      }

      return (T) query.getSingleResult();
   }
}