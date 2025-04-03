package societatrasporti.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import societatrasporti.exception.NotFoundException;
import societatrasporti.classi.titoloViaggio.abbonamento.Abbonamento;
import societatrasporti.classi.utenti.Tessera;

import java.time.LocalDate;
import java.util.List;

public class TesseraDAO {

    private EntityManager em;

    public TesseraDAO(EntityManager em) {
        this.em = em;
    }

    public void salvaTessera(Tessera tessera) {
        em.persist(tessera);
        /*System.out.println("Tessera " + tessera.getId() + " acquistata correttamente");*/
    }

    public List<Tessera> ottieniListaTessere() {
        return em.createQuery("SELECT t FROM Tessera t", Tessera.class).getResultList();
    }

    public Tessera findTesseraById(Long id) {
        Tessera cercato = em.find(Tessera.class, id);
        if (cercato == null) throw new NotFoundException("tessera", "id");
        return cercato;
    }

    public boolean verificaValiditaAbbonamento(Tessera tessera){
        TypedQuery<Abbonamento> query = em.createQuery("SELECT a FROM Abbonamento a WHERE a.tessera = :tessera AND a.dataFine > :date ", Abbonamento.class);
        query.setParameter("tessera", tessera);
        query.setParameter("date", LocalDate.now());
        return !query.getResultList().isEmpty();
    }
}
