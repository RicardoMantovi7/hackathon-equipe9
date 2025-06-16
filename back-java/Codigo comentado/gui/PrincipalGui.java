package felipe.nascimento.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Esta é a janela principal da aplicação, a primeira tela que o usuário vê.
 * Ela contém o menu para navegar entre as outras telas.
 */
public class PrincipalGui extends JFrame {

    public PrincipalGui() {
        // --- Configurações básicas da janela ---
        setTitle("HACKATHON - Gestão de Eventos");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Garante que a aplicação feche ao clicar no 'X'
        setExtendedState(MAXIMIZED_BOTH); // Inicia a janela maximizada
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setJMenuBar(montarMenuBar()); // Monta e adiciona a barra de menus na janela

        // --- Painel de Boas-Vindas (para a tela não ficar vazia) ---
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margem
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título de boas-vindas
        JLabel lblTitle = new JLabel("Bem-vindo ao Sistema de Gestão de Eventos", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
        welcomePanel.add(lblTitle, gbc);

        // Subtítulo com instrução
        JLabel lblSubtitle = new JLabel("Use o menu para gerenciar eventos, palestrantes e inscrições.", SwingConstants.CENTER);
        lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.insets = new Insets(10, 0, 0, 0); // Espaçamento
        welcomePanel.add(lblSubtitle, gbc);

        getContentPane().add(welcomePanel, BorderLayout.CENTER);
    }

    /**
     * Cria e configura a barra de menus principal.
     */
    private JMenuBar montarMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(montarMenuGerenciamento());
        menuBar.add(montarMenuRelatorios());
        menuBar.add(montarMenuAjuda());
        return menuBar;
    }

    /**
     * Cria o menu "Gerenciamento" com seus itens.
     */
    private JMenu montarMenuGerenciamento() {
        JMenu menu = new JMenu("Gerenciamento");
        JMenuItem miEvento = new JMenuItem("Eventos");
        JMenuItem miPalestrante = new JMenuItem("Palestrantes");
        JMenuItem miInscrever = new JMenuItem("Inscrever Aluno em Evento");

        menu.add(miEvento);
        menu.add(miPalestrante);
        menu.addSeparator();
        menu.add(miInscrever);

        // Define as ações para cada item do menu.
        // Ao clicar, uma nova instância da tela correspondente é criada e exibida.
        miEvento.addActionListener(e -> new EventoGui().setVisible(true));
        miPalestrante.addActionListener(e -> new PalestranteGui().setVisible(true));
        miInscrever.addActionListener(e -> new InscricaoGui().setVisible(true));

        return menu;
    }

    /**
     * Cria o menu "Relatórios" com seus itens.
     */
    private JMenu montarMenuRelatorios() {
        JMenu menu = new JMenu("Relatórios");
        JMenuItem miRelAlunosPorEvento = new JMenuItem("Listar Alunos por Evento");
        menu.add(miRelAlunosPorEvento);

        miRelAlunosPorEvento.addActionListener(e -> new RelatorioAlunosPorEventoGui().setVisible(true));

        return menu;
    }

    /**
     * Cria o menu "Ajuda" com seus itens.
     */
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

    /**
     * Método chamado ao clicar em "Sair". Pede confirmação antes de fechar o programa.
     */
    private void exit(ActionEvent actionEvent) {
        int result = JOptionPane.showConfirmDialog(
                this, "Deseja realmente sair?",
                "Finalizar Aplicação", JOptionPane.YES_NO_OPTION);

        // Verifica se o usuário clicou em "Sim" (YES_OPTION)
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}