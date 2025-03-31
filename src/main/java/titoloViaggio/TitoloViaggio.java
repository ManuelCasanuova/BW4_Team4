package titoloViaggio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sistemadistribuzione.SistemaEmettitore;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class TitoloViaggio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long codiceUnivoco;
    private LocalDate dataEmissione;
    private boolean valido;

    @ManyToOne
    private SistemaEmettitore puntoEmittente;
    // bisogna inserire OneToMany in sistemaEmettitore e mappare la colonna con titolo di viaggio

    public TitoloViaggio(LocalDate dataEmissione, boolean valido, SistemaEmettitore puntoEmittente) {

        this.dataEmissione = dataEmissione;
        this.valido = valido;
        this.puntoEmittente = puntoEmittente;
    }


}