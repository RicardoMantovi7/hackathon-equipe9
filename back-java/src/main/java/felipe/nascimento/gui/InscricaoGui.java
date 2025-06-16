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
 * Interface para inscrever Alunos (lidos do banco) em Eventos.
 */
public class InscricaoGui extends JFrame {
    private final AlunoService alunoService = new AlunoService();
    private final EventoService eventoService = new EventoService();
    private final InscricaoService inscricaoService = new InscricaoService();
    private JComboBox<Aluno> cbAlunos;
    private JComboBox<Evento> cbEventos;

    public InscricaoGui() {
        setTitle("Inscrever Aluno em Evento");
        setSize(500, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GuiUtils c = new GuiUtils();

        mainPanel.add(new JLabel("Selecione o Aluno:"), c.montarConstraints(0, 0));
        cbAlunos = new JComboBox<>();
        mainPanel.add(cbAlunos, c.montarConstraints(1, 0));

        mainPanel.add(new JLabel("Selecione o Evento:"), c.montarConstraints(0, 1));
        cbEventos = new JComboBox<>();
        mainPanel.add(cbEventos, c.montarConstraints(1, 1));

        JButton btnInscrever = new JButton("Realizar Inscrição");
        GridBagConstraints gbcButton = c.montarConstraints(1, 2);
        gbcButton.anchor = GridBagConstraints.EAST;
        mainPanel.add(btnInscrever, gbcButton);

        btnInscrever.addActionListener(e -> realizarInscricao());
        add(mainPanel);

        carregarAlunos();
        carregarEventos();
    }

    private void carregarAlunos() {
        List<Aluno> alunos = alunoService.listarTodos();
        cbAlunos.setModel(new DefaultComboBoxModel<>(alunos.toArray(new Aluno[0])));
    }

    private void carregarEventos() {
        List<Evento> eventos = eventoService.listarTodos();
        cbEventos.setModel(new DefaultComboBoxModel<>(eventos.toArray(new Evento[0])));
    }

    private void realizarInscricao() {
        Aluno aluno = (Aluno) cbAlunos.getSelectedItem();
        Evento evento = (Evento) cbEventos.getSelectedItem();

        if (aluno == null || evento == null) {
            JOptionPane.showMessageDialog(this, "Selecione um aluno e um evento.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (inscricaoService.inscrever(aluno.getId(), evento.getId())) {
            JOptionPane.showMessageDialog(this, "Inscrição de '" + aluno.getNome() + "' no evento '" + evento.getNomeEvento() + "' realizada com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Erro: Este aluno já está inscrito no evento.", "Erro de Inscrição", JOptionPane.ERROR_MESSAGE);
        }
    }
}