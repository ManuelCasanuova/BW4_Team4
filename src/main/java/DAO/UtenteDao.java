package DAO;

import jakarta.persistence.EntityManager;
import utenti.Utente;
import utenti.Tessera;
import java.time.LocalDate;



public class UtenteDao extends GenericDAO<Utente> {

    public UtenteDao(EntityManager entityManager) {
        super(entityManager);
    }

    //  registra un nuovo utente
    public Utente registraUtente(String nome, String cognome, String tipoUtente) {
        Utente utente = new Utente();
        utente.setNome(nome);
        utente.setCognome(cognome);
        utente.setTipoUtente(tipoUtente);

        save(utente);
        return utente;
    }


    // assegna una tessera a un utente
    public void aggiungiTessera(Utente utente, String numeroTessera, LocalDate dataDiEmissione, LocalDate dataDiScadenza) {
        Tessera tessera = new Tessera();
        tessera.setNumeroTessera(numeroTessera);
        tessera.setDataDiEmissione(dataDiEmissione);
        tessera.setDataDiScadenza(dataDiScadenza);
        tessera.setUtente(utente);
        utente.setTessera(tessera);
        update(utente);
    }
}