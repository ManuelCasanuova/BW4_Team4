package societa.trasporti.DAO;

import jakarta.persistence.*;
import societa.trasporti.exception.TrattaException;
import societa.trasporti.tratta.Tratta;
import java.util.List;

public class TrattaDAO extends GenericDAO<Tratta> {

    public TrattaDAO(EntityManager entityManager) {
        super(entityManager);
    }

    // trova tratte per zona di partenza
    public List<Tratta> findByZonaPartenza(String zonaPartenza) {
        try {
            TypedQuery<Tratta> query = getEntityManager().createQuery(
                    "SELECT t FROM Tratta t WHERE t.zonaPartenza = :zonaPartenza", Tratta.class);
            query.setParameter("zonaPartenza", zonaPartenza);
            return query.getResultList();
        } catch (Exception e) {
            throw new TrattaException("Errore nel recupero delle tratte per la zona di partenza: " + zonaPartenza, e);
        }
    }

    // trova tratte per zona di arrivo
    public List<Tratta> findByZonaArrivo(String zonaArrivo) {
        try {
            TypedQuery<Tratta> query = getEntityManager().createQuery(
                    "SELECT t FROM Tratta t WHERE t.zonaArrivo = :zonaArrivo", Tratta.class);
            query.setParameter("zonaArrivo", zonaArrivo);
            return query.getResultList();
        } catch (Exception e) {
            throw new TrattaException("Errore nel recupero delle tratte per la zona di arrivo: " + zonaArrivo, e);
        }
    }
}