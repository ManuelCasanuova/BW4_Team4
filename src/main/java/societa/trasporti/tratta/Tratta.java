package societa.trasporti.tratta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import societa.trasporti.parchiMezzi.ParcoMezzi;
import societa.trasporti.servizi.Servizio;

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
    private Long id;

    @Column(name = "nome_tratta")
    private String nomeTratta;


    @Column (name = "partenza")
    private String zonaPartenza;

    @Column (name = "arrivo")
    private String zonaArrivo;

    @Column (name = "tempo_medio_percorrenza")
    private int tempoMedioPercorrenza;

    @OneToMany(mappedBy = "tratta", cascade = CascadeType.ALL)
    private List<Servizio> serviziList;



}