package utenti;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "utenti")
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String tipoUtente; // "Semplice", "Amministratore"

    @OneToOne(mappedBy = "utente", cascade = CascadeType.ALL) // Lato inverso
    private Tessera tessera; // La tessera associata all'utente
}