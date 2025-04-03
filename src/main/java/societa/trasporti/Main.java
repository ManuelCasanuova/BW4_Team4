package societa.trasporti;

import societa.trasporti.DAO.TrattaDAO;
import societa.trasporti.exception.TrattaException;
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
            System.out.println("Sistema di Gestione Trasporti Pubblici");

            //  Creazione di nuove tratte
            System.out.println("\n▶ Creazione nuove tratte...");
            Tratta tratta1 = new Tratta(null, "Linea A", "Centro", "Periferia", 30, null);
            Tratta tratta2 = new Tratta(null, "Linea B", "Stazione", "Aeroporto", 45, null);

            trattaDAO.save(tratta1);
            trattaDAO.save(tratta2);

            System.out.println("Tratte salvate con successo!");

            //  Ricerca per zona di partenza
            System.out.print("\n🔍 Inserisci la zona di partenza per cercare le tratte: ");
            String zonaPartenza = scanner.nextLine();
            List<Tratta> trattePartenza = trattaDAO.findByZonaPartenza(zonaPartenza);
            if (!trattePartenza.isEmpty()) {
                System.out.println("Tratte trovate:");
                trattePartenza.forEach(System.out::println);
            } else {
                System.out.println("Nessuna tratta trovata per la zona di partenza: " + zonaPartenza);
            }

            //  Ricerca per zona di arrivo
            System.out.print("\n🔍 Inserisci la zona di arrivo per cercare le tratte: ");
            String zonaArrivo = scanner.nextLine();
            List<Tratta> tratteArrivo = trattaDAO.findByZonaArrivo(zonaArrivo);
            if (!tratteArrivo.isEmpty()) {
                System.out.println("Tratte trovate:");
                tratteArrivo.forEach(System.out::println);
            } else {
                System.out.println("Nessuna tratta trovata per la zona di arrivo: " + zonaArrivo);
            }

            // Recupero di una tratta per ID
            System.out.print("\n🔍 Inserisci l'ID di una tratta per visualizzarla: ");
            Long idTratta = scanner.nextLong();
            Optional<Tratta> trattaOptional = trattaDAO.findById(idTratta);
            if (trattaOptional.isPresent()) {
                System.out.println("Dettagli tratta: " + trattaOptional.get());
            } else {
                System.out.println("⚠Nessuna tratta trovata con ID: " + idTratta);
            }

            // Simulazione creazione e assegnazione di un mezzo di trasporto
            ParcoMezzi autobus = new ParcoMezzi(1001L, TipoVeicolo.AUTOBUS);
            System.out.println("\n Mezzo creato: " + autobus);
            System.out.println("Assegnazione autobus alla tratta " + tratta1.getNomeTratta());

            // Visualizzazione di tutte le tratte
            System.out.println("\n Tutte le tratte registrate:");
            List<Tratta> tutteTratte = trattaDAO.findByZonaPartenza(""); // Recupera tutte le tratte
            tutteTratte.forEach(System.out::println);

        } catch (TrattaException e) {
            System.err.println("Errore: " + e.getMessage());
        } finally {
            trattaDAO.close();
            scanner.close();
        }
    }
}