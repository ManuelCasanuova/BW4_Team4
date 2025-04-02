package societa.trasporti;

import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import societa.trasporti.manutenzione.Manutenzione;
import societa.trasporti.manutenzione.ManutenzioneDAO;
import societa.trasporti.manutenzione.TipoManutenzione;
import societa.trasporti.parchiMezzi.ParcoMezzi;
import societa.trasporti.parchiMezzi.ParcoMezziDAO;
import societa.trasporti.parchiMezzi.TipoVeicolo;
import societa.trasporti.servizi.ServizioDAO;
import societa.trasporti.titoloViaggio.abbonamento.Abbonamento;
import societa.trasporti.titoloViaggio.abbonamento.TipoAbbonamento;
import societa.trasporti.titoloViaggio.biglietto.Biglietto;
import societa.trasporti.titoloViaggio.biglietto.TitoloViaggioDAO;
import societa.trasporti.tratta.Tratta;
import societa.trasporti.tratta.TrattaDAO;
import societa.trasporti.utenti.Tessera;
import societa.trasporti.utenti.TesseraDAO;
import societa.trasporti.utenti.Utente;
import societa.trasporti.utenti.UtenteDAO;
import societa.trasporti.vendita.PuntoVendita;
import societa.trasporti.vendita.PuntoVenditaDAO;
import societa.trasporti.vendita.distributori.Distributore;
import societa.trasporti.vendita.rivenditori.RivenditoreAutorizzato;

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
        UtenteDAO utenteDao = new UtenteDAO(em);
        TesseraDAO tesseraDAO = new TesseraDAO(em);
        TitoloViaggioDAO titoloViaggioDAO = new TitoloViaggioDAO(em);
        ParcoMezziDAO parcoMezziDAO = new ParcoMezziDAO(em);
        ManutenzioneDAO manutenzioneDAO = new ManutenzioneDAO(em);
        ServizioDAO servizioDAO = new ServizioDAO(em);


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


        //Costruisce i PUNTI VENDITA casuali in caso in cui non ci siano


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


        List<Tratta> tratteList = trattaDAO.ottieniListaTratte();
        if (tratteList.size() < 5) {
            int tratteMancanti = 5 - tratteList.size();
            for (int i = 0; i < tratteMancanti; i++) {
                Tratta tratta = new Tratta(
                        /*"Linea " + (tratteList.size() + i + 1),
                        faker.address().cityName(),
                        faker.address().cityName(),
                        faker.number().numberBetween(1,30)*/
                );
                trattaDAO.save(tratta);
            }
        }

        //Costruisce utenti randomici con faker

        if (utenteDAO.listaTuttiUtenti().isEmpty()) {
            for (int i = 0; i < 10; i++) {
                Utente utente = new Utente(
                        faker.name().firstName(),  // Nominativo
                        faker.number().numberBetween(1938, 2007) // Data di nascita
                );
                utenteDAO.save(utente);
            }
        }

        if (tesseraDAO.ottieniListaTessere().isEmpty()) {
            List<Utente> listaUtenti = utenteDAO.listaTuttiUtenti();
            for (Utente utente : listaUtenti) {
                Tessera tessera = new Tessera(utente, LocalDate.now());
                tesseraDAO.salvaTessera(tessera);
            }
        }

        if (TitoloViaggioDAO.ottieniListaTitoliViaggio().isEmpty()) {
            List<PuntoVendita> listaPuntiVendita = puntoVenditaDAO.ottieniListaPuntiVendita();
            List<Tessera> listaTessere = tesseraDAO.ottieniListaTessere();
            List<TipoAbbonamento> tipoAbbonamentoList = Arrays.asList(TipoAbbonamento.values());
            for (int i = 0; i < 20; i++) {
                boolean random = faker.random().nextBoolean();
                PuntoVendita puntoRandom;
                while (true) {
                    puntoRandom = listaPuntiVendita.get(faker.random().nextInt(0, listaPuntiVendita.size() - 1));
                    if (puntoRandom instanceof RivenditoreAutorizzato) break;
                    else if (((Distributore) puntoRandom).isAttivo()) break;

                    if (random) {
                        Biglietto biglietto = new Biglietto(null, Double.parseDouble(faker.commerce().price()), LocalDate.of(faker.random().nextInt(2022, 2025), faker.random().nextInt(1, 12), faker.random().nextInt(1, 30)), puntoRandom);
                        titoloViaggioDAO.salvaTitoloViaggio(biglietto);
                    } else {
                        Abbonamento abbonamento = new Abbonamento(null, Double.parseDouble(faker.commerce().price()), LocalDate.of(faker.random().nextInt(2022, 2025), faker.random().nextInt(1, 12), faker.random().nextInt(1, 30)), puntoRandom, tipoAbbonamentoList.get(faker.random().nextInt(0, tipoAbbonamentoList.size() - 1)), listaTessere.get(faker.random().nextInt(0, listaTessere.size() - 1)), LocalDate.now());
                        titoloViaggioDAO.salvaTitoloViaggio(abbonamento);
                    }

                }
            }
        }

        if(parcoMezziDAO.findAllParcoMezzi().isEmpty()) {
            for(int i = 0; i<10; i++){
                ParcoMezzi parcoMezzi = new ParcoMezzi( null, TipoVeicolo.TRAM);
                parcoMezziDAO.salvaMezzo(parcoMezzi);
            }
            for(int i = 0; i<10; i++){
                ParcoMezzi parcoMezzi = new ParcoMezzi(null, TipoVeicolo.AUTOBUS);
                parcoMezziDAO.salvaMezzo(parcoMezzi);
            }
        }

        if(servizioDAO.listaControlloServiziAttivi().isEmpty()){
            List<ParcoMezzi> mezzi = parcoMezziDAO.findAllParcoMezzi();
            List<Tratta> tratte = trattaDAO.ottieniListaTratte();
            for(int i = 0; i<10; i++){
                servizioDAO.entrataInServizio(mezzi.get(i), tratte.get(i));
            }

        }

       if (!bigliettiObliteratiInizializzati){
           List<ParcoMezzi> mezzi = parcoMezziDAO.findAllParcoMezzi();
           if(!mezzi.isEmpty()) {
               List<PuntoVendita> puntiVendita = puntoVenditaDAO.ottieniListaPuntiVendita();
               for (int i = 0; i < 10; i++) {
                  PuntoVendita  puntoVendita = puntiVendita.get(faker.random().nextInt(0, puntiVendita.size() - 1));
                  ParcoMezzi mezzo = mezzi.get(faker.random().nextInt(0, mezzi.size() - 1));
                  Biglietto  biglietto = new Biglietto(null, Double.parseDouble(faker.commerce().price()),LocalDate.now().minusDays(faker.random().nextInt(1, 150)),puntoVendita);
                  titoloViaggioDAO.salvaTitoloViaggio(biglietto);
                  titoloViaggioDAO.obliteraBiglietto(biglietto.getCodiceUnivoco(),mezzo);
               }
           }
           bigliettiObliteratiInizializzati = true;
       }
    }
    public static void storicoMezzi (ParcoMezziDAO parcoMezziDAO, ServizioDAO servizioDAO, ManutenzioneDAO  manutenzioneDAO,TrattaDAO trattaDAO, PuntoVenditaDAO  puntoVenditaDAO){

        List<ParcoMezzi> mezzi = parcoMezziDAO.findAllParcoMezzi();
        List<Tratta> tratte = trattaDAO.ottieniListaTratte();
        if(tratte.isEmpty()){
            System.out.println("Nessuna tratta disponibile, verificare. Aggiungi una tratta nel DB");
            return;
        }
        int[] numeroManutenzioni = {10, 5, 10, 7, 2, 8, 3, 6, 4, 9};
        int[] numeroServizi = {10, 5, 10, 7, 2, 8, 3, 6, 4, 9};
        for(ParcoMezzi mezzo : mezzi){

            Long manutenzioniPresenti = ManutenzioneDAO.contaManutenzioniPerMezzo(mezzo); // verificare che non si rompa!!
            Long serviziPresenti = servizioDAO.numeroServiziPerMezzo(mezzo);

            boolean manutenzioniAggiornate = false;
            boolean serviziAggiornati = false;

            if(manutenzioniPresenti < numeroManutenzioni[mezzi.indexOf(mezzo)]){
                // calcolo della differenza tra le manutenzioni presenti e quelle del database
                int differenza = numeroManutenzioni[mezzi.indexOf(mezzo)] - manutenzioniPresenti.intValue();
                for(int i = 0; i < differenza; i++){
                    Manutenzione manutenzione = new Manutenzione (LocalDate.now().minusMonths(faker.random().nextInt(1,12)),LocalDate.now().minusMonths(faker.random().nextInt(1,6)), TipoManutenzione.values()[faker.random().nextInt(TipoManutenzione.values().length)], mezzo);
                    manutenzioneDAO.salvaManutenzione(manutenzione);
                }
                manutenzioniAggiornate = true;
            }
        }

    }


}
