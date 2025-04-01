package DAO;

import jakarta.persistence.EntityManager;
import titoloViaggio.abbonamento.Abbonamento;
import titoloViaggio.abbonamento.TipoAbbonamento;
import utenti.Utente;
import java.time.LocalDate;
import java.util.List;

public class AbbonamentoDAO extends GenericDAO<Abbonamento> {

    public AbbonamentoDAO(EntityManager entityManager) {
        super(entityManager);
    }

    // Metodo per emettere un nuovo abbonamento
    public Abbonamento emettiAbbonamento(Long idUtente, TipoAbbonamento tipo, LocalDate dataInizio) {
        if (idUtente == null) {
            throw new IllegalArgumentException("L'ID Utente non può essere nullo.");
        }

        // Recupera l'utente dal database
        Utente utente = getEntityManager().find(Utente.class, idUtente);
        if (utente == null) {
            throw new IllegalArgumentException("Utente con ID " + idUtente + " non trovato.");
        }

        // Creazione dell'abbonamento
        Abbonamento abbonamento = new Abbonamento();
        abbonamento.setTipoAbbonamento(tipo);
        abbonamento.setDataInizio(dataInizio);
        abbonamento.setDataScadenza(calcolaDataScadenza(tipo, dataInizio));
        abbonamento.setUtente(utente); // Associa l'abbonamento all'utente

        save(abbonamento); // Salva l'abbonamento nel database
        return abbonamento;
    }

    // Metodo per calcolare la data di scadenza
    private LocalDate calcolaDataScadenza(TipoAbbonamento tipo, LocalDate dataInizio) {
        switch (tipo) {
            case SETTIMANALE:
                return dataInizio.plusWeeks(1);
            case MENSILE:
                return dataInizio.plusMonths(1);
            default:
                throw new IllegalArgumentException("Tipo di abbonamento non valido: " + tipo);
        }
    }

    // Metodo per verificare la validità di un abbonamento
    public boolean checkValiditaAbbonamento(Long idAbbonamento) {
        Abbonamento abbonamento = findById(Abbonamento.class, idAbbonamento);
        return abbonamento != null && abbonamento.getDataScadenza().isAfter(LocalDate.now());
    }

    // Metodo per trovare tutti gli abbonamenti di un utente
    public List<Abbonamento> findByUtente(Long idUtente) {
        String query = "SELECT a FROM Abbonamento a WHERE a.utente.id = :idUtente";
        return getEntityManager()
                .createQuery(query, Abbonamento.class)
                .setParameter("idUtente", idUtente)
                .getResultList();
    }
}