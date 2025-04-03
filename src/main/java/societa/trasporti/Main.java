package societa.trasporti;

import com.github.javafaker.Faker;
import jakarta.persistence.*;
import societa.trasporti.manutenzione.ManutenzioneDAO;
import societa.trasporti.parchiMezzi.ParcoMezziDAO;
import societa.trasporti.servizi.ServizioDAO;
import societa.trasporti.titoloViaggio.TitoloViaggio;
import societa.trasporti.titoloViaggio.abbonamento.Abbonamento;
import societa.trasporti.titoloViaggio.abbonamento.TipoAbbonamento;
import societa.trasporti.titoloViaggio.biglietto.TitoloViaggioDAO;
import societa.trasporti.tratta.TrattaDAO;
import societa.trasporti.exception.TrattaPercorsaException;
import societa.trasporti.tratta.Tratta;
import societa.trasporti.utenti.TesseraDAO;
import societa.trasporti.utenti.UtenteDAO;
import societa.trasporti.vendita.PuntoVenditaDAO;
import java.util.*;

public class Main {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("epicode");
    private static final Faker faker = new Faker(Locale.ITALY);

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();

        PuntoVenditaDAO puntoVenditaDAO = new PuntoVenditaDAO(em);
        TrattaDAO trattaDAO = new TrattaDAO(em);
        UtenteDAO utenteDao = new UtenteDAO(em);
        TesseraDAO tesseraDAO = new TesseraDAO(em);
        TitoloViaggioDAO titoloViaggioDAO = new TitoloViaggioDAO(em);
        ParcoMezziDAO parcoMezziDAO = new ParcoMezziDAO(em);
        ManutenzioneDAO manutenzioneDAO = new ManutenzioneDAO(em);
        ServizioDAO servizioDAO = new ServizioDAO(em);

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("=== SISTEMA DI TRASPORTI ===");
            System.out.println("Sei un: \\n1. Utente \\n2. Amministratore");
            System.out.print("Scelta: ");
            int ruolo = scanner.nextInt();
            scanner.nextLine();

            switch (ruolo) {
                case 1 -> menuUtente(scanner, trattaDAO, titoloViaggioDAO);
                case 2 -> menuAmministratore(scanner, trattaDAO);
                default -> System.out.println("⚠️ Scelta non valida!");
            }

        } finally {
            em.close();
            emf.close();
            scanner.close();
        }
    }

    // menu utente
    private static void menuUtente(Scanner scanner, TrattaDAO trattaDAO, TitoloViaggioDAO titoloViaggioDAO) {
        while (true) {
            System.out.println("\\n=== MENU UTENTE ===");
            System.out.println("1. Compra biglietto");
            System.out.println("2. Compra abbonamento");
            System.out.println("3. Cerca tratte per zona di partenza");
            System.out.println("4. Cerca tratte per zona di arrivo");
            System.out.println("5. Visualizza tutte le tratte");
            System.out.println("0. Esci");
            System.out.print("Scelta: ");
            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1 -> {
                    System.out.print("Inserisci ID tratta: ");
                    Long idTratta = scanner.nextLong();
                    System.out.print("Numero di biglietti: ");
                    int numBiglietti = scanner.nextInt();
                    titoloViaggioDAO.salvaTitoloViaggio(new TitoloViaggio(null, idTratta, numBiglietti));
                    System.out.println("✅ Biglietti acquistati con successo!");
                }
                case 2 -> {
                    System.out.print("Tipo di abbonamento (1=Mensile, 2=Annuale): ");
                    int tipoAbbonamento = scanner.nextInt();
                    Abbonamento abbonamento = new Abbonamento(null, TipoAbbonamento.values()[tipoAbbonamento - 1]);
                    System.out.println("✅ Abbonamento attivato!");
                }
                case 3 -> {
                    System.out.print("Zona di partenza: ");
                    String partenza = scanner.nextLine();
                    stampaTratte(trattaDAO.findByZonaPartenza(partenza));
                }
                case 4 -> {
                    System.out.print("Zona di arrivo: ");
                    String arrivo = scanner.nextLine();
                    stampaTratte(trattaDAO.findByZonaArrivo(arrivo));
                }
                case 5 -> stampaTratte(trattaDAO.ottieniListaTratte());
                case 0 -> {
                    System.out.println("👋 Arrivederci!");
                    return;
                }
                default -> System.out.println("⚠️ Scelta non valida!");
            }
        }
    }


    // menu amministratore
    private static void menuAmministratore(Scanner scanner, TrattaDAO trattaDAO) {
        while (true) {
            System.out.println("\n=== MENU AMMINISTRATORE ===");
            System.out.println("1. Aggiungi nuova tratta");
            System.out.println("2. Elimina tratta");
            System.out.println("3. Visualizza tutte le tratte");
            System.out.println("0. Esci");
            System.out.print("Scelta: ");
            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1 -> {
                    System.out.print("Nome tratta: ");
                    String nome = scanner.nextLine();
                    System.out.print("Zona di partenza: ");
                    String partenza = scanner.nextLine();
                    System.out.print("Zona di arrivo: ");
                    String arrivo = scanner.nextLine();
                    System.out.print("Tempo medio (minuti): ");
                    int tempo = scanner.nextInt();
                    trattaDAO.save(new Tratta(null, nome, partenza, arrivo, tempo, null));
                    System.out.println("✅ Tratta aggiunta!");
                }
                case 2 -> {
                    System.out.print("ID tratta da eliminare: ");
                    Long id = scanner.nextLong();
                    try {
                        trattaDAO.delete(id);
                        System.out.println("✅ Tratta eliminata!");
                    } catch (TrattaPercorsaException e) {
                        System.out.println("⚠️ Errore: " + e.getMessage());
                    }
                }
                case 3 -> stampaTratte(trattaDAO.ottieniListaTratte());
                case 0 -> {
                    System.out.println("👋 Uscita dal menu amministratore!");
                    return;
                }
                default -> System.out.println("⚠️ Scelta non valida!");
            }
        }
    }

    private static void stampaTratte(List<Tratta> tratte) {
        if (tratte.isEmpty()) System.out.println("⚠️ Nessuna tratta trovata!");
        else tratte.forEach(System.out::println);
    }
}