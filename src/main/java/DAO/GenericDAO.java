package DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public abstract class GenericDAO<T> {

    private final EntityManager entityManager;

    public GenericDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    EntityManager getEntityManager() {
        if (!entityManager.isOpen()) {
            throw new IllegalStateException("EntityManager è chiuso.");
        }
        return entityManager;
    }

    public void save(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            if (!transaction.isActive()) {
                transaction.begin();
            }
            if (!entityManager.contains(entity)) {
                entity = entityManager.merge(entity);
            } else {
                entityManager.persist(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void update(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            if (!transaction.isActive()) {
                transaction.begin();
            }
            entityManager.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void delete(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            if (!transaction.isActive()) {
                transaction.begin();
            }
            if (!entityManager.contains(entity)) {
                entity = entityManager.merge(entity);
            }
            entityManager.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public T findById(Class<T> classe, Long id) {
        return entityManager.find(classe, id);
    }
}