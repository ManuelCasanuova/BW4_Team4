package societa.trasporti.servizi;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import societa.trasporti.exception.DAOException;
import societa.trasporti.exception.ManutenzioneOrServizioException;
import societa.trasporti.exception.ServizioNotFoundException;
import societa.trasporti.exception.TrattaPercorsaException;
import societa.trasporti.parchiMezzi.ParcoMezzi;
import societa.trasporti.tratta.Tratta;

import java.time.LocalDate;
import java.util.List;

public class ServizioDAO {

    private final EntityManager em;

    public ServizioDAO(EntityManager em) {
        this.em = em;
    }

    // Salva un nuovo servizio nel database
    public void save(Servizio servizio) {
            em.persist(servizio);
            System.out.println("Il servizio " + servizio.getId() + " è stato salvato correttamente.");
    }

    // Trova un servizio nel database dato il suo ID
    public Servizio findServizioById(Long id) {
        Servizio servizio = em.find(Servizio.class, id);
        if (servizio == null) {
            throw new ServizioNotFoundException(id);
        }
        return servizio;
    }

    // Recupera tutti i servizi dal database
    public List<Servizio> listaServizi() {
            return em.createQuery("SELECT s FROM Servizio s", Servizio.class).getResultList();
    }

    public Long numeroServiziPerMezzo (ParcoMezzi parcoMezzi){
        return em.createQuery("SELECT COUNT(s) FROM Servizio s WHERE s.parcoMezzi = :parcoMezzi", Long.class)
                .setParameter("parcoMezzi", parcoMezzi)
                .getSingleResult();
    }

    public static List<Servizio>  listaControlloServiziAttivi (EntityManager em) {
        return em.createQuery("SELECT s FROM Servizio s WHERE s.dataFine IS NULL", Servizio.class).getResultList();
    }


    public void entrataInServizio (ParcoMezzi parcoMezzi, Tratta tratta){
        if(parcoMezzi.isInServizio()) throw new ManutenzioneOrServizioException("già in servizio", true);
        if(parcoMezzi.isInManutenzione()) throw new ManutenzioneOrServizioException("in manutenzione", true);
        if(tratta.getServiziList().stream().anyMatch(servizio -> servizio.getDataFine() == null)) throw new TrattaPercorsaException();
        parcoMezzi.setInServizio(true);
        Servizio servizio = new Servizio(parcoMezzi, tratta);
        em.persist(servizio);
        System.out.println("Il servizio " + servizio.getId() + " è stato salvato correttamente.");

    }


    public void uscitaDalServizio(ParcoMezzi parcoMezzi){
        if(parcoMezzi.isInManutenzione()) throw new ManutenzioneOrServizioException("in manutenzione", true);
        parcoMezzi.setInServizio(false);
        Servizio servizio = em.createQuery("SELECT s FROM Servizio s WHERE s.parcoMezzi = :parcoMezzi AND s.dataFine IS NULL", Servizio.class)
                .setParameter("parcoMezzi", parcoMezzi)
                .getSingleResult();
        System.out.println("Il mezzo " + parcoMezzi.getMatricola() + " è uscito dal servizio " + servizio.getId() + ".");
    }

    // Trova i servizi attivi in un determinato intervallo di tempo
    // trova correttamente i servizi attivi in un periodo, includendo anche quelli ancora in corso
    public List<Servizio> findByPeriodo(LocalDate inizio, LocalDate fine) {
        try {
            return em.createQuery("SELECT s FROM Servizio s WHERE s.dataInizio <= :fine AND (s.dataFine IS NULL OR s.dataFine >= :inizio)", Servizio.class)
                    .setParameter("inizio", inizio)
                    .setParameter("fine", fine)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new DAOException("Errore nel recupero dei servizi per periodo.", e);
        }
    }








}