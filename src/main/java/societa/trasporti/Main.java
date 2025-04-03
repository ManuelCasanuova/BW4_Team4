package societa.trasporti;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import societa.trasporti.parchiMezzi.ParcoMezzi;
import societa.trasporti.servizi.Servizio;
import societa.trasporti.servizi.ServizioDAO;
import societa.trasporti.titoloViaggio.abbonamento.Abbonamento;
import societa.trasporti.titoloViaggio.abbonamento.TipoAbbonamento;
import societa.trasporti.titoloViaggio.biglietto.Biglietto;
import societa.trasporti.titoloViaggio.biglietto.TitoloViaggioDAO;
import societa.trasporti.tratta.Tratta;
import societa.trasporti.utenti.Tessera;
import societa.trasporti.utenti.UtenteDAO;
import societa.trasporti.parchiMezzi.ParcoMezziDAO;
import societa.trasporti.tratta.TrattaDAO;
import societa.trasporti.vendita.PuntoVendita;
import societa.trasporti.vendita.PuntoVenditaDAO;
import societa.trasporti.utenti.TesseraDAO;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("epicode");
    private static final EntityManager em = emf.createEntityManager();

    // Creazione DAO per l’interazione diretta con il database
    private static final TitoloViaggioDAO titoloViaggioDAO = new TitoloViaggioDAO(em);
    private static final UtenteDAO utenteDAO = new UtenteDAO(em);
    private static final ParcoMezziDAO parcoMezziDAO = new ParcoMezziDAO(em);
    private static final TrattaDAO trattaDAO = new TrattaDAO(em);
    private static final TesseraDAO tesseraDAO = new TesseraDAO(em);
    private static final PuntoVenditaDAO puntoVenditaDAO = new PuntoVenditaDAO(em);

    public static void main(String[] args) {
        System.out.println("🚍 Benvenuto nel sistema di trasporto pubblico!");
        avviaInterazione();
        em.close();
        emf.close();
    }

    public static void avviaInterazione() {
        while (true) {
            System.out.println("\n⚙️ Seleziona il tuo ruolo:");
            System.out.println("1️⃣ Utente");
            System.out.println("2️⃣ Amministratore");
            System.out.println("0️⃣ Esci");
            System.out.print("👉 Inserisci la tua scelta: ");

            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1:
                    interazioneUtente();
                    break;
                case 2:
                    interazioneAmministratore();
                    break;
                case 0:
                    System.out.println("👋 Uscita dal sistema. Arrivederci!");
                    return;
                default:
                    System.out.println("❌ Scelta non valida. Riprova.");
            }
        }
    }

    public static void interazioneUtente() {
        System.out.println("\n👤 Modalità Utente");
        System.out.println("📌 Opzioni disponibili:");
        System.out.println("1️⃣ Acquista biglietto");
        System.out.println("2️⃣ Acquista abbonamento");
        System.out.println("3️⃣ Verifica tessera");
        System.out.println("4️⃣ Consulta tratte disponibili");
        System.out.println("5️⃣ Visualizza storico viaggi");
        System.out.println("0️⃣ Torna al menu principale");
        System.out.print("👉 Inserisci la tua scelta: ");

        int scelta = scanner.nextInt();
        scanner.nextLine();

        switch (scelta) {
            case 1:
                acquistaBiglietto();
                break;
            case 2:
                acquistaAbbonamento();
                break;
            case 3:
                verificaTessera();
                break;
            case 4:
                consultaTratte();
                break;
            case 5:
                visualizzaStoricoViaggi();
                break;
            case 0:
                return;
            default:
                System.out.println("❌ Scelta non valida. Riprova.");
        }
        interazioneUtente();
    }

    public static void interazioneAmministratore() {
        System.out.println("\n🛠 Modalità Amministratore");
        System.out.println("📌 Opzioni disponibili:");
        System.out.println("1️⃣ Gestisci tratte");
        System.out.println("2️⃣ Monitoraggio mezzi");
        System.out.println("3️⃣ Gestione punti vendita");
        System.out.println("4️⃣ Report servizi");
        System.out.println("5️⃣ Analisi statistiche");
        System.out.println("0️⃣ Torna al menu principale");
        System.out.print("👉 Inserisci la tua scelta: ");

        int scelta = scanner.nextInt();
        scanner.nextLine();

        switch (scelta) {
            case 1:
                gestisciTratte();
                break;
            case 2:
                monitoraggioMezzi();
                break;
            case 3:
                gestisciPuntiVendita();
                break;
            case 4:
                reportServizi();
                break;
            case 5:
                analisiStatistiche();
                break;
            case 0:
                return;
            default:
                System.out.println("❌ Scelta non valida. Riprova.");
        }
        interazioneAmministratore();
    }

    // ✅ Funzioni utente
    private static void acquistaBiglietto() {
        System.out.println("🛒 Acquisto biglietto...");
        List<PuntoVendita> puntiVendita = puntoVenditaDAO.ottieniListaPuntiVendita();
        if (puntiVendita.isEmpty()) {
            System.out.println("⚠️ Nessun punto vendita disponibile.");
            return;
        }
        PuntoVendita puntoVendita = puntiVendita.get(0);  // Seleziona il primo punto vendita disponibile.

        Biglietto biglietto = new Biglietto(null, 2.0, LocalDate.now(), puntoVendita);
        titoloViaggioDAO.salvaTitoloViaggio(biglietto);
        System.out.println("✅ Biglietto acquistato con successo! Codice: " + biglietto.getCodiceUnivoco());
    }

    private static void acquistaAbbonamento() {
        System.out.println("🛒 Acquisto abbonamento...");
        List<Tessera> tessere = tesseraDAO.ottieniListaTessere();
        List<PuntoVendita> puntiVendita = puntoVenditaDAO.ottieniListaPuntiVendita();
        List<TipoAbbonamento> tipiAbbonamento = Arrays.asList(TipoAbbonamento.values());

        if (tessere.isEmpty() || puntiVendita.isEmpty()) {
            System.out.println("⚠️ Nessuna tessera o punto vendita disponibile.");
            return;
        }

        Tessera tessera = tessere.get(0);  // Seleziona la prima tessera disponibile
        PuntoVendita puntoVendita = puntiVendita.get(0);  // Seleziona il primo punto vendita
        TipoAbbonamento tipoAbbonamento = tipiAbbonamento.get(new Random().nextInt(tipiAbbonamento.size())); // Seleziona un tipo casuale

        Abbonamento abbonamento = new Abbonamento(
                null,
                50.0,
                LocalDate.now(),
                puntoVendita,
                tipoAbbonamento, // Tipo di abbonamento casuale
                tessera,
                LocalDate.now() // Data di inizio
        );

        titoloViaggioDAO.salvaTitoloViaggio(abbonamento);
        System.out.println("✅ Abbonamento " + tipoAbbonamento + " acquistato per la tessera ID " + tessera.getId());
    }

    private static void verificaTessera() {
        System.out.println("🔎 Verifica tessera...");
        List<Tessera> tessere = tesseraDAO.ottieniListaTessere();
        if (tessere.isEmpty()) {
            System.out.println("⚠️ Nessuna tessera disponibile.");
            return;
        }
        Tessera tessera = tessere.get(0);
        boolean valida = tesseraDAO.verificaValiditaAbbonamento(tessera);
        System.out.println(valida ? "✅ Tessera valida!" : "❌ Tessera scaduta.");
    }

    private static void consultaTratte() {
        System.out.println("🗺 Consulta tratte disponibili...");
        List<Tratta> tratte = trattaDAO.ottieniListaTratte();
        if (tratte.isEmpty()) {
            System.out.println("⚠️ Nessuna tratta disponibile.");
            return;
        }
        tratte.forEach(tratta -> System.out.println("🛤 " + tratta.getNomeTratta() + " → " + tratta.getZonaArrivo()));
    }

    private static void visualizzaStoricoViaggi() {
        System.out.println("📜 Storico viaggi...");
        List<Biglietto> biglietti = titoloViaggioDAO.ottieniBigliettiObliteratiPerPeriodo(30);
        if (biglietti.isEmpty()) {
            System.out.println("⚠️ Nessun biglietto obliterato negli ultimi 30 giorni.");
            return;
        }
        biglietti.forEach(biglietto -> System.out.println("🎫 Biglietto convalidato: " + biglietto.getCodiceUnivoco() + " il " + biglietto.getDataConvalida()));
    }

    // ✅ Funzioni amministratore
    private static void gestisciTratte() {
        System.out.println("🚏 Gestione tratte...");
        List<Tratta> tratte = trattaDAO.ottieniListaTratte();
        System.out.println("🔹 Numero di tratte gestite: " + tratte.size());

        // Aggiunta di una nuova tratta
        System.out.print("Vuoi aggiungere una nuova tratta? (sì/no): ");
        String scelta = scanner.nextLine();
        if (scelta.equalsIgnoreCase("sì")) {
            System.out.print("Nome tratta: ");
            String nomeTratta = scanner.nextLine();
            Tratta nuovaTratta = new Tratta(null, nomeTratta, "Partenza", "Arrivo", 20, null);
            trattaDAO.save(nuovaTratta);
            System.out.println("✅ Nuova tratta aggiunta con successo!");
        }
    }
    private static void monitoraggioMezzi() {
        System.out.println("🚌 Monitoraggio mezzi...");
        List<ParcoMezzi> mezzi = parcoMezziDAO.findAllParcoMezzi();
        if (mezzi.isEmpty()) {
            System.out.println("⚠️ Nessun mezzo registrato.");
            return;
        }
        mezzi.forEach(mezzo -> System.out.println("🚍 Mezzo " + mezzo.getMatricola() + " → Stato: " + (mezzo.isInManutenzione() ? "🔧 In manutenzione" : "✅ Operativo")));
    }
    private static void gestisciPuntiVendita() {
        System.out.println("🏪 Gestione punti vendita...");
        List<PuntoVendita> puntiVendita = puntoVenditaDAO.ottieniListaPuntiVendita();
        System.out.println("🔹 Numero di punti vendita attivi: " + puntiVendita.size());
    }
    private static void reportServizi() {
        System.out.println("📊 Generazione report servizi...");
        List<Servizio> serviziAttivi = ServizioDAO.listaControlloServiziAttivi(em);
        System.out.println("🔹 Numero di servizi attivi: " + serviziAttivi.size());
    }
    private static void analisiStatistiche() {
        System.out.println("📈 Analisi statistiche...");
        long bigliettiVenduti = titoloViaggioDAO.ottieniListaTitoliViaggio(em).size();
        System.out.println("🎟 Biglietti venduti: " + bigliettiVenduti);
    }}