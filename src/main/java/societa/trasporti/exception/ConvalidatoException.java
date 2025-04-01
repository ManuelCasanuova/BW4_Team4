package societa.trasporti.exception;

import societa.trasporti.parchiMezzi.ParcoMezzi;

public class ConvalidatoException extends RuntimeException {
    public ConvalidatoException(Long codiceUnivoco, ParcoMezzi parcoMezzi ) {
        super("Biglietto codice " + codiceUnivoco + " già convalidato sul mezzo con matricola " + parcoMezzi.getMatricola());
    }


}
