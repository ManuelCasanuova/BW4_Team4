package societatrasporti.DAO;

import jakarta.persistence.*;
import societatrasporti.exception.EmptyListException;
import societatrasporti.classi.tratta.Tratta;

import java.util.List;

public class TrattaDAO {

    private final EntityManager em;

    public TrattaDAO(EntityManager em) {
        this.em = em;
    }


    /*public Tratta findTratta(Long id) {
        Tratta found = em.find(Tratta.class, id);
        if (found == null) throw new RuntimeException("La tratta " + id + " non è stata trovata");
        return found;
    }*/

    public void saveTratta(Tratta tratta) {
        em.persist(tratta);
      /*  System.out.println("La tratta " + tratta.getNomeTratta() + " è stata creata con successo");*/

    }

    public List<Tratta> ottieniListaTratte() {
        return em.createQuery("SELECT t FROM Tratta t", Tratta.class).getResultList();
    }

    public List<Tratta> ottieniTratteScoperte(){
        TypedQuery<Tratta> query = em.createQuery("SELECT t FROM Tratta t WHERE NOT EXISTS (SELECT s FROM Servizio s WHERE s.tratta = t AND s.dataFine IS NULL)", Tratta.class);
        List<Tratta> result = query.getResultList();
        if(result.isEmpty()) throw new EmptyListException();
        return result;
    }
}