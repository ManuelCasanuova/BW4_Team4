package societa.trasporti.DAO;

import jakarta.persistence.EntityManager;
import utenti.Tessera;
import java.time.LocalDate;

public class TesseraDAO extends GenericDAO<Tessera> {

    public TesseraDAO(EntityManager entityManager) {
        super(entityManager);
    }

    // Metodo per trovare una tessera per numero tessera
    public Tessera findByNumeroTessera(String numeroTessera) {
        String query = "SELECT t FROM Tessera t WHERE t.numeroTessera = :numeroTessera";
        return getEntityManager()
                .createQuery(query, Tessera.class)
                .setParameter("numeroTessera", numeroTessera)
                .getSingleResult();
    }

    // Metodo per rinnovare una tessera (aggiornare la data di scadenza)
    public void rinnovaTessera(Long idTessera, LocalDate nuovaScadenza) {
        Tessera tessera = findById(Tessera.class, idTessera);
        if (tessera == null) {
            throw new IllegalArgumentException("Tessera con ID " + idTessera + " non trovata.");
        }
        tessera.setDataDiScadenza(nuovaScadenza);
        update(tessera);
    }

    // Metodo per verificare la validità di una tessera (controllo della data di scadenza)
    public boolean isValid(Long idTessera) {
        Tessera tessera = findById(Tessera.class, idTessera);
        return tessera != null && tessera.getDataDiScadenza().isAfter(LocalDate.now());
    }
}
