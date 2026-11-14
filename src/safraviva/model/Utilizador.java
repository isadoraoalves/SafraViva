package safraviva.model;

import java.io.Serializable;

public abstract class Utilizador implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String nome;
    private String contacto;
    private String cidade;

    public Utilizador(String id, String nome, String contacto, String cidade) {
        this.id = id;
        this.nome = nome;
        this.contacto = contacto;
        this.cidade = cidade;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getContacto() { return contacto; }
    public String getCidade() { return cidade; }

    public abstract String getDescricaoPapel();
}