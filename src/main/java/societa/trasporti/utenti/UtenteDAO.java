package societa.trasporti.utenti;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UtenteDAO {
    private EntityManager em;

    // metodo per fare il find di un utente
    public Utente findUtente(Long id) {
        return em.find(Utente.class, id);
    }

    // metodo per salvare un utente
    public void save(Utente u) {
        em.persist(u);
    }

    // metodo per cancellare un utente dato un id
    public void DeleteById (Long id) {
        Utente u = em.find(Utente.class, id);
        em.remove(u);
    }

}
