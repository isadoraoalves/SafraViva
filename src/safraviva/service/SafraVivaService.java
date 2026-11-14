package safraviva.service;

import safraviva.excecoes.CapacidadeExcedidaException;
import safraviva.excecoes.LoteExpiradoException;
import safraviva.model.*;
import safraviva.repository.LoteRepository;
import safraviva.repository.OrdemRepository;

import java.util.List;

public class SafraVivaService {
    private LoteRepository loteRepo;
    private OrdemRepository ordemRepo;

    public SafraVivaService() {
        this.loteRepo = new LoteRepository();
        this.ordemRepo = new OrdemRepository();
    }

    public void cadastrarLote(LoteAlimento lote) {
        loteRepo.salvar(lote);
    }

    public List<LoteAlimento> listarTodosLotes() {
        return loteRepo.listarTodos();
    }

    public List<OrdemDistribuicao> listarTodasOrdens() {
        return ordemRepo.listarTodos();
    }

    // Regra de negocio: valida validade do lote e capacidade do veiculo antes de criar a ordem
    public OrdemDistribuicao criarOrdemDistribuicao(String idOrdem, 
                                                   EntidadeBeneficiaria beneficiario, 
                                                   TransportadorVoluntario voluntario, 
                                                   LoteAlimento lote, 
                                                   double quantidadeKg, 
                                                   double temperaturaAmbienteC) 
                                                   throws LoteExpiradoException, CapacidadeExcedidaException {
        
        // 1. Validacao polimorfica de validade restante
        int diasRestantes = lote.calcularDiasValidadeRestante(temperaturaAmbienteC);
        if (diasRestantes <= 0) {
            throw new LoteExpiradoException("O lote '" + lote.getDescricao() + "' expirou (0 dias restantes) e noo pode ser despachado.");
        }

        // 2. Validacao da capacidade de carga do voluntario
        if (quantidadeKg > voluntario.getCapacidadeCargaKg()) {
            throw new CapacidadeExcedidaException("A quantidade (" + quantidadeKg + " kg) excede a capacidade do veiculo (" 
                                                 + voluntario.getCapacidadeCargaKg() + " kg).");
        }

        // 3. Validacao do limite diario do beneficiario
        if (quantidadeKg > beneficiario.getCapacidadeDiariaKg()) {
            throw new CapacidadeExcedidaException("A quantidade (" + quantidadeKg + " kg) ultrapassa a capacidade de rececao diaria da entidade (" 
                                                 + beneficiario.getCapacidadeDiariaKg() + " kg).");
        }

        // Cria a ordem e associa o item
        OrdemDistribuicao ordem = new OrdemDistribuicao(idOrdem, beneficiario, voluntario);
        ordem.adicionarItem(lote, quantidadeKg);
        ordem.setStatus("EM_TRANSITO");

        // Notifica a entidade beneficiaria atraves da interface Notificavel
        beneficiario.receberAlerta("Remessa #" + idOrdem + " a caminho com " + quantidadeKg + " kg de " + lote.getDescricao());

        // Grava no repositorio com persistencia em ficheiro
        ordemRepo.salvar(ordem);
        return ordem;
    }
}