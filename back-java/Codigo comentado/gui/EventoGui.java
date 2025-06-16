package felipe.nascimento.gui;

import felipe.nascimento.model.Evento;
import felipe.nascimento.model.Palestrante;
import felipe.nascimento.service.EventoService;
import felipe.nascimento.service.PalestranteService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Tela para o CRUD (Criar, Ler, Atualizar, Deletar) de Eventos.
 */
public class EventoGui extends JFrame {
    // Camadas de serviço para acessar a lógica de negócio
    private final EventoService eventoService;
    private final PalestranteService palestranteService;

    // Componentes da interface
    private JTextField tfId, tfNome, tfData;
    private JComboBox<Palestrante> cbPalestrante; // Lista de seleção para os palestrantes
    private JTable table;
    private DefaultTableModel tableModel;

    public EventoGui() {
        // Instancia os serviços
        this.eventoService = new EventoService();
        this.palestranteService = new PalestranteService();

        setTitle("Cadastro de Eventos");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Apenas fecha esta janela

        // Define o layout principal da janela
        setLayout(new BorderLayout(10, 10));

        // Adiciona os painéis de formulário e tabela na janela
        add(criarPainelFormulario(), BorderLayout.NORTH);
        add(criarPainelTabela(), BorderLayout.CENTER);

        // Carrega dados iniciais
        carregarPalestrantes();
        atualizarTabela();
    }

    /**
     * Cria e retorna o painel com os campos do formulário.
     */
    private JPanel criarPainelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Dados do Evento"));
        GuiUtils c = new GuiUtils(); // Utilitário para facilitar o layout

        // Campo ID (não editável)
        panel.add(new JLabel("ID:"), c.montarConstraints(0, 0));
        tfId = new JTextField(5);
        tfId.setEditable(false);
        panel.add(tfId, c.montarConstraints(1, 0));

        // Campo Nome do Evento
        panel.add(new JLabel("Nome do Evento:"), c.montarConstraints(0, 1));
        tfNome = new JTextField(30);
        panel.add(tfNome, c.montarConstraints(1, 1));

        // Campo Data
        panel.add(new JLabel("Data (dd/mm/aaaa):"), c.montarConstraints(0, 2));
        tfData = new JTextField(15);
        panel.add(tfData, c.montarConstraints(1, 2));

        // Lista de seleção de Palestrantes
        panel.add(new JLabel("Palestrante:"), c.montarConstraints(0, 3));
        cbPalestrante = new JComboBox<>();
        panel.add(cbPalestrante, c.montarConstraints(1, 3));

        // Painel para os botões de ação
        JPanel painelBotoes = new JPanel();
        JButton btnNovo = new JButton("Novo");
        JButton btnSalvar = new JButton("Salvar");
        JButton btnRemover = new JButton("Remover");

        painelBotoes.add(btnNovo);
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnRemover);
        panel.add(painelBotoes, c.montarConstraints(1, 4));

        // Define as ações (eventos) para cada botão
        btnNovo.addActionListener(e -> limparCampos());
        btnSalvar.addActionListener(e -> salvar());
        btnRemover.addActionListener(e -> remover());

        return panel;
    }

    /**
     * Cria e retorna o painel que contém a tabela de eventos.
     */
    private JScrollPane criarPainelTabela(){
        // Define as colunas da tabela
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome do Evento", "Data", "Palestrante"}, 0);
        table = new JTable(tableModel);
        // Adiciona um "ouvinte" de seleção na tabela
        table.getSelectionModel().addListSelectionListener(e -> {
            // Este evento é disparado quando o usuário clica em uma linha da tabela
            // if evita disparos múltiplos
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                preencherCamposComLinhaSelecionada();
            }
        });
        return new JScrollPane(table); // Adiciona a tabela a um painel com barra de rolagem
    }

    /**
     * Pega os dados da linha selecionada na tabela e preenche os campos do formulário.
     */
    private void preencherCamposComLinhaSelecionada() {
        int selectedRow = table.getSelectedRow();
        Long id = (Long) tableModel.getValueAt(selectedRow, 0);

        // Busca o objeto Evento completo na lista de eventos (evita nova consulta ao banco)
        Evento evento = eventoService.listarTodos().stream()
                .filter(e -> e.getId().equals(id)).findFirst().orElse(null);

        // Se encontrou o evento, preenche os campos
        if (evento != null) {
            tfId.setText(evento.getId().toString());
            tfNome.setText(evento.getNomeEvento());
            tfData.setText(evento.getDataEvento());
            // Seleciona o palestrante correto na lista de seleção
            cbPalestrante.setSelectedItem(evento.getPalestrante());
        }
    }

    /**
     * Pega os dados do formulário e envia para a camada de serviço salvar.
     */
    private void salvar() {
        // Cria um objeto Evento a partir dos dados nos campos da tela
        Evento evento = new Evento();
        evento.setId(tfId.getText().isEmpty() ? null : Long.valueOf(tfId.getText()));
        evento.setNomeEvento(tfNome.getText());
        evento.setDataEvento(tfData.getText());
        evento.setPalestrante((Palestrante) cbPalestrante.getSelectedItem());

        // Verificação: Garante que um palestrante foi selecionado
        if (evento.getPalestrante() == null) {
            JOptionPane.showMessageDialog(this, "Selecione um palestrante.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return; // Interrompe a execução do método
        }

        // Chama o serviço para salvar (o serviço decide se é insert ou update)
        if (eventoService.salvar(evento)) {
            JOptionPane.showMessageDialog(this, "Evento salvo com sucesso!");
            limparCampos(); // Limpa o formulário
            atualizarTabela(); // Recarrega a tabela com os novos dados
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar evento.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Remove o evento selecionado.
     */
    private void remover() {
        // Verificação: Garante que um evento foi selecionado na tabela
        if (tfId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um evento para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Pede confirmação ao usuário
        int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente remover?", "Confirmar remoção", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            eventoService.remover(Long.valueOf(tfId.getText()));
            limparCampos();
            atualizarTabela();
        }
    }

    /**
     * Busca os palestrantes no banco e preenche a lista de seleção (JComboBox).
     */
    private void carregarPalestrantes() {
        cbPalestrante.removeAllItems(); // Limpa os itens antigos
        List<Palestrante> palestrantes = palestranteService.listarTodos();
        for (Palestrante p : palestrantes) {
            cbPalestrante.addItem(p);
        }
    }

    /**
     * Limpa todos os campos do formulário.
     */
    private void limparCampos() {
        tfId.setText("");
        tfNome.setText("");
        tfData.setText("");
        cbPalestrante.setSelectedIndex(-1); // Desseleciona qualquer item na lista
        table.clearSelection(); // Remove a seleção da tabela
    }

    /**
     * Limpa a tabela e a preenche novamente com os dados do banco.
     */
    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas da tabela
        List<Evento> eventos = eventoService.listarTodos();
        for (Evento e : eventos) {
            // Adiciona uma nova linha para cada evento encontrado
            tableModel.addRow(new Object[]{
                    e.getId(),
                    e.getNomeEvento(),
                    e.getDataEvento(),
                    // Verificação para evitar erro se um evento não tiver palestrante
                    e.getPalestrante() != null ? e.getPalestrante().getNome() : "N/D"
            });
        }
    }
}