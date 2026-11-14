package safraviva.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class HortifrutiFolhoso extends LoteAlimento {
    private static final int VIDA_UTIL_BASE_DIAS = 7;

    public HortifrutiFolhoso(String idLote, String descricao, double pesoKg, 
                             LocalDate dataColheita, ProdutorRural produtor) {
        super(idLote, descricao, pesoKg, dataColheita, produtor);
    }

    @Override
    public int calcularDiasValidadeRestante(double temperaturaMediaC) {
        long diasDesdeColheita = ChronoUnit.DAYS.between(getDataColheita(), LocalDate.now());
        double fatorTemperatura = (temperaturaMediaC > 15.0) ? 1.8 : 1.0;
        int diasRestantes = (int) (VIDA_UTIL_BASE_DIAS - (diasDesdeColheita * fatorTemperatura));
        return Math.max(diasRestantes, 0);
    }

    @Override
    public double calcularIndicePrioridade() {
        return 9.5;
    }
}