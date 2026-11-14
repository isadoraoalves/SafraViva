package safraviva.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdemDistribuicao implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idOrdem;
    private LocalDateTime dataCriacao;
    private String status;
    private EntidadeBeneficiaria beneficiario; // Agregacao
    private TransportadorVoluntario voluntario; // Agregacao
    private List<ItemDistribuicao> itens; // Composicao

    public OrdemDistribuicao(String idOrdem, EntidadeBeneficiaria beneficiario, TransportadorVoluntario voluntario) {
        this.idOrdem = idOrdem;
        this.beneficiario = beneficiario;
        this.voluntario = voluntario;
        this.dataCriacao = LocalDateTime.now();
        this.status = "CRIADA";
        this.itens = new ArrayList<>();
    }

    public void adicionarItem(LoteAlimento lote, double quantidadeKg) {
        ItemDistribuicao item = new ItemDistribuicao(lote, quantidadeKg);
        this.itens.add(item);
        lote.registarPontoRastreio("Alocado na Ordem #" + idOrdem + " (" + quantidadeKg + " kg)");
    }

    public double calcularPesoTotal() {
        double total = 0.0;
        for (ItemDistribuicao item : itens) {
            total += item.getQuantidadeAlocadaKg();
        }
        return total;
    }

    public String getIdOrdem() { return idOrdem; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public EntidadeBeneficiaria getBeneficiario() { return beneficiario; }
    public TransportadorVoluntario getVoluntario() { return voluntario; }
    public List<ItemDistribuicao> getItens() { return new ArrayList<>(itens); }
}