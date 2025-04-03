package societa.trasporti;

import java.util.Scanner;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import societa.trasporti.titoloViaggio.biglietto.TitoloViaggioDAO;
import societa.trasporti.utenti.UtenteDAO;
import societa.trasporti.parchiMezzi.ParcoMezziDAO;
import societa.trasporti.tratta.TrattaDAO;
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
        // Logica di acquisto
    }

    private static void acquistaAbbonamento() {
        System.out.println("🛒 Acquisto abbonamento...");
        // Logica di acquisto
    }

    private static void verificaTessera() {
        System.out.println("🔎 Verifica tessera...");
        // Logica di verifica tessera
    }

    private static void consultaTratte() {
        System.out.println("🗺 Consulta tratte disponibili...");
        // Logica di consultazione tratte
    }

    private static void visualizzaStoricoViaggi() {
        System.out.println("📜 Visualizzazione dello storico viaggi...");
        // Logica di visualizzazione titoli di viaggio e tratte percorse
    }

    // ✅ Funzioni amministratore
    private static void gestisciTratte() {
        System.out.println("🚏 Gestione tratte...");
    }

    private static void monitoraggioMezzi() {
        System.out.println("🚌 Monitoraggio mezzi...");
    }

    private static void gestisciPuntiVendita() {
        System.out.println("🏪 Gestione punti vendita...");
    }

    private static void reportServizi() {
        System.out.println("📊 Generazione report servizi...");
    }

    private static void analisiStatistiche() {
        System.out.println("📈 Analisi statistiche...");
    }
}