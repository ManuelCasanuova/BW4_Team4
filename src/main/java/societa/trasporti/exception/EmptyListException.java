package societa.trasporti.exception;

public class EmptyListException extends RuntimeException {
  public EmptyListException(){super("La ricerca non ha prodotto alcun risultato");}
}
