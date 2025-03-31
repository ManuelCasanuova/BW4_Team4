package societa.trasporti.parchiMezzi;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "tratte")
public class Tratta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String zonaPartenza;

    @Column
    private String zonaArrivo;

    @Column
    private int tempoPrevisto;

    @OneToMany(mappedBy = "tratta", cascade = CascadeType.ALL) // Relazione 1-N
    private List<ParcoMezzi> mezzi; // Lista dei mezzi collegati alla tratta

    public Tratta() {}

    public Tratta(String zonaPartenza, String zonaArrivo, int tempoPrevisto) {
        this.zonaPartenza = zonaPartenza;
        this.zonaArrivo = zonaArrivo;
        this.tempoPrevisto = tempoPrevisto;
    }
}