package societatrasporti.classi.tratta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import societatrasporti.classi.servizi.Servizio;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "tratte")
@AllArgsConstructor
@NoArgsConstructor

public class Tratta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "tratta_id")
    private Long trattaId;

    @Column(name = "nome_tratta")
    private String nomeTratta;

    @Column (name = "partenza")

    private String zonaPartenza;

    @Column (name = "arrivo")
    private String zonaArrivo;

    @Column (name = "tempo_medio_percorrenza")
    private int tempoMedioPercorrenza;

    @OneToMany(mappedBy = "tratta", cascade = CascadeType.ALL)
    private List<Servizio> serviziList = new ArrayList<>();

    public Tratta(String nomeTratta, String zonaPartenza, String zonaArrivo, int tempoMedioPercorrenza) {
        this.zonaPartenza = zonaPartenza;
        this.zonaArrivo = zonaArrivo;
        this.tempoMedioPercorrenza = tempoMedioPercorrenza;
        this.nomeTratta = nomeTratta;
    }

}