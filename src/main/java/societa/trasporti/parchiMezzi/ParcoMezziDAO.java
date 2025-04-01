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
        em.persist(parcoMezzi);
        System.out.println("Veicolo " + parcoMezzi.getMatricola() + " salvato correttamente");
        }

        public ParcoMezzi findMezzoById (Long matricola){
        ParcoMezzi cercato= em.find(ParcoMezzi.class, matricola);
        if(cercato == null) throw new NotFoundException("parcoMezzi", "matricola");
        return cercato;
        }

        public List<ParcoMezzi> findAllParcoMezzi() {
        return em.createQuery("SELECT p FROM ParcoMezzi p", ParcoMezzi.class).getResultList();
        }

        public boolean manutenzioniSvolte(ParcoMezzi parcoMezzi) {
        return em.createQuery("SELECT COUNT(m) FROM Manutenzione m WHERE m.parcoMezzi = :parcoMezzi", Long.class)
                .setParameter("parcoMezzi", parcoMezzi)
                .getSingleResult() > 0;
        }

        public boolean serviziSvolti(ParcoMezzi parcoMezzi) {
        return em.createQuery("SELECT COUNT(s) FROM Servizio s WHERE s.parcoMezzi = :parcoMezzi", Long.class)
                .setParameter("parcoMezzi", parcoMezzi)
                .getSingleResult() > 0;
        }

        public long contaManutenzioniPerMezzo(ParcoMezzi parcoMezzi) {
            return em.createQuery("SELECT COUNT(m) FROM Manutenzione m WHERE m.parcoMezzi = :parcoMezzi", Long.class)
                    .setParameter("parcoMezzi", parcoMezzi)
                    .getSingleResult();
        }

        public Long contaServiziPerMezzo(ParcoMezzi parcoMezzi) {
            return em.createQuery("SELECT COUNT(s) FROM Servizio s WHERE s.parcoMezzi = :parcoMezzi", Long.class)
                    .setParameter("parcoMezzi", parcoMezzi)
                    .getSingleResult();
        }


        public void storicoParcoMezzi(ParcoMezzi parcoMezzi) {
          long manutenzioniContate = contaManutenzioniPerMezzo(parcoMezzi);
          long serviziContati = contaServiziPerMezzo(parcoMezzi);

            System.out.println("Di seguito lo storico del veicolo" + parcoMezzi.getMatricola());
            System.out.println("Numero di manutenzioni" + manutenzioniContate);
            System.out.println("Numero di servizi" + serviziContati);
        }


}