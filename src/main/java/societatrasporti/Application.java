package societatrasporti;

import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import societatrasporti.exception.DistributoreFuoriServizioException;
import societatrasporti.classi.manutenzione.Manutenzione;
import societatrasporti.DAO.ManutenzioneDAO;
import societatrasporti.classi.manutenzione.TipoManutenzione;
import societatrasporti.classi.parchiMezzi.ParcoMezzi;
import societatrasporti.DAO.ParcoMezziDAO;
import societatrasporti.classi.parchiMezzi.TipoVeicolo;
import societatrasporti.classi.servizi.Servizio;
import societatrasporti.DAO.ServizioDAO;
import societatrasporti.classi.titoloViaggio.abbonamento.Abbonamento;
import societatrasporti.classi.titoloViaggio.abbonamento.TipoAbbonamento;
import societatrasporti.classi.titoloViaggio.biglietto.Biglietto;
import societatrasporti.DAO.TitoloViaggioDAO;
import societatrasporti.classi.tratta.Tratta;
import societatrasporti.DAO.TrattaDAO;
import societatrasporti.classi.utenti.Tessera;
import societatrasporti.DAO.TesseraDAO;
import societatrasporti.classi.utenti.Utente;
import societatrasporti.DAO.UtenteDAO;
import societatrasporti.classi.vendita.PuntoVendita;
import societatrasporti.DAO.PuntoVenditaDAO;
import societatrasporti.classi.vendita.distributori.Distributore;
import societatrasporti.classi.vendita.rivenditori.RivenditoreAutorizzato;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;



public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("epicode");
    private static final Faker faker = new Faker(Locale.ITALY);
    private static boolean bigliettiObliteratiInizializzati = false;

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        PuntoVenditaDAO puntoVenditaDAO = new PuntoVenditaDAO(em);
        TrattaDAO trattaDAO = new TrattaDAO(em);
        UtenteDAO utenteDAO = new UtenteDAO(em);
        TesseraDAO tesseraDAO = new TesseraDAO(em);
        TitoloViaggioDAO titoloViaggioDAO = new TitoloViaggioDAO(em);
        ParcoMezziDAO parcoMezziDAO = new ParcoMezziDAO(em);
        ManutenzioneDAO manutenzioneDAO = new ManutenzioneDAO(em);
        ServizioDAO servizioDAO = new ServizioDAO(em);
        MenuInterattivo menuInterattivo = new MenuInterattivo(em);
        inizializzaDB(puntoVenditaDAO, trattaDAO, utenteDAO, tesseraDAO, titoloViaggioDAO, parcoMezziDAO, manutenzioneDAO, servizioDAO, em);
        storicoMezzi(parcoMezziDAO, servizioDAO, manutenzioneDAO, trattaDAO);
        if(!puntoVenditaDAO.ottieniListaPuntiVendita().isEmpty()) menuInterattivo.avviaMenu();


        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    public static void inizializzaDB(
            PuntoVenditaDAO puntoVenditaDAO,
            TrattaDAO trattaDAO,
            UtenteDAO utenteDAO,
            TesseraDAO tesseraDAO,
            TitoloViaggioDAO titoloViaggioDAO,
            ParcoMezziDAO parcoMezziDAO,
            ManutenzioneDAO manutenzioneDAO,
            ServizioDAO servizioDAO,
            EntityManager em) {


        if (puntoVenditaDAO.ottieniListaPuntiVendita().isEmpty()) {
            for (int i = 0; i < 10; i++) {
                boolean random = faker.random().nextBoolean();
                if (random) {
                    RivenditoreAutorizzato rivenditoreAutorizzato = new RivenditoreAutorizzato(
                            faker.address().streetAddress(), faker.company().name());
                    puntoVenditaDAO.SalvaPuntiVendita(rivenditoreAutorizzato);
                } else {
                    Distributore distributore = new Distributore(
                            faker.address().streetAddress(), faker.random().nextBoolean());
                    puntoVenditaDAO.SalvaPuntiVendita(distributore);
                }
            }
        }


        List<Tratta> tratteEsistenti = trattaDAO.ottieniListaTratte();
        if (tratteEsistenti.size() < 6) {
            int tratteMancanti = 6 - tratteEsistenti.size();
            for (int i = 0; i < tratteMancanti; i++) {
                Tratta tratta = new Tratta(
                        "Linea " + (tratteEsistenti.size() + i + 1),
                        faker.address().cityName(),
                        faker.address().cityName(),
                        faker.number().numberBetween(20,60)
                );
                trattaDAO.saveTratta(tratta);
            }
        }


        if (utenteDAO.listaTuttiUtenti().isEmpty()) {
            for (int i = 0; i < 5; i++) {
                Utente utente = new Utente(
                        faker.name().firstName(),
                        faker.number().numberBetween(1938, 2007)
                );
                utenteDAO.save(utente);
            }
        }

        if (tesseraDAO.ottieniListaTessere().isEmpty()) {
            List<Utente> utentiList = utenteDAO.listaTuttiUtenti();
            for (Utente utente : utentiList) {
                Tessera tessera = new Tessera(utente, LocalDate.now());
                tesseraDAO.salvaTessera(tessera);
            }
        }

        if (titoloViaggioDAO.ottieniListaTitoliViaggio().isEmpty()) {
            List<PuntoVendita> puntiVenditaList = puntoVenditaDAO.ottieniListaPuntiVendita();
            List<Tessera> tessereList = tesseraDAO.ottieniListaTessere();
            List<TipoAbbonamento> tipoAbbonamentoList = Arrays.asList(TipoAbbonamento.values());

            for (int i = 0; i < 30; i++) {
                boolean random = faker.random().nextBoolean();
                PuntoVendita puntoRandom;
                while (true) {
                    puntoRandom = puntiVenditaList.get(faker.random().nextInt(0, puntiVenditaList.size() - 1));
                    if (puntoRandom instanceof RivenditoreAutorizzato) break;
                    else if (((Distributore) puntoRandom).isAttivo()) break;
                }
                    if (random) {
                        Biglietto biglietto = new Biglietto(null,
                                Double.parseDouble(faker.commerce().price().replace(",", ".")),
                                LocalDate.of(faker.random().nextInt(2022, 2025),
                                        faker.random().nextInt(1, 12),
                                        faker.random().nextInt(1, 27)),
                                puntoRandom
                        );
                        titoloViaggioDAO.salvaTitoloViaggio(biglietto);
                    } else {
                        Abbonamento abbonamento = new Abbonamento(null, Double.parseDouble(faker.commerce().price().replace(",", ".")),
                                LocalDate.of(faker.random().nextInt(2022, 2025),
                                        faker.random().nextInt(1, 12),
                                        faker.random().nextInt(1, 27)),
                                        puntoRandom,
                                        tipoAbbonamentoList.get(faker.random().nextInt(0, tipoAbbonamentoList.size() - 1)),
                                        tessereList.get(faker.random().nextInt(0, tessereList.size() - 1)),
                                        LocalDate.now()
                        );
                        try {
                            titoloViaggioDAO.salvaTitoloViaggio(abbonamento);
                        } catch (Exception e) {
                            System.out.println("Errore nell'acquisto dell'abbonamento: " + e.getMessage());
                        }
                    }
                }
            }


        if(parcoMezziDAO.findAllParcoMezzi().isEmpty()) {
            for(int i = 0; i<6; i++){
                ParcoMezzi parcoMezzi = new ParcoMezzi(
                        null,
                        TipoVeicolo.TRAM
                );
                parcoMezziDAO.salvaMezzo(parcoMezzi);
            }
            for(int i = 0; i<6; i++){
                ParcoMezzi parcoMezzi = new ParcoMezzi(
                        null,
                        TipoVeicolo.AUTOBUS
                );
                parcoMezziDAO.salvaMezzo(parcoMezzi);
            }
        }

        if(servizioDAO.controlloServiziAttivi().isEmpty()){
            List<ParcoMezzi> mezzi = parcoMezziDAO.findAllParcoMezzi();
            List<Tratta> tratte = trattaDAO.ottieniListaTratte();
            for(int i = 0; i<6; i++){
                servizioDAO.entrataInServizio(mezzi.get(i), tratte.get(i));
            }

        }

       if (!bigliettiObliteratiInizializzati){
           List<ParcoMezzi> mezzi = parcoMezziDAO.findAllParcoMezzi();
           if(!mezzi.isEmpty()) {
               List<PuntoVendita> puntiVendita = puntoVenditaDAO.ottieniListaPuntiVendita();
               for (int j = 0; j < 10; j++) {
                  PuntoVendita  puntoVendita = puntiVendita.get(faker.random().nextInt(0, puntiVendita.size() - 1));
                  ParcoMezzi mezzo2 = mezzi.get(faker.random().nextInt(0, mezzi.size() - 1));

                  Biglietto  biglietto = new Biglietto(
                          null,
                          Double.parseDouble(faker.commerce().price().replace(",",".")),
                          LocalDate.now().minusDays(faker.random().nextInt(1, 150)),
                          puntoVendita
                  );

                   try {
                       titoloViaggioDAO.salvaTitoloViaggio(biglietto);
                   } catch (DistributoreFuoriServizioException e) {
                       System.out.println(e.getMessage());
                   }

                   try {
                       titoloViaggioDAO.obliteraBiglietto(biglietto.getCodiceUnivoco(), mezzo2);
                   } catch (Exception e) {
                       System.out.println("Errore durante l'obliterazione del biglietto: " + e.getMessage());
                   }
               }
           }
           bigliettiObliteratiInizializzati = true;
       }
    }
    public static void storicoMezzi (ParcoMezziDAO parcoMezziDAO,
                                     ServizioDAO servizioDAO,
                                     ManutenzioneDAO  manutenzioneDAO,
                                     TrattaDAO trattaDAO){

        List<ParcoMezzi> mezzi = parcoMezziDAO.findAllParcoMezzi();
        List<Tratta> tratte = trattaDAO.ottieniListaTratte();

        if(tratte.isEmpty()){
            System.out.println("Nessuna tratta disponibile, verificare. Aggiungi una tratta nel DB");
            return;
        }


        int[] numeroManutenzioni = {4, 6, 9, 3, 9, 4, 2, 6, 7, 3, 3, 7};
        int[] numeroServizi = {5, 8, 5, 4, 7, 6, 5, 7, 8, 6, 3, 3};


        int indice= 0;
        for(ParcoMezzi mezzo : mezzi){

            long manutenzioniPresenti = manutenzioneDAO.contaManutenzioniPerMezzo(mezzo);
            long serviziPresenti = servizioDAO.numeroServiziPerMezzo(mezzo);

            boolean manutenzioniAggiornate = false;
            boolean serviziAggiornati = false;

            if(manutenzioniPresenti < numeroManutenzioni[indice]){

                int manutenzioniDaCreare = numeroManutenzioni[indice] - (int) manutenzioniPresenti;
                for(int i = 0; i < manutenzioniDaCreare; i++){
                    Manutenzione manutenzione = new Manutenzione (
                            LocalDate.now().minusMonths(faker.random().nextInt(1,12)),
                            LocalDate.now().minusMonths(faker.random().nextInt(1,6)),
                            TipoManutenzione.values()[faker.random().nextInt(TipoManutenzione.values().length)],
                            mezzo);

                    manutenzioneDAO.salvaManutenzione(manutenzione);
                }
                manutenzioniAggiornate = true;
            }

            if (serviziPresenti < numeroServizi[indice]) {
                int serviziDaCreare = numeroServizi[indice] - (int) serviziPresenti;
                Tratta trattaAssociata = tratte.get(indice % tratte.size());
                for (int j = 0; j < serviziDaCreare; j++) {
                    Servizio servizio = new Servizio(
                            LocalDate.now().minusMonths(faker.random().nextInt(1, 12)),
                            LocalDate.now().minusMonths(faker.random().nextInt(1, 6)),
                            mezzo,
                            trattaAssociata
                    );
                    servizioDAO.saveServizio(servizio);
                }
                serviziAggiornati = true;
            }
            indice ++;
        }
    }

 public static boolean DatabasePopolato (TitoloViaggioDAO  titoloViaggioDAO, ParcoMezziDAO parcoMezziDAO){
        return !titoloViaggioDAO.ottieniListaTitoliViaggio().isEmpty() && !parcoMezziDAO.findAllParcoMezzi().isEmpty();
 }

}
