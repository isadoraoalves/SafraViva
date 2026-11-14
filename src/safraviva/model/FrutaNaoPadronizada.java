package safraviva.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FrutaNaoPadronizada extends LoteAlimento {
    private int grauMaturacao; // Nível de 1 a 5

    public FrutaNaoPadronizada(String idLote, String descricao, double pesoKg, 
                               LocalDate dataColheita, ProdutorRural produtor, int grauMaturacao) {
        super(idLote, descricao, pesoKg, dataColheita, produtor);
        this.grauMaturacao = grauMaturacao;
    }

    public int getGrauMaturacao() { return grauMaturacao; }

    @Override
    public int calcularDiasValidadeRestante(double temperaturaMediaC) {
        long diasDesdeColheita = ChronoUnit.DAYS.between(getDataColheita(), LocalDate.now());
        int diasBase = 15 - (grauMaturacao * 2);
        int restantes = (int) (diasBase - diasDesdeColheita);
        return Math.max(restantes, 0);
    }

    @Override
    public double calcularIndicePrioridade() {
        return 2.0 * grauMaturacao;
    }
}