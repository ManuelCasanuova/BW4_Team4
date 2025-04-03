package societa.trasporti.classiAggiuntive;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import societa.trasporti.classiAggiuntive.patente.Patente;
import societa.trasporti.classiAggiuntive.turno.Turno;
import societa.trasporti.parchiMezzi.ParcoMezzi;
import societa.trasporti.tratta.Tratta;

import java.util.List;

@Data
@Entity
@Table(name = "autisti")
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Autista {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long matricola;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cognome",  nullable = false)
    private String cognome;

    @OneToOne(mappedBy = "patente")
    private Patente patente;

    @Column(name = "disponibile", nullable = false)
    private boolean disponibile;

    @OneToMany(mappedBy = "turni")
    private List<Turno> turni;

    // Inserire relazione in Tratta
    @OneToOne(mappedBy = "tratta")
    private Tratta tratta;

    // Inserire relazione in ParcoMezzi
    @OneToOne(mappedBy = "mezzo")
    private ParcoMezzi mezzo;

    public Autista(String nome, String cognome, Patente patente, boolean disponibile ,  Tratta tratta, ParcoMezzi mezzo) {
        this.nome = nome;
        this.cognome = cognome;
        this.patente = patente;
        this.disponibile = disponibile;
        this.tratta = tratta;
        this.mezzo = mezzo;
    }


}
