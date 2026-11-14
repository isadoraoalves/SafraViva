package safraviva.model;

public class ProdutorRural extends Utilizador {
    private String registoRural;
    private String nomePropriedade;

    public ProdutorRural(String id, String nome, String contacto, String cidade, 
                         String registoRural, String nomePropriedade) {
        super(id, nome, contacto, cidade);
        this.registoRural = registoRural;
        this.nomePropriedade = nomePropriedade;
    }

    public String getRegistoRural() { return registoRural; }
    public String getNomePropriedade() { return nomePropriedade; }

    @Override
    public String getDescricaoPapel() {
        return "Produtor Rural";
    }
}