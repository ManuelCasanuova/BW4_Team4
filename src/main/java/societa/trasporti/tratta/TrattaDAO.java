package societa.trasporti.tratta;

import jakarta.persistence.*;
import societa.trasporti.exception.TrattaPercorsaException;

import java.util.List;
import java.util.Optional;

public class TrattaDAO {

    private EntityManager em;

    public TrattaDAO(EntityManager em) {
        this.em = em;
    }

    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    // trova tratta per id
    public Optional<Tratta> findById(Long id) {
        try {
            return Optional.ofNullable(em.find(Tratta.class, id));
        } catch (Exception e) {
            throw new TrattaPercorsaException();
        }
    }

    // trova tratte per zona di partenza
    public List<Tratta> findByZonaPartenza(String zonaPartenza) {
        try {
            TypedQuery<Tratta> query = em.createQuery(
                    "SELECT t FROM Tratta t WHERE t.zonaPartenza = :zonaPartenza", Tratta.class);
            query.setParameter("zonaPartenza", zonaPartenza);
            return query.getResultList();
        } catch (Exception e) {
            throw new TrattaPercorsaException();
        }
    }

    // trova tratte per zona di arrivo
    public List<Tratta> findByZonaArrivo(String zonaArrivo) {
        try {
            TypedQuery<Tratta> query = em.createQuery(
                    "SELECT t FROM Tratta t WHERE t.zonaArrivo = :zonaArrivo", Tratta.class);
            query.setParameter("zonaArrivo", zonaArrivo);
            return query.getResultList();
        } catch (Exception e) {
            throw new TrattaPercorsaException();
        }
    }

    // salva tratte
    public void save(Tratta tratta) {
        try {
            em.getTransaction().begin();
            em.persist(tratta);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new TrattaPercorsaException();
        }
    }

    // aggiorna una tratta
    public void update(Tratta tratta) {
        try {
            em.getTransaction().begin();
            em.merge(tratta);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new TrattaPercorsaException();
        }
    }

    // elimina una tratta
    public void delete(Long id) {
        try {
            em.getTransaction().begin();
            Tratta tratta = em.find(Tratta.class, id);
            if (tratta != null) {
                em.remove(tratta);
            } else {
                throw new TrattaPercorsaException();
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new TrattaPercorsaException();
        }
    }
}