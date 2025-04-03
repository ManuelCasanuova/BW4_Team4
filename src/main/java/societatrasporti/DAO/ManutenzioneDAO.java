package societatrasporti.DAO;

import jakarta.persistence.EntityManager;
import lombok.Data;
import societatrasporti.exception.ManutenzioneOrServizioException;
import societatrasporti.classi.manutenzione.Manutenzione;
import societatrasporti.classi.manutenzione.TipoManutenzione;
import societatrasporti.classi.parchiMezzi.ParcoMezzi;

import java.time.LocalDate;
import java.util.Optional;

@Data
public class ManutenzioneDAO {

    private static EntityManager em = null;

    public ManutenzioneDAO(EntityManager em) {
        this.em = em;
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
        em.persist(manutenzione);
    }

    public long contaManutenzioniPerMezzo(ParcoMezzi veicolo) {
        return em.createQuery("SELECT COUNT(m) FROM Manutenzione m WHERE m.parcoMezzi = :veicolo", Long.class)
                .setParameter("veicolo", veicolo)
                .getSingleResult();
    }

    public void terminaManutenzione(ParcoMezzi parcoMezzi) {
        if(parcoMezzi.isInServizio() || !parcoMezzi.isInManutenzione())
            throw new ManutenzioneOrServizioException("Il veicolo è in servizio e non può terminare la manutenzione", false);
        parcoMezzi.setInManutenzione(false);
        Optional<Manutenzione> ricerca = parcoMezzi.getManutenzionList().stream().filter(manutenzione -> manutenzione.getDataFine() == null).findFirst();
        ricerca.ifPresent(manutenzione -> manutenzione.setDataFine(LocalDate.now()));
        em.persist(parcoMezzi);
        System.out.println("Il veicolo " + parcoMezzi.getMatricola() + " è fuori manutenzione");
    }


}
