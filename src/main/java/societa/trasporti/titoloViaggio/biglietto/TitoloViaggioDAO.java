package societa.trasporti.titoloViaggio.biglietto;

import jakarta.persistence.EntityManager;
import societa.trasporti.exception.*;
import societa.trasporti.parchiMezzi.ParcoMezzi;
import societa.trasporti.titoloViaggio.TitoloViaggio;
import societa.trasporti.titoloViaggio.abbonamento.Abbonamento;
import societa.trasporti.utenti.Tessera;
import societa.trasporti.vendita.PuntoVendita;
import societa.trasporti.vendita.distributori.Distributore;

import java.time.LocalDate;
import java.util.List;

public class TitoloViaggioDAO {
    private final EntityManager em;


    public TitoloViaggioDAO(EntityManager entityManager) {
        this.em = entityManager;
    }

    public void salvaTitoloViaggio(TitoloViaggio titoloViaggio) {
        PuntoVendita puntoVendita = titoloViaggio.getPuntoVendita();
        if(puntoVendita instanceof Distributore){
            if (!((Distributore) puntoVendita).isAttivo()) throw new DistributoreFuoriServizioException();
        }
        if (titoloViaggio instanceof Abbonamento) {
            Abbonamento abbonamento = (Abbonamento) titoloViaggio;
            if (abbonamento.getTessera().getDataDiScadenza().isBefore(LocalDate.now())) {
                throw new TesseraScadutaException();
            }
            List<Abbonamento> abbonamentiList = getAllAbbonamentiByTessera(abbonamento.getTessera());
            if (!abbonamentiList.isEmpty()) {
                if (abbonamentiList.stream().anyMatch(a -> abbonamento.getDataInizio().isBefore(a.getDataFine()))) {
                    throw new AbbonamentoDateException();
                }
            }
        }

        em.persist(titoloViaggio);


        System.out.println((titoloViaggio instanceof Biglietto ? "Biglietto ": "Abbonamento ") + titoloViaggio.getCodiceUnivoco() + " comprato correttamente presso " + titoloViaggio.getPuntoVendita().getIndirizzo());
    }

    public static List<TitoloViaggio> ottieniListaTitoliViaggio() {
        return em.createNamedQuery("getAllTitoliViaggio", TitoloViaggio.class)
                .getResultList();
    }

    public TitoloViaggio findTitoloViaggioById(Long id) {
        TitoloViaggio cercato = em.find(TitoloViaggio.class, id);
        if (cercato == null) throw new NotFoundException("titolo viaggio", "id");
        return cercato;
    }

    public List<Abbonamento> getAllAbbonamentiByTessera(Tessera tessera) {
        return em.createQuery(
                        "SELECT a FROM Abbonamento a WHERE a.tessera = :tessera", Abbonamento.class)
                .setParameter("tessera", tessera)
                .getResultList();
    }

    public List<TitoloViaggio> getAllTitoliViaggioPerPuntoVendita(PuntoVendita puntoVendita) {
        List<TitoloViaggio> result = em.createQuery(
                        "SELECT t FROM TitoloViaggio t WHERE t.puntoVendita = :puntoVendita", TitoloViaggio.class)
                .setParameter("puntoVendita", puntoVendita)
                .getResultList();

        if (result.isEmpty()) throw new EmptyListException();

        return result;
    }

    public List<TitoloViaggio> getAllTitoliViaggioPerPeriodo(int giorni) {
        List<TitoloViaggio> result = em.createQuery(
                        "SELECT t FROM TitoloViaggio t WHERE t.dataAcquisto > :date", TitoloViaggio.class)
                .setParameter("date", LocalDate.now().minusDays(giorni))
                .getResultList();

        if (result.isEmpty()) throw new EmptyListException();

        return result;
    }

    public void obliteraBiglietto(Long bigliettoId , ParcoMezzi parcoMezzi) {
        Biglietto biglietto1 = em.find(Biglietto.class, bigliettoId);
        if (biglietto1 == null) throw new NotFoundException("biglietto", "id");
        if (biglietto1.isConvalidato()) throw new BigliettoGiaConvalidatoException();
        biglietto1.setConvalidato(true);
        biglietto1.setParcoMezzi(parcoMezzi);
        biglietto1.setDataConvalida(LocalDate.now());
        em.persist(biglietto1);
        System.out.println("Biglietto con id " + bigliettoId + " convalidato correttamente.");
    }

    public List<Biglietto> ottieniBigliettiObliteratiPerVeicolo(ParcoMezzi parcoMezzi) {
        List<Biglietto> result = em.createQuery(
                        "SELECT b FROM Biglietto b WHERE b.veicoloPubblico = :veicolo", Biglietto.class)
                .setParameter("veicolo", parcoMezzi)
                .getResultList();

        if (result.isEmpty()) throw new EmptyListException();

        return result;
    }

    public List<Biglietto> ottieniBigliettiObliteratiPerPeriodo(int giorni) {
        List<Biglietto> result = em.createQuery(
                        "SELECT b FROM Biglietto b WHERE b.convalidato AND b.dataConvalidazione > :data", Biglietto.class)
                .setParameter("data", LocalDate.now().minusDays(giorni))
                .getResultList();

        if (result.isEmpty()) throw new EmptyListException();

        return result;
    }
}
