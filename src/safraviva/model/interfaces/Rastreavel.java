package safraviva.model.interfaces;

import java.util.List;

public interface Rastreavel {
    void registarPontoRastreio(String ponto);
    List<String> obterHistoricoRastreio();
}