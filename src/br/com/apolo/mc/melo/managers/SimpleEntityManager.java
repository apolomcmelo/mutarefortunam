package br.com.apolo.mc.melo.managers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SimpleEntityManager {
    private EntityManager entityManager;
    private EntityManagerFactory factory;
    private String PERSISTENCE_UNIT_NAME = "mutarefortunam";
     
    public SimpleEntityManager(EntityManagerFactory factory) {
        this.factory = factory;
        this.entityManager = factory.createEntityManager();
    }
     
    public SimpleEntityManager(){
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        this.entityManager = factory.createEntityManager();
    }
 
    public void beginTransaction(){
        entityManager.getTransaction().begin();
    }
     
    public void commit(){
        entityManager.getTransaction().commit();
    }

    public void close(){
        entityManager.close();
        factory.close();
    }
     
    public void rollBack(){
        entityManager.getTransaction().rollback();
    }
     
    public EntityManager getEntityManager(){
        return entityManager;
    }
}