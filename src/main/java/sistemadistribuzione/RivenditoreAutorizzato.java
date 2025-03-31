package sistemadistribuzione;

public class RivenditoreAutorizzato extends SistemaEmettitore{

    private String nome;


    public RivenditoreAutorizzato(Long id, String nome) {
        super(id);
        this.nome = nome;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}
