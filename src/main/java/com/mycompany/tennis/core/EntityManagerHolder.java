package com.mycompany.tennis.core;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class EntityManagerHolder {
    private static final ThreadLocal<EntityManager> entityManagerThreadLocal = new ThreadLocal<>();
    private static EntityManagerFactory entityManagerFactory = buildEntityManagerFactory();

    private static EntityManagerFactory buildEntityManagerFactory(){
        return Persistence.createEntityManagerFactory("tennis-unit");
    }

    private EntityManagerHolder(){

    }

    /**
     * @return The {@link EntityManager} linked to this thread
     */
    public static EntityManager getCurrentEntityManager() {
        EntityManager entityManager = entityManagerThreadLocal.get();

        if (entityManager == null) {

            // Start the conversation by creating the EntityManager for this thread
            entityManager = entityManagerFactory.createEntityManager();
            entityManagerThreadLocal.set(entityManager);

        }
        return entityManager;
    }

}