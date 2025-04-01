package societa.trasporti.manutenzione;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.Data;
import societa.trasporti.exception.ManutenzioneOrServizioException;
import societa.trasporti.parchiMezzi.ParcoMezzi;

import java.time.LocalDate;
import java.util.Optional;

@Data
public class ManutenzioneDAO {

    private static EntityManager em = null;

    public ManutenzioneDAO(EntityManager entityManager) {
        this.em = entityManager;
    }

    public void mettiInManutenzione(ParcoMezzi parcoMezzi, TipoManutenzione tipoManutenzione) {
        if (parcoMezzi.isInServizio()) throw new ManutenzioneOrServizioException("in servizio", true);
        if (parcoMezzi.isInManutenzione()) throw new ManutenzioneOrServizioException("già in manutenzione", true);
        parcoMezzi.setInManutenzione(true);
        Manutenzione manutenzione = new Manutenzione(tipoManutenzione, parcoMezzi);
        em.persist(manutenzione);
        System.out.println("Il veicolo " + manutenzione.getParcoMezzi().getMatricola() + " è in manutenzione");
    }

    public void salvaManutenzione(Manutenzione manutenzione) {
        EntityTransaction transaction = em.getTransaction();
        em.persist(manutenzione);
    }

    public long contaManutenzioniPerMezzo(ParcoMezzi veicolo) {
        return em.createQuery("SELECT COUNT(m) FROM Manutenzione m WHERE m.parcoMezzi = :veicolo", Long.class)
                .setParameter("veicolo", veicolo)
                .getSingleResult();
    }

    public void terminaManutenzione(ParcoMezzi parcoMezzi) {
        if(parcoMezzi.isInServizio()){
            throw new ManutenzioneOrServizioException("Il veicolo è in servizio e non può terminare la manutenzione", false);
        }
        if(!parcoMezzi.isInManutenzione()){
            throw new ManutenzioneOrServizioException("Il veicolo non è in manutenzione", false);
        }
        parcoMezzi.setInManutenzione(false);

        Optional<Manutenzione> ricerca = parcoMezzi.getManutenzionList().stream().filter(manutenzione -> manutenzione.getDataFine() == null).findFirst();

        ricerca.ifPresent(manutenzione -> manutenzione.setDataFine(LocalDate.now()));
        em.persist(parcoMezzi);
        System.out.println("Il veicolo " + parcoMezzi.getMatricola() + " è fuori manutenzione");
    }


}
