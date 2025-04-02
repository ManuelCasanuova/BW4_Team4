package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import societa.trasporti.DAO.*;
import societa.trasporti.tratta.Tratta;
import societa.trasporti.utenti.Utente;
import societa.trasporti.vendita.PuntoVendita;
import societa.trasporti.vendita.PuntoVenditaDAO;
import societa.trasporti.vendita.rivenditori.RivenditoreAutorizzato;
import societa.trasporti.vendita.distributori.Distributore;
import java.util.List;

public class MainTest {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("epicode");

    public static void main(String[] args) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            // Creazione DAO
            PuntoVenditaDAO puntoVenditaDAO = new PuntoVenditaDAO(em);
            UtenteDao utenteDao = new UtenteDao(em);
            TrattaDAO trattaDAO = new TrattaDAO();

            // Apertura della transazione
            em.getTransaction().begin();

            // **1. Creazione e salvataggio dei dati**
            PuntoVendita pv1 = new RivenditoreAutorizzato("Via Roma 10", "Tabaccheria Rossi");
            PuntoVendita pv2 = new Distributore("Piazza Garibaldi", true);
            Utente utente1 = new Utente("Mario Rossi", 1985);
            Utente utente2 = new Utente("Anna Bianchi", 1990);
            Tratta tratta1 = new Tratta();
            tratta1.setNomeTratta("Linea 1");
            tratta1.setZonaPartenza("Centro");
            tratta1.setZonaArrivo("Stazione");
            tratta1.setTempoMedioPercorrenza(20);

            Tratta tratta2 = new Tratta();
            tratta2.setNomeTratta("Linea 2");
            tratta2.setZonaPartenza("Porto");
            tratta2.setZonaArrivo("Università");
            tratta2.setTempoMedioPercorrenza(15);

            // Salvataggio delle tratte
            trattaDAO.save(tratta1);
            trattaDAO.save(tratta2);


            // Salvataggio dati
            puntoVenditaDAO.SalvaPuntiVendita(pv1);
            puntoVenditaDAO.SalvaPuntiVendita(pv2);
            utenteDao.save(utente1);
            utenteDao.save(utente2);
            trattaDAO.save(tratta1);
            trattaDAO.save(tratta2);

            // **2. Conferma della transazione**
            em.getTransaction().commit();

            // **3. Recupero e stampa dei dati salvati**
            System.out.println("Lista punti vendita:");
            List<PuntoVendita> puntiVendita = puntoVenditaDAO.ottieniListaPuntiVendita();
            puntiVendita.forEach(System.out::println);

            System.out.println("\nLista utenti:");
            System.out.println(utenteDao.findUtente(utente1.getUtenteId()));
            System.out.println(utenteDao.findUtente(utente2.getUtenteId()));

            System.out.println("\nLista tratte:");
            System.out.println(trattaDAO.findById(tratta1.getId()));
            System.out.println(trattaDAO.findById(tratta2.getId()));

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Errore durante l'esecuzione: " + e.getMessage());
        } finally {
            em.close();
            ENTITY_MANAGER_FACTORY.close();
        }
    }
}
