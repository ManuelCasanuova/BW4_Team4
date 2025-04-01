package DAO;

import jakarta.persistence.EntityManager;
import societa.trasporti.parchiMezzi.ParcoMezzi;
import societa.trasporti.parchiMezzi.TipoVeicolo;
import societa.trasporti.parchiMezzi.Tratta;
import java.util.List;

public class VeicoloDAO extends GenericDAO<ParcoMezzi> {

    public VeicoloDAO(EntityManager entityManager) {
        super(entityManager);
    }

    // Metodo per trovare tutti i mezzi per tipo (es. Tram o Autobus)
    public List<ParcoMezzi> findByTipoVeicolo(TipoVeicolo tipoVeicolo) {
        String query = "SELECT v FROM ParcoMezzi v WHERE v.tipoVeicolo = :tipoVeicolo";
        return getEntityManager()
                .createQuery(query, ParcoMezzi.class)
                .setParameter("tipoVeicolo", tipoVeicolo)
                .getResultList();
    }

    // Metodo per trovare i mezzi in base allo stato (in servizio o manutenzione)
    public List<ParcoMezzi> findByStato(boolean inServizio) {
        String query = "SELECT v FROM ParcoMezzi v WHERE v.inServizio = :inServizio";
        return getEntityManager()
                .createQuery(query, ParcoMezzi.class)
                .setParameter("inServizio", inServizio)
                .getResultList();
    }

    // Metodo per assegnare una tratta a un veicolo
    public void assegnaTratta(Long matricola, Long idTratta) {
        ParcoMezzi veicolo = findById(ParcoMezzi.class, matricola);
        Tratta tratta = getEntityManager().find(Tratta.class, idTratta);

        if (veicolo == null) {
            throw new IllegalArgumentException("Veicolo con matricola " + matricola + " non trovato.");
        }
        if (tratta == null) {
            throw new IllegalArgumentException("Tratta con ID " + idTratta + " non trovata.");
        }

        veicolo.setTratta(tratta);
        update(veicolo);
    }
}