package DAO;

import jakarta.persistence.EntityManager;
import titoloViaggio.biglietto.Biglietto;
import sistemadistribuzione.SistemaEmettitore;
import java.time.LocalDate;
import java.util.List;

public class BigliettoDAO extends GenericDAO<Biglietto> {

    public BigliettoDAO(EntityManager entityManager) {
        super(entityManager);
    }

    // Metodo per emettere un nuovo biglietto
    public Biglietto emettiBiglietto(boolean ridotto, SistemaEmettitore sistemaEmettitore) {
        Biglietto biglietto = new Biglietto();
        biglietto.setRidotto(ridotto);
        biglietto.setSistemaEmettitore(sistemaEmettitore);
        save(biglietto);
        return biglietto;
    }

    // Metodo per trovare i biglietti emessi in un determinato periodo
    public List<Biglietto> findByPeriodo(LocalDate inizio, LocalDate fine) {
        String query = "SELECT b FROM Biglietto b WHERE b.dataEmissione BETWEEN :inizio AND :fine";
        return getEntityManager()
                .createQuery(query, Biglietto.class)
                .setParameter("inizio", inizio)
                .setParameter("fine", fine)
                .getResultList();
    }

    // Metodo per trovare i biglietti emessi da un sistema emettitore
    public List<Biglietto> findBySistemaEmettitore(SistemaEmettitore sistemaEmettitore) {
        String query = "SELECT b FROM Biglietto b WHERE b.sistemaEmettitore = :sistemaEmettitore";
        return getEntityManager()
                .createQuery(query, Biglietto.class)
                .setParameter("sistemaEmettitore", sistemaEmettitore)
                .getResultList();
    }
}