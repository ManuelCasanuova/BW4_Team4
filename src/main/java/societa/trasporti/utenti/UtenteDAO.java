package societa.trasporti.utenti;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UtenteDAO {
    private EntityManager em;

    // metodo per fare il find di un utente
    public Utente findUtente(Long id) {
        Utente cercato = em.find(Utente.class, id);
        if (cercato ==null) throw new RuntimeException();
        return cercato;
    }

    // metodo per salvare un utente
    public void save(Utente utente) {
        em.persist(utente);
        System.out.println("Utente " + utente.getNominativo() + " inserito correttamente");
    }

 /*   // metodo per cancellare un utente dato un id
    public void DeleteById (Long id) {
        Utente u = em.find(Utente.class, id);
        em.remove(u);
    }*/

    public List <Utente> listaTuttiUtenti() {
        return em.createQuery("SELECT u FROM Utente u", Utente.class).getResultList();
    }
}
