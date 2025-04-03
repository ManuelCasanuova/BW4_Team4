package societatrasporti.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import societatrasporti.exception.ManutenzioneOrServizioException;
import societatrasporti.exception.TrattaPercorsaException;
import societatrasporti.classi.parchiMezzi.ParcoMezzi;
import societatrasporti.classi.servizi.Servizio;
import societatrasporti.classi.tratta.Tratta;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ServizioDAO {

    private final EntityManager em;

    public ServizioDAO(EntityManager em) {
        this.em = em;
    }

    // Salva un nuovo servizio nel database
    public void saveServizio(Servizio servizio) {
            em.persist(servizio);
            /*System.out.println("Il servizio " + servizio.getId() + " è stato salvato correttamente.");*/
    }


    public Long numeroServiziPerMezzo (ParcoMezzi parcoMezzi){
        return em.createQuery("SELECT COUNT(s) FROM Servizio s WHERE s.parcoMezzi = :parcoMezzi", Long.class)
                .setParameter("parcoMezzi", parcoMezzi)
                .getSingleResult();
    }

    public List<Servizio> controlloServiziAttivi(){
        TypedQuery<Servizio> query = em.createQuery("SELECT s FROM Servizio s WHERE s.dataFine IS NULL", Servizio.class);
        return query.getResultList();
    }



    public void entrataInServizio (ParcoMezzi parcoMezzi, Tratta tratta){
        if(parcoMezzi.isInServizio()) throw new ManutenzioneOrServizioException("già in servizio", true);
        if(parcoMezzi.isInManutenzione()) throw new ManutenzioneOrServizioException("in manutenzione", true);
        if(tratta.getServiziList().stream().anyMatch(servizio1 -> servizio1.getDataFine() == null)) throw new TrattaPercorsaException();
        parcoMezzi.setInServizio(true);
        Servizio servizio = new Servizio(parcoMezzi, tratta);
        em.persist(servizio);
       /* System.out.println("Il servizio " + servizio.getId() + " è stato salvato correttamente.");*/

    }


    public void uscitaDalServizio(ParcoMezzi parcoMezzi){
        if(parcoMezzi.isInManutenzione() || !parcoMezzi.isInServizio()) throw new ManutenzioneOrServizioException("in servizio", false);
        parcoMezzi.setInServizio(false);
        Optional<Servizio> ricerca = parcoMezzi.getServiziList().stream().filter(servizio -> servizio.getDataFine() == null).findFirst();
        ricerca.ifPresent(servizio -> servizio.setDataFine(LocalDate.now()));
        em.persist(parcoMezzi);
        System.out.println("Il veicolo " + parcoMezzi.getMatricola() + " è stato fermato");
    }











}