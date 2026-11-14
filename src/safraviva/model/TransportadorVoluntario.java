package safraviva.model;

public class TransportadorVoluntario extends Utilizador {
    private String licencaConducao;
    private double capacidadeCargaKg;
    private String tipoVeiculo;

    public TransportadorVoluntario(String id, String nome, String contacto, String cidade, 
                                   String licencaConducao, double capacidadeCargaKg, String tipoVeiculo) {
        super(id, nome, contacto, cidade);
        this.licencaConducao = licencaConducao;
        this.capacidadeCargaKg = capacidadeCargaKg;
        this.tipoVeiculo = tipoVeiculo;
    }

    public String getLicencaConducao() { return licencaConducao; }
    public double getCapacidadeCargaKg() { return capacidadeCargaKg; }
    public String getTipoVeiculo() { return tipoVeiculo; }

    @Override
    public String getDescricaoPapel() {
        return "Transportador Voluntario";
    }
}