package felipe.nascimento.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PrincipalGui extends JFrame {

    public PrincipalGui() {
        setTitle("HACKATHON - Gestão de Eventos");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setJMenuBar(montarMenuBar());

        JPanel welcomePanel = new JPanel(new GridBagLayout());
        // ... (código do painel de boas vindas que já existe) ...
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel lblTitle = new JLabel("Bem-vindo ao Sistema de Gestão de Eventos", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
        welcomePanel.add(lblTitle, gbc);
        JLabel lblSubtitle = new JLabel("Use o menu para gerenciar eventos, palestrantes e inscrições.", SwingConstants.CENTER);
        lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.insets = new Insets(10, 0, 0, 0);
        welcomePanel.add(lblSubtitle, gbc);
        getContentPane().add(welcomePanel, BorderLayout.CENTER);
    }

    private JMenuBar montarMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(montarMenuGerenciamento());
        menuBar.add(montarMenuRelatorios());
        menuBar.add(montarMenuAjuda());
        return menuBar;
    }

    private JMenu montarMenuGerenciamento() {
        JMenu menu = new JMenu("Gerenciamento");
        JMenuItem miEvento = new JMenuItem("Eventos");
        JMenuItem miPalestrante = new JMenuItem("Palestrantes");
        JMenuItem miInscrever = new JMenuItem("Inscrever Aluno em Evento");

        menu.add(miEvento);
        menu.add(miPalestrante);
        menu.addSeparator();
        menu.add(miInscrever);

        miEvento.addActionListener(e -> new EventoGui().setVisible(true));
        miPalestrante.addActionListener(e -> new PalestranteGui().setVisible(true));
        miInscrever.addActionListener(e -> new InscricaoGui().setVisible(true));

        return menu;
    }

    private JMenu montarMenuRelatorios() {
        JMenu menu = new JMenu("Relatórios");
        JMenuItem miRelAlunosPorEvento = new JMenuItem("Listar Alunos por Evento");
        menu.add(miRelAlunosPorEvento);

        miRelAlunosPorEvento.addActionListener(e -> new RelatorioAlunosPorEventoGui().setVisible(true));

        return menu;
    }

    private JMenu montarMenuAjuda() {
        JMenu menu = new JMenu("Ajuda");
        JMenuItem miSobre = new JMenuItem("Sobre o Projeto");
        JMenuItem miEquipe = new JMenuItem("Equipe");
        JMenuItem miSair = new JMenuItem("Sair");

        miSobre.addActionListener(e -> new SobreGui().setVisible(true));
        miEquipe.addActionListener(e -> new EquipeGui().setVisible(true));
        miSair.addActionListener(this::exit);

        menu.add(miSobre);
        menu.add(miEquipe);
        menu.addSeparator();
        menu.add(miSair);

        return menu;
    }

    private void exit(ActionEvent actionEvent) {
        int result = JOptionPane.showConfirmDialog(
                this, "Deseja realmente sair?",
                "Finalizar Aplicação", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}