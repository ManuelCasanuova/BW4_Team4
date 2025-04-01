package societa.trasporti.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import societa.trasporti.utenti.Tessera;
import societa.trasporti.utenti.Utente;

import java.time.LocalDate;
import java.util.List;

public class TesseraDAO {
    private final EntityManager entityManager;

    public TesseraDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Tessera tessera) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(tessera);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Tessera findById(Long id) {
        return entityManager.find(Tessera.class, id);
    }

    public List<Tessera> findAll() {
        return entityManager.createQuery("SELECT t FROM Tessera t", Tessera.class).getResultList();
    }

    public Tessera findByNumeroTessera(String numeroTessera) {
        try {
            return entityManager.createQuery("SELECT t FROM Tessera t WHERE t.numeroTessera = :numeroTessera", Tessera.class)
                    .setParameter("numeroTessera", numeroTessera)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public boolean rinnovaTessera(Long idTessera, LocalDate nuovaScadenza) {
        Tessera tessera = findById(idTessera);
        if (tessera == null || nuovaScadenza == null || !nuovaScadenza.isAfter(LocalDate.now())) {
            return false;
        }
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            tessera.setDataDiScadenza(nuovaScadenza);
            entityManager.merge(tessera);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public boolean isValid(Long idTessera) {
        Tessera tessera = findById(idTessera);
        return tessera != null && tessera.getDataDiScadenza() != null && tessera.getDataDiScadenza().isAfter(LocalDate.now());
    }

    public List<Tessera> findByUtente(Long idUtente) {
        return entityManager.createQuery("SELECT t FROM Tessera t WHERE t.utente.id = :idUtente", Tessera.class)
                .setParameter("idUtente", idUtente)
                .getResultList();
    }

    public void delete(Long idTessera) {
        Tessera tessera = findById(idTessera);
        if (tessera != null) {
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.remove(tessera);
                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw e;
            }
        }
    }
}
