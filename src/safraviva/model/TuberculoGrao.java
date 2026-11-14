package safraviva.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class TuberculoGrao extends LoteAlimento {
    private static final int VIDA_UTIL_BASE_DIAS = 45;

    public TuberculoGrao(String idLote, String descricao, double pesoKg, 
                         LocalDate dataColheita, ProdutorRural produtor) {
        super(idLote, descricao, pesoKg, dataColheita, produtor);
    }

    @Override
    public int calcularDiasValidadeRestante(double temperaturaMediaC) {
        long diasDesdeColheita = ChronoUnit.DAYS.between(getDataColheita(), LocalDate.now());
        int restantes = (int) (VIDA_UTIL_BASE_DIAS - diasDesdeColheita);
        return Math.max(restantes, 0);
    }

    @Override
    public double calcularIndicePrioridade() {
        return 4.0;
    }
}