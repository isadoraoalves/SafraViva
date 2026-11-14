package safraviva.model;

import safraviva.model.interfaces.Rastreavel;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class LoteAlimento implements Rastreavel, Serializable {
    private static final long serialVersionUID = 1L;

    private String idLote;
    private String descricao;
    private double pesoKg;
    private LocalDate dataColheita;
    private ProdutorRural produtor; // Agregação
    private List<String> historicoRastreio;

    public LoteAlimento(String idLote, String descricao, double pesoKg, 
                        LocalDate dataColheita, ProdutorRural produtor) {
        this.idLote = idLote;
        this.descricao = descricao;
        this.pesoKg = pesoKg;
        this.dataColheita = dataColheita;
        this.produtor = produtor;
        this.historicoRastreio = new ArrayList<>();
        registarPontoRastreio("Lote registado pelo produtor " + produtor.getNome());
    }

    public String getIdLote() { return idLote; }
    public String getDescricao() { return descricao; }
    public double getPesoKg() { return pesoKg; }
    public LocalDate getDataColheita() { return dataColheita; }
    public ProdutorRural getProdutor() { return produtor; }

    @Override
    public void registarPontoRastreio(String ponto) {
        this.historicoRastreio.add(LocalDate.now() + " - " + ponto);
    }

    @Override
    public List<String> obterHistoricoRastreio() {
        return new ArrayList<>(historicoRastreio);
    }

    // Métodos polimórficos obrigatórios do barema
    public abstract int calcularDiasValidadeRestante(double temperaturaMediaC);
    public abstract double calcularIndicePrioridade();
}