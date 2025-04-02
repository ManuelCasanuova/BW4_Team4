package societa.trasporti.utenti;

import jakarta.persistence.EntityManager;
import societa.trasporti.exception.NotFoundException;
import societa.trasporti.titoloViaggio.abbonamento.Abbonamento;

import java.time.LocalDate;
import java.util.List;

public class TesseraDAO {

    private EntityManager em;

    public TesseraDAO(EntityManager em) {
        this.em = em;
    }

    public void salvaTessera(Tessera tessera) {
        em.persist(tessera);
        System.out.println("Tessera " + tessera.getId() + " acquistata correttamente");
    }

    public List<Tessera> ottieniListaTessere() {
        return em.createQuery("SELECT t FROM Tessera t", Tessera.class).getResultList();
    }

    public Tessera findTesseraById(Long id) {
        Tessera cercato = em.find(Tessera.class, id);
        if (cercato == null) throw new NotFoundException("tessera", "id");
        return cercato;
    }

    public void rinnovaTessera(Utente utente) {
        if (utente.getTessera() == null) throw new NotFoundException("tessera", "utente");
        Tessera cercata = em.find(Tessera.class, utente.getTessera().getId());
        if (cercata.getDataDiScadenza().isAfter(LocalDate.now()))
            System.out.println("La tessera è ancora in corso di validità");
        else {
            cercata.setDataDiScadenza(LocalDate.now().plusYears(1));
            em.persist(cercata);

        }
    }

    public boolean verificaValiditaAbbonamento(Tessera tessera) {
        return !em
                .createQuery("SELECT a FROM Abbonamento a WHERE a.tessera = :tessera AND a.dataFine > :date", Abbonamento.class)
                .setParameter("tessera", tessera)
                .setParameter("date", LocalDate.now())
                .getResultList()
                .isEmpty();
    }
}
