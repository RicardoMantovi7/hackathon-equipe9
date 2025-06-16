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
 * Tela para exibir um relatório de Alunos inscritos em um Evento específico.
 */
public class RelatorioAlunosPorEventoGui extends JFrame {
    private final EventoService eventoService = new EventoService();
    private final AlunoService alunoService = new AlunoService();
    private JComboBox<Evento> cbEventos; // Lista de seleção para filtrar por evento
    private JTable table; // Tabela para mostrar os alunos inscritos
    private DefaultTableModel tableModel;

    public RelatorioAlunosPorEventoGui() {
        setTitle("Relatório - Alunos por Evento");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Adiciona o painel de filtro na parte de cima e a tabela no centro
        add(criarPainelFiltro(), BorderLayout.NORTH);
        add(criarPainelTabela(), BorderLayout.CENTER);

        // Carrega os eventos na lista de seleção ao iniciar a tela
        carregarEventos();
    }

    /**
     * Cria o painel superior que contém a lista de eventos e o botão de busca.
     */
    private JPanel criarPainelFiltro() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("Filtro"));
        panel.add(new JLabel("Selecione o Evento:"));
        cbEventos = new JComboBox<>();
        cbEventos.setPreferredSize(new Dimension(300, 25));
        panel.add(cbEventos);
        JButton btnBuscar = new JButton("Buscar Alunos");
        panel.add(btnBuscar);

        // Define a ação do botão "Buscar"
        btnBuscar.addActionListener(e -> buscarAlunosInscritos());
        return panel;
    }

    /**
     * Cria o painel que contém a tabela de resultados.
     */
    private JScrollPane criarPainelTabela() {
        tableModel = new DefaultTableModel(new Object[]{"ID Aluno", "Nome", "CPF"}, 0);
        table = new JTable(tableModel);
        return new JScrollPane(table); // Coloca a tabela dentro de uma barra de rolagem
    }

    /**
     * Busca todos os eventos e os adiciona na lista de seleção (JComboBox).
     */
    private void carregarEventos() {
        List<Evento> eventos = eventoService.listarTodos();
        cbEventos.setModel(new DefaultComboBoxModel<>(eventos.toArray(new Evento[0])));
    }

    /**
     * Método chamado pelo botão "Buscar". Pega o evento selecionado e lista os alunos.
     */
    private void buscarAlunosInscritos() {
        Evento evento = (Evento) cbEventos.getSelectedItem();
        // Verificação: Garante que um evento foi selecionado
        if (evento == null) {
            JOptionPane.showMessageDialog(this, "Selecione um evento.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Chama o serviço para buscar os alunos inscritos no evento selecionado
        List<Aluno> alunosInscritos = alunoService.listarPorEvento(evento.getId());
        tableModel.setRowCount(0); // Limpa a tabela antes de adicionar os novos resultados

        // Preenche a tabela com os alunos encontrados
        for (Aluno aluno : alunosInscritos) {
            tableModel.addRow(new Object[]{aluno.getId(), aluno.getNome(), aluno.getCpf()});
        }

        // Verificação: Se a lista de alunos estiver vazia, informa o usuário
        if (alunosInscritos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum aluno inscrito neste evento.", "Informação", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}