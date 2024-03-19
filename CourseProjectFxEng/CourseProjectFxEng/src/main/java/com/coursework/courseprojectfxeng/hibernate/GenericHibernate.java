package com.coursework.courseprojectfxeng.hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;

import java.util.ArrayList;
import java.util.List;

public class GenericHibernate {

    private EntityManagerFactory entityManagerFactory;

    public GenericHibernate(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }


    //Persist = INSERT
    public <T> void create(T entity) {
        EntityManager entityManager = getEntityManager();
        try {

            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
        } catch (Exception e) {

        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    //merge = UPDATE

    public <T> void update(T entity) {
        EntityManager entityManager = getEntityManager();
        try {

            entityManager.getTransaction().begin();
            entityManager.merge(entity);
            entityManager.getTransaction().commit();
        } catch (Exception e) {

        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public <T> List<T> getAllRecords(Class<T> className) {
        List<T> listOfRecords = new ArrayList<>();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaQuery query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(className));
            Query q = entityManager.createQuery(query);
            listOfRecords = q.getResultList();
        } catch (Exception e) {

        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return listOfRecords;
    }

    public <T> void delete(Class<T> entityClass, int id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            var object = em.find(entityClass, id);
            em.remove(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
    }

    public <T> T getEntityById(Class<T> entityClass, int id) {
        EntityManager em = null;
        T result = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            result = em.find(entityClass, id);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
