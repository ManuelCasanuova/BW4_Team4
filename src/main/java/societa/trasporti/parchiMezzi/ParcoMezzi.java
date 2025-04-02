package societa.trasporti.parchiMezzi;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import societa.trasporti.manutenzione.Manutenzione;
import societa.trasporti.servizi.Servizio;

import java.util.List;

@Data
@Entity
@Table(name = "mezzi_pubblici")
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ParcoMezzi {

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

    @OneToMany(mappedBy = "parcoMezzi", cascade = CascadeType.ALL)
    private List<Servizio> serviziList;

    @OneToMany(mappedBy = "parcoMezzi", cascade = CascadeType.ALL)
    private List<Manutenzione> manutenzionList;

    public ParcoMezzi(Long matricola, TipoVeicolo tipoVeicolo) {
        this.matricola = matricola;
        this.tipoVeicolo = tipoVeicolo;
        this.capienza = tipoVeicolo ==  TipoVeicolo.AUTOBUS ? 50 : 150;
    }
}