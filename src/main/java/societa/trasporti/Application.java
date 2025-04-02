package societa.trasporti;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import societa.trasporti.manutenzione.ManutenzioneDAO;
import societa.trasporti.parchiMezzi.ParcoMezzi;
import societa.trasporti.parchiMezzi.ParcoMezziDAO;
import societa.trasporti.servizi.ServizioDAO;
import societa.trasporti.titoloViaggio.biglietto.TitoloViaggioDAO;
import societa.trasporti.tratta.TrattaDAO;
import societa.trasporti.utenti.TesseraDAO;
import societa.trasporti.utenti.UtenteDAO;
import societa.trasporti.vendita.PuntoVenditaDAO;

public class Application {
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory("epicode");
    private static boolean bigliettiObliteratiInizializzati=false;

    public static void main (String[] args) {
        EntityManager em = emf.createEntityManager();

        PuntoVenditaDAO puntoVenditaDAO = new PuntoVenditaDAO(em);
        TrattaDAO trattaDAO = new TrattaDAO(em);
        UtenteDAO utenteDao = new UtenteDAO(em);
        TesseraDAO tesseraDAO = new TesseraDAO(em);
        TitoloViaggioDAO titoloViaggioDAO = new TitoloViaggioDAO(em);
        ParcoMezziDAO parcoMezziDAO = new ParcoMezziDAO(em);
        ManutenzioneDAO manutenzioneDAO = new ManutenzioneDAO(em);
        ServizioDAO servizioDAO = new ServizioDAO();


        em.close();
        emf.close();
    }

    public static void inizializzaDB(
            PuntoVenditaDAO puntoVenditaDAO,
            TrattaDAO trattaDAO,
            UtenteDAO utenteDao,
            TesseraDAO tesseraDAO,
            TitoloViaggioDAO titoloViaggioDAO,
            ParcoMezziDAO parcoMezziDAO,
            ManutenzioneDAO manutenzioneDAO,
            ServizioDAO servizioDAO,
            EntityManager em) {

            if(puntoVenditaDAO.ottieniListaPuntiVendita().isEmpty()) {
                for (int i = 0; i < 10; i++) {

                }
            }
    }


}
