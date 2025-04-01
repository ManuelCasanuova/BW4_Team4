package societa.trasporti.exception;

public class ManutenzioneOrServizioException extends RuntimeException {
    public ManutenzioneOrServizioException(String manutenzioneOrServizio , boolean isOrIsNot) {
        super("Attenzione! Il veicolo " + (isOrIsNot ? "è " : "non è ") + manutenzioneOrServizio);
    }

}
