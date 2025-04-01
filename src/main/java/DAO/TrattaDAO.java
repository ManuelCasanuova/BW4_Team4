package DAO;

import jakarta.persistence.EntityManager;
import societa.trasporti.parchiMezzi.Tratta;
import java.util.List;

public class TrattaDAO extends GenericDAO<Tratta> {

    public TrattaDAO(EntityManager entityManager) {
        super(entityManager);
    }

    // Metodo specifico: trova tratte per zona di partenza
    public List<Tratta> findByZonaPartenza(String zonaPartenza) {
        String query = "SELECT t FROM Tratta t WHERE t.zonaPartenza = :zonaPartenza";
        return getEntityManager()
                .createQuery(query, Tratta.class)
                .setParameter("zonaPartenza", zonaPartenza)
                .getResultList();
    }

    // Metodo specifico: trova tratte per zona di arrivo
    public List<Tratta> findByZonaArrivo(String zonaArrivo) {
        String query = "SELECT t FROM Tratta t WHERE t.zonaArrivo = :zonaArrivo";
        return getEntityManager()
                .createQuery(query, Tratta.class)
                .setParameter("zonaArrivo", zonaArrivo)
                .getResultList();
    }
}