package safraviva.model;

import java.io.Serializable;

public class ItemDistribuicao implements Serializable {
    private static final long serialVersionUID = 1L;

    private LoteAlimento lote; // Agregacao
    private double quantidadeAlocadaKg;

    public ItemDistribuicao(LoteAlimento lote, double quantidadeAlocadaKg) {
        this.lote = lote;
        this.quantidadeAlocadaKg = quantidadeAlocadaKg;
    }

    public LoteAlimento getLote() { return lote; }
    public double getQuantidadeAlocadaKg() { return quantidadeAlocadaKg; }
}