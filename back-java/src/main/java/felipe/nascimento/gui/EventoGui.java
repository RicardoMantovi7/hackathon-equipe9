package felipe.nascimento.gui;

import felipe.nascimento.model.Evento;
import felipe.nascimento.model.Palestrante;
import felipe.nascimento.service.EventoService;
import felipe.nascimento.service.PalestranteService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EventoGui extends JFrame {
    private final EventoService eventoService;
    private final PalestranteService palestranteService;
    private JTextField tfId, tfNome, tfData;
    private JComboBox<Palestrante> cbPalestrante;
    private JTable table;
    private DefaultTableModel tableModel;

    public EventoGui() {
        this.eventoService = new EventoService();
        this.palestranteService = new PalestranteService();

        setTitle("Cadastro de Eventos");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Apenas fecha esta janela

        setLayout(new BorderLayout(10, 10));

        add(criarPainelFormulario(), BorderLayout.NORTH);
        add(criarPainelTabela(), BorderLayout.CENTER);

        carregarPalestrantes();
        atualizarTabela();
    }

    private JPanel criarPainelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Dados do Evento"));
        GuiUtils c = new GuiUtils();

        panel.add(new JLabel("ID:"), c.montarConstraints(0, 0));
        tfId = new JTextField(5);
        tfId.setEditable(false);
        panel.add(tfId, c.montarConstraints(1, 0));

        panel.add(new JLabel("Nome do Evento:"), c.montarConstraints(0, 1));
        tfNome = new JTextField(30);
        panel.add(tfNome, c.montarConstraints(1, 1));

        panel.add(new JLabel("Data (dd/mm/aaaa):"), c.montarConstraints(0, 2));
        tfData = new JTextField(15);
        panel.add(tfData, c.montarConstraints(1, 2));

        panel.add(new JLabel("Palestrante:"), c.montarConstraints(0, 3));
        cbPalestrante = new JComboBox<>();
        panel.add(cbPalestrante, c.montarConstraints(1, 3));

        // Botões
        JPanel painelBotoes = new JPanel();
        JButton btnNovo = new JButton("Novo");
        JButton btnSalvar = new JButton("Salvar");
        JButton btnRemover = new JButton("Remover");

        painelBotoes.add(btnNovo);
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnRemover);
        panel.add(painelBotoes, c.montarConstraints(1, 4));

        btnNovo.addActionListener(e -> limparCampos());
        btnSalvar.addActionListener(e -> salvar());
        btnRemover.addActionListener(e -> remover());

        return panel;
    }

    private JScrollPane criarPainelTabela(){
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome do Evento", "Data", "Palestrante"}, 0);
        table = new JTable(tableModel);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                preencherCamposComLinhaSelecionada();
            }
        });
        return new JScrollPane(table);
    }

    private void preencherCamposComLinhaSelecionada() {
        int selectedRow = table.getSelectedRow();
        Long id = (Long) tableModel.getValueAt(selectedRow, 0);

        Evento evento = eventoService.listarTodos().stream()
                .filter(e -> e.getId().equals(id)).findFirst().orElse(null);

        if (evento != null) {
            tfId.setText(evento.getId().toString());
            tfNome.setText(evento.getNomeEvento());
            tfData.setText(evento.getDataEvento());
            cbPalestrante.setSelectedItem(evento.getPalestrante());
        }
    }

    private void salvar() {
        Evento evento = new Evento();
        evento.setId(tfId.getText().isEmpty() ? null : Long.valueOf(tfId.getText()));
        evento.setNomeEvento(tfNome.getText());
        evento.setDataEvento(tfData.getText());
        evento.setPalestrante((Palestrante) cbPalestrante.getSelectedItem());

        if (evento.getPalestrante() == null) {
            JOptionPane.showMessageDialog(this, "Selecione um palestrante.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (eventoService.salvar(evento)) {
            JOptionPane.showMessageDialog(this, "Evento salvo com sucesso!");
            limparCampos();
            atualizarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar evento.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void remover() {
        if (tfId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um evento para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente remover?", "Confirmar remoção", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            eventoService.remover(Long.valueOf(tfId.getText()));
            limparCampos();
            atualizarTabela();
        }
    }

    private void carregarPalestrantes() {
        cbPalestrante.removeAllItems();
        List<Palestrante> palestrantes = palestranteService.listarTodos();
        for (Palestrante p : palestrantes) {
            cbPalestrante.addItem(p);
        }
    }

    private void limparCampos() {
        tfId.setText("");
        tfNome.setText("");
        tfData.setText("");
        cbPalestrante.setSelectedIndex(-1);
        table.clearSelection();
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        List<Evento> eventos = eventoService.listarTodos();
        for (Evento e : eventos) {
            tableModel.addRow(new Object[]{
                    e.getId(),
                    e.getNomeEvento(),
                    e.getDataEvento(),
                    e.getPalestrante() != null ? e.getPalestrante().getNome() : "N/D"
            });
        }
    }
}