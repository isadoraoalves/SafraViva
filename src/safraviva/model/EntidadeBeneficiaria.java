package safraviva.model;

import safraviva.model.interfaces.Notificavel;

public class EntidadeBeneficiaria extends Utilizador implements Notificavel {
    private String documentoSocial;
    private double capacidadeDiariaKg;

    public EntidadeBeneficiaria(String id, String nome, String contacto, String cidade, 
                                String documentoSocial, double capacidadeDiariaKg) {
        super(id, nome, contacto, cidade);
        this.documentoSocial = documentoSocial;
        this.capacidadeDiariaKg = capacidadeDiariaKg;
    }

    public String getDocumentoSocial() { return documentoSocial; }
    public double getCapacidadeDiariaKg() { return capacidadeDiariaKg; }

    @Override
    public String getDescricaoPapel() {
        return "Entidade Beneficiaria";
    }

    @Override
    public void receberAlerta(String mensagem) {
        System.out.println("[ALERTA - " + getNome() + "]: " + mensagem);
    }
}