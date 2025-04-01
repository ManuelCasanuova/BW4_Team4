package societa.trasporti.parchiMezzi;

import jakarta.persistence.EntityManager;
import societa.trasporti.exception.NotFoundException;

import java.util.List;

public class ParcoMezziDAO  {
    private EntityManager em;

    public ParcoMezziDAO(EntityManager em) {
        this.em = em;
    }

        public void salvaMezzo(ParcoMezzi parcoMezzi){
        em.merge(parcoMezzi);
        System.out.println("Veicolo " + parcoMezzi.getMatricola() + " salvato correttamente");
        }

        public ParcoMezzi findMezzoById (Long matricola){
        ParcoMezzi cercato= em.find(ParcoMezzi.class, matricola);
        if(cercato == null) throw new NotFoundException();
        }


}