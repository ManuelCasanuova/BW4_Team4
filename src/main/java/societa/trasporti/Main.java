package societa.trasporti;

import societa.trasporti.tratta.TrattaDAO;
import societa.trasporti.exception.TrattaPercorsaException;
import societa.trasporti.parchiMezzi.ParcoMezzi;
import societa.trasporti.parchiMezzi.TipoVeicolo;
import societa.trasporti.tratta.Tratta;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TrattaDAO trattaDAO = new TrattaDAO();

        try {
            System.out.println("Benvenuto nel Sistema di Trasporti");
            System.out.println("Sei un:\n1. Utente \n2. Amministratore");
            System.out.print("Scelta: ");
            int ruolo = scanner.nextInt();
            scanner.nextLine();

            switch (ruolo) {
                case 1 -> menuUtente(scanner, trattaDAO);
                case 2 -> menuAmministratore(scanner, trattaDAO);
                default -> System.out.println("Scelta non valida!");
            }

        } finally {
            trattaDAO.close();
            scanner.close();
        }
    }

    // menu utente
    private static void menuUtente(Scanner scanner, TrattaDAO trattaDAO) {
        while (true) {
            System.out.println("\n MENU UTENTE");
            System.out.println("1. Compra bigletto o abbonamento");
            System.out.println("2. Cerca tratte per zona di partenza");
            System.out.println("3. Cerca tratte per zona di arrivo");
            System.out.println("4. Visualizza tutte le tratte");
            System.out.println("0. Esci");
            System.out.print("Scelta: ");
            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch () {
                case 1 -> {
                    System.out.println("Seleziona la tratta per cui vuoi comprare il biglietto:");
                    List<Tratta> tratte = trattaDAO.ottieniListaTratte();
                    stampaTratte(tratte);
                    System.out.print("Inserisci l'ID della tratta: ");
                    Long idTratta = scanner.nextLong();
                    scanner.nextLine();
                    System.out.print("Inserisci il numero di biglietti da comprare: ");
                    int numBiglietti = scanner.nextInt();
                    scanner.nextLine();
                    try {
                        trattaDAO.compraBiglietto(idTratta, numBiglietti);
                        System.out.println("Biglietto/i acquistato/i con successo!");
                    } catch (TrattaException e) {
                        System.out.println("Errore: " + e.getMessage());
                    }
                }
                case 2 -> {
                    System.out.println("Seleziona il tipo di abbonamento:");
                    System.out.println("1. Abbonamento mensile");
                    System.out.println("2. Abbonamento annuale");
                    System.out.print("Scelta: ");
                    int sceltaAbbonamento = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Inserisci il numero di abbonamenti da comprare: ");
                    int numAbbonamenti = scanner.nextInt();
                    scanner.nextLine();
                    try {
                        trattaDAO.compraAbbonamento(sceltaAbbonamento, numAbbonamenti);
                        System.out.println("Abbonamento/i acquistato/i con successo!");
                    } catch (TrattaException e) {
                        System.out.println("Errore: " + e.getMessage());
                    }
                }
                default -> System.out.println("Scelta non valida!");

                case 2 -> {
                    System.out.print("Inserisci zona di partenza: ");
                    String partenza = scanner.nextLine();
                    List<Tratta> tratte = trattaDAO.findByZonaPartenza(partenza);
                    trattaDAO.findByZonaPartenza(tratte);
                }
                case 3 -> {
                    System.out.print("Inserisci zona di arrivo: ");
                    String arrivo = scanner.nextLine();
                    List<Tratta> tratte = trattaDAO.findByZonaArrivo(arrivo);
                    trattaDAO.findByZonaArrivo(tratte);
                }
                case 4 -> {
                    List<Tratta> tratte = trattaDAO.ottieniListaTratte();
                    trattaDAO.ottieniListaTratte();
                }
                case 0 -> {
                    System.out.println("Arrivederci!");
                    return;
                }
                default -> System.out.println("Scelta non valida!");
            }
        }
    }

    // menu amministratore
    private static void menuAmministratore(Scanner scanner, TrattaDAO trattaDAO) {
        while (true) {
            System.out.println("\n MENU AMMINISTRATORE");
            System.out.println("1. Aggiungi nuova tratta");
            System.out.println("2. Elimina tratta");
            System.out.println("3. Visualizza tutte le tratte");
            System.out.println("4. Esci");
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
                    scanner.nextLine();

                    Tratta nuovaTratta = new Tratta(null, nome, partenza, arrivo, tempo, null);
                    trattaDAO.save(nuovaTratta);
                    System.out.println("Tratta salvata con successo!");
                }
                case 2 -> {
                    System.out.print("Inserisci ID della tratta da eliminare: ");
                    Long id = scanner.nextLong();
                    scanner.nextLine();
                    try {
                        trattaDAO.delete(id);
                        System.out.println("Tratta eliminata con successo!");
                    } catch (TrattaException e) {
                        System.out.println("Errore: " + e.getMessage());
                    }
                }
                case 3 -> {
                    List<Tratta> tratte = trattaDAO.findByZonaPartenza("");
                    stampaTratte(tratte);
                }
                case 4 -> {
                    System.out.println("Uscita dal menu amministratore!");
                    return;
                }
                default -> System.out.println("Scelta non valida!");
            }
        }
    }

    private static void stampaTratte(List<Tratta> tratte) {
        for (Tratta tratta : tratte) {
            System.out.println("ID: " + tratta.getId());
            System.out.println("Nome: " + tratta.getNomeTratta());
            System.out.println("Zona di partenza: " + tratta.getZonaPartenza());
            System.out.println("Zona di arrivo: " + tratta.getZonaArrivo());
            System.out.println("Tempo medio: " + tratta.getTempoMedioPercorrenza() + " minuti");
        }
    }
}