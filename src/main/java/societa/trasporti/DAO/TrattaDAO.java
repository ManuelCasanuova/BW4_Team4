package societa.trasporti.DAO;

import jakarta.persistence.*;
import societa.trasporti.exception.TrattaException;
import societa.trasporti.tratta.Tratta;

import java.util.List;
import java.util.Optional;

public class TrattaDAO {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("trasportiPU");

    private EntityManager entityManager;

    public TrattaDAO() {
        this.entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    public void close() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }

    // trova tratta per id
    public Optional<Tratta> findById(Long id) {
        try {
            return Optional.ofNullable(entityManager.find(Tratta.class, id));
        } catch (Exception e) {
            throw new TrattaException("Errore nel recupero della tratta con ID: " + id, e);
        }
    }

    // trova tratte per zona di partenza
    public List<Tratta> findByZonaPartenza(String zonaPartenza) {
        try {
            TypedQuery<Tratta> query = entityManager.createQuery(
                    "SELECT t FROM Tratta t WHERE t.zonaPartenza = :zonaPartenza", Tratta.class);
            query.setParameter("zonaPartenza", zonaPartenza);
            return query.getResultList();
        } catch (Exception e) {
            throw new TrattaException("Errore nel recupero delle tratte per la zona di partenza: " + zonaPartenza, e);
        }
    }

    // trova tratte per zona di arrivo
    public List<Tratta> findByZonaArrivo(String zonaArrivo) {
        try {
            TypedQuery<Tratta> query = entityManager.createQuery(
                    "SELECT t FROM Tratta t WHERE t.zonaArrivo = :zonaArrivo", Tratta.class);
            query.setParameter("zonaArrivo", zonaArrivo);
            return query.getResultList();
        } catch (Exception e) {
            throw new TrattaException("Errore nel recupero delle tratte per la zona di arrivo: " + zonaArrivo, e);
        }
    }

    // salva tratte
    public void save(Tratta tratta) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(tratta);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new TrattaException("Errore durante il salvataggio della tratta", e);
        }
    }

    // aggiorna una tratta
    public void update(Tratta tratta) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(tratta);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new TrattaException("Errore durante l'aggiornamento della tratta", e);
        }
    }

    // elimina una tratta
    public void delete(Long id) {
        try {
            entityManager.getTransaction().begin();
            Tratta tratta = entityManager.find(Tratta.class, id);
            if (tratta != null) {
                entityManager.remove(tratta);
            } else {
                throw new TrattaException("Tratta con ID " + id + " non trovata");
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new TrattaException("Errore durante l'eliminazione della tratta con ID: " + id, e);
        }
    }
}