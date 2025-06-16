package felipe.nascimento.gui;

import felipe.nascimento.model.Aluno;
import felipe.nascimento.model.Evento;
import felipe.nascimento.service.AlunoService;
import felipe.nascimento.service.EventoService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Interface para exibir um relatório de Alunos inscritos por Evento.
 */
public class RelatorioAlunosPorEventoGui extends JFrame {
    private final EventoService eventoService = new EventoService();
    private final AlunoService alunoService = new AlunoService();
    private JComboBox<Evento> cbEventos;
    private JTable table;
    private DefaultTableModel tableModel;

    public RelatorioAlunosPorEventoGui() {
        setTitle("Relatório - Alunos por Evento");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        add(criarPainelFiltro(), BorderLayout.NORTH);
        add(criarPainelTabela(), BorderLayout.CENTER);
        carregarEventos();
    }

    private JPanel criarPainelFiltro() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("Filtro"));
        panel.add(new JLabel("Selecione o Evento:"));
        cbEventos = new JComboBox<>();
        cbEventos.setPreferredSize(new Dimension(300, 25));
        panel.add(cbEventos);
        JButton btnBuscar = new JButton("Buscar Alunos");
        panel.add(btnBuscar);
        btnBuscar.addActionListener(e -> buscarAlunosInscritos());
        return panel;
    }

    private JScrollPane criarPainelTabela() {
        tableModel = new DefaultTableModel(new Object[]{"ID Aluno", "Nome", "CPF"}, 0);
        table = new JTable(tableModel);
        return new JScrollPane(table);
    }

    private void carregarEventos() {
        List<Evento> eventos = eventoService.listarTodos();
        cbEventos.setModel(new DefaultComboBoxModel<>(eventos.toArray(new Evento[0])));
    }

    private void buscarAlunosInscritos() {
        Evento evento = (Evento) cbEventos.getSelectedItem();
        if (evento == null) {
            JOptionPane.showMessageDialog(this, "Selecione um evento.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        List<Aluno> alunosInscritos = alunoService.listarPorEvento(evento.getId());
        tableModel.setRowCount(0); // Limpa a tabela
        for (Aluno aluno : alunosInscritos) {
            tableModel.addRow(new Object[]{aluno.getId(), aluno.getNome(), aluno.getCpf()});
        }
        if (alunosInscritos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum aluno inscrito neste evento.", "Informação", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}