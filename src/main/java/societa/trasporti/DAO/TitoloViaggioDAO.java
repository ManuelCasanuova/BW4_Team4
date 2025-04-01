package societa.trasporti.DAO;


import jakarta.persistence.EntityManager;
import societa.trasporti.exception.ConvalidatoException;
import societa.trasporti.exception.NotFoundException;
import societa.trasporti.parchiMezzi.ParcoMezzi;
import societa.trasporti.titoloViaggio.TitoloViaggio;
import societa.trasporti.titoloViaggio.biglietto.Biglietto;

import java.time.LocalDate;
import java.util.List;

public class TitoloViaggioDAO {

    private static EntityManager em = null;

    public TitoloViaggioDAO(EntityManager entityManager) {
        this.em = entityManager;
    }

    // crea un titolo di viaggio
    public void creaTitoloViaggio(TitoloViaggio titoloViaggio) {
        em.persist(titoloViaggio);
    }

    // trova un titolo di viaggio tramite il codice univoco
    public TitoloViaggio findTitoloViaggioByCodiceUnivoco(Long codiceUnivoco) {
        return em.find(TitoloViaggio.class, codiceUnivoco);
    }

    // trova tutti i titoli di viaggio
    public List<TitoloViaggio> findAllTitoliViaggio() {
        return em.createQuery("SELECT t FROM TitoloViaggio t", TitoloViaggio.class).getResultList();
    }

    // trova tutti i biglietti
    public List<Biglietto> findAllBiglietti() {
        return em.createQuery("SELECT b FROM Biglietto b", Biglietto.class).getResultList();
    }

    // trova tutti i biglietti convalidati
    public List<Biglietto> findAllBigliettiConvalidati() {
        return em.createQuery("SELECT b FROM Biglietto b WHERE b.convalidato = true", Biglietto.class).getResultList();
    }

    // trova tutti i biglietti non convalidati
    public List<Biglietto> findAllBigliettiNonConvalidati() {
        return em.createQuery("SELECT b FROM Biglietto b WHERE b.convalidato = false", Biglietto.class).getResultList();
    }

    // controlla se un biglietto è convalidato
    public void convalidaBiglietto(Long codiceUnivoco) {
        Biglietto biglietto = em.find(Biglietto.class, codiceUnivoco);
        if (biglietto != null) {
            if (biglietto.isConvalidato()) {
                System.out.println("Biglietto convalidato");
            } else {
                System.out.println("Biglietto non convalidato");
            }
        } else {
            System.out.println("Biglietto non trovato");
        }
    }

    // obliteratrice
    public void obbliteratore (Long codiceUnivoco, ParcoMezzi parcoMezzi) {
        Biglietto biglietto = em.find(Biglietto.class, codiceUnivoco);
        if (biglietto == null){
            throw new NotFoundException("biglietto", "codiceUnivoco");
        }
        if(biglietto.isConvalidato()){
            throw new ConvalidatoException(codiceUnivoco, parcoMezzi);
        }
        biglietto.setConvalidato(true);
        biglietto.setParcoMezzi(parcoMezzi);
        em.merge(biglietto);
    }


    // trova tutti gli abbonamenti
    public List<TitoloViaggio> findAllAbbonamenti() {
        return em.createQuery("SELECT a FROM Abbonamento a", TitoloViaggio.class).getResultList();
    }

    // trova abbonamenti validi
    public List<TitoloViaggio> findAbbonamentiValidi() {
        LocalDate oggi = LocalDate.now();
        return em.createQuery("SELECT a FROM Abbonamento a WHERE a.dataInizio <= :oggi AND a.dataFine >= :oggi", TitoloViaggio.class)
                .setParameter("oggi", oggi)
                .getResultList();
    }

    // trova abbonamenti scaduti
    public List<TitoloViaggio> findAbbonamentiScaduti() {
        LocalDate oggi = LocalDate.now();
        return em.createQuery("SELECT a FROM Abbonamento a WHERE a.dataFine < :oggi", TitoloViaggio.class)
                .setParameter("oggi", oggi)
                .getResultList();
    }

    // trova gli abbonamenti per numero di tessera
    public List<TitoloViaggio> findAbbonamentiByNumeroTessera(Long numeroTessera) {
        return em.createQuery("SELECT a FROM Abbonamento a WHERE a.numeroTessera = :numeroTessera", TitoloViaggio.class)
                .setParameter("numeroTessera", numeroTessera)
                .getResultList();
    }

    // trova abbonamenti per utente
    public List<TitoloViaggio> findAbbonamentiByUtente(Long idUtente) {
        return em.createQuery("SELECT a FROM Abbonamento a WHERE a.utente.id = :idUtente", TitoloViaggio.class)
                .setParameter("idUtente", idUtente)
                .getResultList();
    }

    // lista dei titoli di viaggio per punto vendita
    public List<TitoloViaggio> findTitoliViaggioByPuntoVendita(Long idPuntoVendita) {
        return em.createQuery("SELECT t FROM TitoloViaggio t WHERE t.puntoVendita.id = :idPuntoVendita", TitoloViaggio.class)
                .setParameter("idPuntoVendita", idPuntoVendita)
                .getResultList();
    }



}