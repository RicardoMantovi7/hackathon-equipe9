package felipe.nascimento.gui;

import felipe.nascimento.model.Aluno;
import felipe.nascimento.model.Evento;
import felipe.nascimento.service.AlunoService;
import felipe.nascimento.service.EventoService;
import felipe.nascimento.service.InscricaoService;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Tela responsável por inscrever Alunos (lidos do banco) em Eventos.
 */
public class InscricaoGui extends JFrame {
    // Camadas de serviço para acessar a lógica de negócio
    private final AlunoService alunoService = new AlunoService();
    private final EventoService eventoService = new EventoService();
    private final InscricaoService inscricaoService = new InscricaoService();

    // Componentes da interface
    private JComboBox<Aluno> cbAlunos; // Lista de seleção para os alunos
    private JComboBox<Evento> cbEventos; // Lista de seleção para os eventos

    public InscricaoGui() {
        setTitle("Inscrever Aluno em Evento");
        setSize(500, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha apenas esta janela

        // Cria o painel principal e define seu layout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margem
        GuiUtils c = new GuiUtils(); // Utilitário para facilitar o layout

        // Adiciona os componentes da tela
        mainPanel.add(new JLabel("Selecione o Aluno:"), c.montarConstraints(0, 0));
        cbAlunos = new JComboBox<>();
        mainPanel.add(cbAlunos, c.montarConstraints(1, 0));

        mainPanel.add(new JLabel("Selecione o Evento:"), c.montarConstraints(0, 1));
        cbEventos = new JComboBox<>();
        mainPanel.add(cbEventos, c.montarConstraints(1, 1));

        JButton btnInscrever = new JButton("Realizar Inscrição");
        GridBagConstraints gbcButton = c.montarConstraints(1, 2);
        gbcButton.anchor = GridBagConstraints.EAST; // Alinha o botão à direita
        mainPanel.add(btnInscrever, gbcButton);

        // Define a ação do botão "Inscrever"
        btnInscrever.addActionListener(e -> realizarInscricao());
        add(mainPanel); // Adiciona o painel principal à janela

        // Carrega os dados iniciais nas listas de seleção
        carregarAlunos();
        carregarEventos();
    }

    /**
     * Busca todos os alunos no banco e preenche a lista de seleção de alunos.
     */
    private void carregarAlunos() {
        List<Aluno> alunos = alunoService.listarTodos();
        // Converte a lista de alunos para um array e define como o modelo do ComboBox
        cbAlunos.setModel(new DefaultComboBoxModel<>(alunos.toArray(new Aluno[0])));
    }

    /**
     * Busca todos os eventos no banco e preenche a lista de seleção de eventos.
     */
    private void carregarEventos() {
        List<Evento> eventos = eventoService.listarTodos();
        cbEventos.setModel(new DefaultComboBoxModel<>(eventos.toArray(new Evento[0])));
    }

    /**
     * Pega o aluno e o evento selecionados e realiza a inscrição.
     */
    private void realizarInscricao() {
        // Pega o objeto Aluno e Evento que estão selecionados nas listas
        Aluno aluno = (Aluno) cbAlunos.getSelectedItem();
        Evento evento = (Evento) cbEventos.getSelectedItem();

        // Verificação: Garante que o usuário selecionou um item em cada lista
        if (aluno == null || evento == null) {
            JOptionPane.showMessageDialog(this, "Selecione um aluno e um evento.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return; // Interrompe a execução
        }

        // Chama o serviço para tentar realizar a inscrição no banco de dados
        if (inscricaoService.inscrever(aluno.getId(), evento.getId())) {
            JOptionPane.showMessageDialog(this, "Inscrição de '" + aluno.getNome() + "' no evento '" + evento.getNomeEvento() + "' realizada com sucesso!");
        } else {
            // Isso geralmente acontece se a inscrição já existe (erro de chave primária no banco)
            JOptionPane.showMessageDialog(this, "Erro: Este aluno já está inscrito no evento.", "Erro de Inscrição", JOptionPane.ERROR_MESSAGE);
        }
    }
}