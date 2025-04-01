package societa.trasporti.servizi;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import societa.trasporti.exception.DAOException;
import societa.trasporti.exception.ServizioNotFoundException;

import java.time.LocalDate;
import java.util.List;

public class ServizioDAO {

    //metto EntityManagerFactory in private  static per evitare di creare nuove istanze ogni volta che si crea un ServizioDAO.
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("epicode");
    private final EntityManager em;

    // Inizializza un'istanza di EntityManager per interagire con il database
    public ServizioDAO() {
        em = emf.createEntityManager();
    }

    // Salva un nuovo servizio nel database
    public void save(Servizio servizio) {
        try {
            em.getTransaction().begin();
            em.persist(servizio);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            throw new DAOException("Errore nel salvataggio del servizio.", e);
        }
    }

    // Trova un servizio nel database dato il suo ID
    public Servizio findById(Long id) {
        Servizio servizio = em.find(Servizio.class, id);
        if (servizio == null) {
            throw new ServizioNotFoundException(id);
        }
        return servizio;
    }

    // Recupera tutti i servizi dal database
    public List<Servizio> findAll() {
        try {
            return em.createQuery("SELECT s FROM Servizio s", Servizio.class).getResultList();
        } catch (PersistenceException e) {
            throw new DAOException("Errore nel recupero dei servizi.", e);
        }
    }
    // Trova i servizi attivi in un determinato intervallo di tempo
    // trova correttamente i servizi attivi in un periodo, includendo anche quelli ancora in corso
    public List<Servizio> findByPeriodo(LocalDate inizio, LocalDate fine) {
        try {
            String query = """
                SELECT s FROM Servizio s 
                WHERE s.dataInizio <= :fine 
                AND (s.dataFine IS NULL OR s.dataFine >= :inizio)
            """;
            return em.createQuery(query, Servizio.class)
                    .setParameter("inizio", inizio)
                    .setParameter("fine", fine)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new DAOException("Errore nel recupero dei servizi per periodo.", e);
        }
    }

    // Aggiorna un servizio esistente nel database
    public void update(Servizio servizio) {
        try {
            em.getTransaction().begin();
            em.merge(servizio);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            throw new DAOException("Errore nell'aggiornamento del servizio.", e);
        }
    }

    // Elimina un servizio dal database
    public void delete(Servizio servizio) {
        try {
            em.getTransaction().begin();
            //remove(servizio) funziona solo se l'entità è gestita. Se non lo sono, la funzione merge la gestisce.
            em.remove(em.contains(servizio) ? servizio : em.merge(servizio));
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            throw new DAOException("Errore nella rimozione del servizio.", e);
        }
    }

    public void close() {
        em.close();
    }

    public static void closeEntityManagerFactory() {
        if (emf.isOpen()) {
            emf.close();
        }
    }


}