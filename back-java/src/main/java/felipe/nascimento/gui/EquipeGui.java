package felipe.nascimento.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Janela para exibir os integrantes da equipe usando texto simples.
 */
public class EquipeGui extends JFrame {

    public EquipeGui() {
        setTitle("Equipe de Desenvolvimento");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setOpaque(false);
        textArea.setBorder(null);

        String textoEquipe =
                "Desenvolvedores\n" +
                        "-----------------------------------\n" +
                        "   • Felipe do Nascimento\n" +
                        "Disciplina: Programação Orientada a Objetos\n";

        textArea.setText(textoEquipe);
        mainPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());


        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(btnFechar);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }
}