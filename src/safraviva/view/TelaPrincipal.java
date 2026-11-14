package safraviva.view;

import safraviva.excecoes.CapacidadeExcedidaException;
import safraviva.excecoes.LoteExpiradoException;
import safraviva.model.*;
import safraviva.service.SafraVivaService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class TelaPrincipal extends JFrame {
    private SafraVivaService service;
    private DefaultTableModel tableModel;
    private JTable tabelaLotes;

    private ProdutorRural produtorExemplo;
    private EntidadeBeneficiaria beneficiarioExemplo;
    private TransportadorVoluntario transportadorExemplo;

    public TelaPrincipal() {
        super("SafraViva - Sistema de Gestão contra Desperdício de Alimentos");
        this.service = new SafraVivaService();

        // Dados base para testes e demonstração ao professor
        this.produtorExemplo = new ProdutorRural("P01", "Sítio Boa Esperança", "34-9999-1111", "Uberlândia", "DAP-MG-882", "Fazenda Esperança");
        this.beneficiarioExemplo = new EntidadeBeneficiaria("B01", "Cozinha Comunitária Esperança", "34-3232-4444", "Uberlândia", "CNPJ-12.345.678/0001-90", 350.0);
        this.transportadorExemplo = new TransportadorVoluntario("T01", "Carlos Andrade", "34-9888-2222", "Uberlândia", "CNH-55443322", 200.0, "Furgão Refrigerado");

        configurarJanela();
        atualizarTabela();
    }

    private void configurarJanela() {
        setSize(850, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Título Superior
        JLabel lblTitulo = new JLabel("Painel Logístico de Alimentos Resgatados (SafraViva)", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(lblTitulo, BorderLayout.NORTH);

        // Tabela Central
        String[] colunas = {"ID", "Descrição", "Tipo", "Peso (kg)", "Dias Validade", "Índice Urgência"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabelaLotes = new JTable(tableModel);
        add(new JScrollPane(tabelaLotes), BorderLayout.CENTER);

        // Painel de Ações Inferior
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton btnAdicionarFolhoso = new JButton("Adicionar Folhoso");
        JButton btnAdicionarFruta = new JButton("Adicionar Fruta");
        JButton btnAdicionarTubérculo = new JButton("Adicionar Tubérculo");
        JButton btnCriarOrdem = new JButton("Despachar Ordem");

        painelBotoes.add(btnAdicionarFolhoso);
        painelBotoes.add(btnAdicionarFruta);
        painelBotoes.add(btnAdicionarTubérculo);
        painelBotoes.add(btnCriarOrdem);
        add(painelBotoes, BorderLayout.SOUTH);

        // Eventos dos botões
        btnAdicionarFolhoso.addActionListener(e -> cadastrarExemploFolhoso());
        btnAdicionarFruta.addActionListener(e -> cadastrarExemploFruta());
        btnAdicionarTubérculo.addActionListener(e -> cadastrarExemploTuberculo());
        btnCriarOrdem.addActionListener(e -> despacharOrdem());
    }

    private void cadastrarExemploFolhoso() {
        String id = "L-FOLHA-" + (service.listarTodosLotes().size() + 1);
        HortifrutiFolhoso folhoso = new HortifrutiFolhoso(id, "Alface Crespa Excedente", 30.0, LocalDate.now().minusDays(1), produtorExemplo);
        service.cadastrarLote(folhoso);
        atualizarTabela();
        JOptionPane.showMessageDialog(this, "Lote de folhoso registado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void cadastrarExemploFruta() {
        String id = "L-FRUTA-" + (service.listarTodosLotes().size() + 1);
        FrutaNaoPadronizada fruta = new FrutaNaoPadronizada(id, "Manga Fora do Padrão Estético", 50.0, LocalDate.now().minusDays(2), produtorExemplo, 4);
        service.cadastrarLote(fruta);
        atualizarTabela();
        JOptionPane.showMessageDialog(this, "Lote de fruta registado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void cadastrarExemploTuberculo() {
        String id = "L-TUB-" + (service.listarTodosLotes().size() + 1);
        TuberculoGrao tuberculo = new TuberculoGrao(id, "Batata Doce Calibre Pequeno", 100.0, LocalDate.now().minusDays(3), produtorExemplo);
        service.cadastrarLote(tuberculo);
        atualizarTabela();
        JOptionPane.showMessageDialog(this, "Lote de tubérculo registado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void despacharOrdem() {
        List<LoteAlimento> lotes = service.listarTodosLotes();
        if (lotes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum lote disponível para despacho.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LoteAlimento loteSelecionado = lotes.get(lotes.size() - 1);
        try {
            // Tentativa de criar a ordem com validação das regras de negócio
            service.criarOrdemDistribuicao(
                "ORD-" + System.currentTimeMillis() % 10000,
                beneficiarioExemplo,
                transportadorExemplo,
                loteSelecionado,
                loteSelecionado.getPesoKg(),
                22.0 // Temperatura ambiente em Celsius
            );
            JOptionPane.showMessageDialog(this, "Ordem de despacho criada com sucesso para a entidade!", "Ordem Criada", JOptionPane.INFORMATION_MESSAGE);
        } catch (LoteExpiradoException | CapacidadeExcedidaException ex) {
            JOptionPane.showMessageDialog(this, "Falha na validação de regras de negócio:\n" + ex.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        for (LoteAlimento lote : service.listarTodosLotes()) {
            tableModel.addRow(new Object[]{
                lote.getIdLote(),
                lote.getDescricao(),
                lote.getClass().getSimpleName(),
                lote.getPesoKg(),
                lote.calcularDiasValidadeRestante(22.0) + " dias",
                lote.calcularIndicePrioridade()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaPrincipal tela = new TelaPrincipal();
            tela.setVisible(true);
        });
    }
}