package utenti;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "persone")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String nome;

    @Column
    private String cognome;
}