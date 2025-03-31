package societa.trasporti.parchiMezzi;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "mezzi_pubblici")
@NoArgsConstructor


public abstract class ParcoMezzi {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long matricola;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_veicolo")
    private TipoVeicolo tipoVeicolo;

    @Column
    private int capienza;

    @Column(name = "in_servizio")
    private boolean inServizio=false;

    @Column (name = "in_manutenzione")
    private boolean inManutenzione=false;

    public ParcoMezzi(Long matricola, TipoVeicolo tipoVeicolo) {
        this.matricola = matricola;
        this.tipoVeicolo = tipoVeicolo;
        this.capienza = tipoVeicolo ==  TipoVeicolo.AUTOBUS ? 50 : 150;
    }
}