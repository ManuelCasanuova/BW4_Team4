package societa.trasporti.utenti;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Entity
@Table(name = "utenti")
@NoArgsConstructor


public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "utente_id")
    private Long utenteId;

    @Column(name = "nominativo", nullable = false)
    private String nominativo;

    @Column(name = "anno_nascita", nullable = false)
    private int annoNascita;


    @OneToOne(mappedBy = "utente")
    private Tessera tessera;


    public Utente(String nominativo, int annoNascita) {
        this.nominativo = nominativo;
        this.annoNascita = annoNascita;
    }


}