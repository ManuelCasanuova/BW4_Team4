import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

public class Tratta {

    @Getter
    @Id
    private Long id;

    @Setter
    @Getter
    private String zonaPartenza;

    @Setter
    @Getter
    private String zonaArrivo;

    @Setter
    @Getter
    private int tempoPrevisto;

    public Tratta() {}

    public Tratta(String zonaPartenza, String capolinea, int tempoPrevisto) {
        this.zonaPartenza = zonaPartenza;
        this.zonaArrivo = capolinea;
        this.tempoPrevisto = tempoPrevisto;
    }
}
