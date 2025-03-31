package societa.trasporti.vendita;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PuntoVenditaDAO {
    private EntityManager em;


    public void SalvaPuntiVendita(PuntoVendita pv) {
        em.persist(pv);
        System.out.println("Punto vendita " + pv.getId() + " salvato correttamente");
    }

    public List<PuntoVendita> ottieniListaPuntiVendita() {
        return em.createQuery("from PuntoVendita", PuntoVendita.class).getResultList();
    }

    public PuntoVendita findByIdPuntoVendita (Long id) {
        PuntoVendita cercato= em.find(PuntoVendita.class, id);
        if(cercato == null) throw new RuntimeException();
        return cercato;

    }
}
