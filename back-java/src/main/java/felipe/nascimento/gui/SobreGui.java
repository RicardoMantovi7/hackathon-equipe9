package felipe.nascimento.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Janela para exibir informações sobre o projeto usando texto simples.
 */
public class SobreGui extends JFrame {

    public SobreGui() {
        setTitle("Sobre o Projeto");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Painel principal com uma borda para espaçamento
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Use JTextArea para texto simples
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false); // O usuário não pode editar
        textArea.setFont(new Font("Arial", Font.PLAIN, 14)); // Define uma fonte legível
        textArea.setWrapStyleWord(true); // Quebra a linha por palavras
        textArea.setLineWrap(true); // Habilita a quebra de linha
        textArea.setOpaque(false); // Deixa o fundo transparente para pegar a cor da janela
        textArea.setBorder(null); // Sem bordas

        String textoSobre =
                "Hotsite de Eventos UniALFA\n\n" +
                        "--------------------------------------------------\n\n" +
                        "Objetivo:\n" +
                        "O objetivo deste projeto é criar uma plataforma para a gestão e " +
                        "divulgação de eventos acadêmicos da UniALFA, como palestras e cursos.\n\n" +
                        "Tecnologias Utilizadas:\n" +
                        "   • Java: Para a interface de gerenciamento da coordenação (back-office).\n" +
                        "   • MySQL: Como banco de dados para armazenar os dados.\n" +
                        "   • Node.js (API): Fornece os dados para o site(PHP).\n" +
                        "   • PHP: Interface pública para os alunos, mostrando os eventos e horários.\n\n";
        textArea.setText(textoSobre);
        mainPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Botão para fechar
        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(btnFechar);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }
}