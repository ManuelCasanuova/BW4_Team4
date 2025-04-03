package societatrasporti.DAO;

import jakarta.persistence.EntityManager;
import lombok.Data;
import societatrasporti.classi.vendita.PuntoVendita;

import java.util.List;

@Data
public class PuntoVenditaDAO {
    private EntityManager em;

    public PuntoVenditaDAO(EntityManager em) {
        this.em = em;
    }


    public void SalvaPuntiVendita(PuntoVendita pv) {
        em.persist(pv);
       /* System.out.println("Punto vendita " + pv.getId() + " salvato correttamente");*/
    }

    public List<PuntoVendita> ottieniListaPuntiVendita() {
        return em.createQuery("SELECT p FROM PuntoVendita p", PuntoVendita.class).getResultList();
    }

    public PuntoVendita findByIdPuntoVendita (Long id) {
        PuntoVendita cercato= em.find(PuntoVendita.class, id);
        if(cercato == null) throw new RuntimeException();
        return cercato;

    }
}
