package societa.trasporti.DAO;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import societa.trasporti.vendita.distributori.Distributore;


import java.util.List;

@AllArgsConstructor
public class DistributoreAutomaticoDAO {

    private EntityManager em;

    // meodo per fare il find di un distributore automatico
    public Distributore findDistributoreAutomatico(Long id) {
        return em.find(Distributore.class, id);
    }

    // metodo per salvare un distributore automatico
    public void save(Distributore d) {
        em.persist(d);
    }

    // metodo per cancellare un distributore automatico dato un id
    public void DeleteById (Long id) {
        Distributore d = em.find(Distributore.class, id);
        em.remove(d);
    }

    // metodo per modificare un distributore automatico
    public void update(Distributore d) {
        em.merge(d);
    }

    // metodo per fare la lista di tutti i distributori automatici
    public List<Distributore> findAll() {
        return em.createQuery("SELECT d FROM Distributore d", Distributore.class).getResultList();
    }

    // metodo per fare la lista di tutti i distributori automatici attivi
    public List<Distributore> findAllAttivi() {
        return em.createQuery("SELECT d FROM Distributore d WHERE d.inServizio = true", Distributore.class).getResultList();
    }

    // metodo per fare la lista di tutti i distributori automatici inattivi
    public List<Distributore> findAllInattivi() {
        return em.createQuery("SELECT d FROM Distributore d WHERE d.inServizio = false", Distributore.class).getResultList();
    }
}
