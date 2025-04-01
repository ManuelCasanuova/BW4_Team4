package societa.trasporti.DAO;

import jakarta.persistence.EntityManager;
import sistemadistribuzione.DistributoreAutomatico;
import sistemadistribuzione.StatoDistributore;

import java.time.LocalDate;
import java.util.List;

public class DistributoreAutomaticoDAO extends GenericDAO<DistributoreAutomatico> {

    public DistributoreAutomaticoDAO(EntityManager entityManager) {
        super(entityManager);
    }

    // Metodo per trovare i distributori in base al loro stato (attivo o fuori servizio)
    public List<DistributoreAutomatico> findByStato(String stato) {
        String query = "SELECT d FROM DistributoreAutomatico d WHERE d.stato = :stato";
        return getEntityManager()
                .createQuery(query, DistributoreAutomatico.class)
                .setParameter("stato", stato)
                .getResultList();
    }

    // Metodo per aggiornare lo stato di un distributore
    public void updateStato(Long idDistributore, String nuovoStato) {
        DistributoreAutomatico distributore = findById(DistributoreAutomatico.class, idDistributore);
        if (distributore == null) {
            throw new IllegalArgumentException("Distributore con ID " + idDistributore + " non trovato.");
        }
        distributore.setStato(StatoDistributore.valueOf(nuovoStato));
        update(distributore);
    }

    // Metodo per ottenere le vendite effettuate da un distributore in un intervallo di tempo
    public List getVenditeByDistributore(Long idDistributore, LocalDate inizio, LocalDate fine) {
        String query = "SELECT v FROM Vendita v WHERE v.distributore.id = :idDistributore AND v.data BETWEEN :inizio AND :fine";
        return getEntityManager()
                .createQuery(query)
                .setParameter("idDistributore", idDistributore)
                .setParameter("inizio", inizio)
                .setParameter("fine", fine)
                .getResultList();
    }
}