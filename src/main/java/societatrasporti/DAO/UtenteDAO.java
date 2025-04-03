package societatrasporti.DAO;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import societatrasporti.classi.utenti.Utente;

import java.util.List;

@AllArgsConstructor
public class UtenteDAO {
    private EntityManager em;

    public void save(Utente utente) {
        em.persist(utente);
/*
        System.out.println("Utente " + utente.getNominativo() + " inserito correttamente");
*/
    }


    public List <Utente> listaTuttiUtenti() {
        return em.createQuery("SELECT u FROM Utente u", Utente.class).getResultList();
    }
}
