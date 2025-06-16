package felipe.nascimento.gui;

import felipe.nascimento.model.Palestrante;
import felipe.nascimento.service.PalestranteService;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class PalestranteGui extends JFrame {
    private final PalestranteService service;
    private JTextField tfId, tfNome, tfTemas;
    private JTextArea taMinicurriculo;
    private JTable table;
    private DefaultTableModel tableModel;

    // --- Componentes para a imagem ---
    private JLabel lblFotoPreview;
    private File arquivoImagemSelecionado;
    private static final String PASTA_IMAGENS = "C:\\Faculdade estudar\\Trabalhos 3 p\\Hackathon\\vai_funcionar\\imagens\\";


    public PalestranteGui() {
        this.service = new PalestranteService();
        setTitle("Cadastro de Palestrantes");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));
        add(criarPainelPrincipal(), BorderLayout.CENTER);
        add(criarPainelTabela(), BorderLayout.SOUTH);

        criarPastaDeImagensSeNaoExistir();

        atualizarTabela();
    }

    private JPanel criarPainelPrincipal() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.add(criarPainelFormulario(), BorderLayout.CENTER);
        painelPrincipal.add(criarPainelFoto(), BorderLayout.EAST);
        return painelPrincipal;
    }

    private void criarPastaDeImagensSeNaoExistir() {
        Path pasta = Paths.get(PASTA_IMAGENS);
        if (!Files.exists(pasta)) {
            try {
                Files.createDirectories(pasta);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao criar pasta de imagens:\n" + e.getMessage(), "Erro Crítico", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JPanel criarPainelFoto() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Foto"));

        lblFotoPreview = new JLabel("Sem imagem");
        lblFotoPreview.setHorizontalAlignment(SwingConstants.CENTER);
        lblFotoPreview.setVerticalAlignment(SwingConstants.CENTER);
        lblFotoPreview.setPreferredSize(new Dimension(200, 200));
        lblFotoPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.add(lblFotoPreview, BorderLayout.CENTER);

        JButton btnSelecionarFoto = new JButton("Selecionar Imagem...");
        btnSelecionarFoto.addActionListener(e -> selecionarImagem());
        panel.add(btnSelecionarFoto, BorderLayout.SOUTH);

        return panel;
    }

    private void selecionarImagem() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Escolha uma imagem");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Imagens (jpg, png, gif)", "jpg", "png", "gif"));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            arquivoImagemSelecionado = fileChooser.getSelectedFile();
            exibirPreviewImagem(arquivoImagemSelecionado.getAbsolutePath());
        }
    }

    private void exibirPreviewImagem(String imagePath) {
        if (imagePath == null || imagePath.isEmpty() || !new File(imagePath).exists()) {
            lblFotoPreview.setIcon(null);
            lblFotoPreview.setText("Sem imagem");
            return;
        }

        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image resizedImage = originalIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        lblFotoPreview.setIcon(new ImageIcon(resizedImage));
        lblFotoPreview.setText(null);
    }

    private JPanel criarPainelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Dados do Palestrante"));
        GuiUtils c = new GuiUtils();

        panel.add(new JLabel("ID:"), c.montarConstraints(0, 0));
        tfId = new JTextField(5);
        tfId.setEditable(false);
        panel.add(tfId, c.montarConstraints(1, 0));

        panel.add(new JLabel("Nome:"), c.montarConstraints(0, 1));
        tfNome = new JTextField(30);
        panel.add(tfNome, c.montarConstraints(1, 1));

        panel.add(new JLabel("Temas:"), c.montarConstraints(0, 2));
        tfTemas = new JTextField(30);
        panel.add(tfTemas, c.montarConstraints(1, 2));

        panel.add(new JLabel("Minicurrículo:"), c.montarConstraints(0, 3));
        taMinicurriculo = new JTextArea(5, 30);
        panel.add(new JScrollPane(taMinicurriculo), c.montarConstraints(1, 3));

        JPanel painelBotoes = new JPanel();
        JButton btnNovo = new JButton("Novo");
        JButton btnSalvar = new JButton("Salvar");
        JButton btnRemover = new JButton("Remover");

        painelBotoes.add(btnNovo);
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnRemover);
        GridBagConstraints gbcBotoes = c.montarConstraints(1, 4);
        gbcBotoes.anchor = GridBagConstraints.WEST;
        panel.add(painelBotoes, gbcBotoes);

        btnNovo.addActionListener(e -> limparCampos());
        btnSalvar.addActionListener(e -> salvar());
        btnRemover.addActionListener(e -> remover());

        return panel;
    }

    private JScrollPane criarPainelTabela() {
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Temas Abordados"}, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                preencherCamposComLinhaSelecionada();
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 250));
        return scrollPane;
    }

    private void preencherCamposComLinhaSelecionada() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) return;

        Long id = (Long) tableModel.getValueAt(selectedRow, 0);

        Palestrante palestrante = service.select(id);

        if (palestrante != null) {
            tfId.setText(palestrante.getId().toString());
            tfNome.setText(palestrante.getNome());
            tfTemas.setText(palestrante.getTemasAbordados());
            taMinicurriculo.setText(palestrante.getMinicurriculo());

            arquivoImagemSelecionado = null;
            exibirPreviewImagem(palestrante.getFotoPath());
        }
    }

    private String copiarImagemParaPasta(String idAtual) throws IOException {
        if (arquivoImagemSelecionado == null) {
            if (idAtual != null && !idAtual.isEmpty()) {
                Palestrante pExistente = service.select(Long.valueOf(idAtual));
                return pExistente != null ? pExistente.getFotoPath() : null;
            }
            return null;
        }

        String nomeArquivo = System.currentTimeMillis() + "_" + arquivoImagemSelecionado.getName();
        Path origem = arquivoImagemSelecionado.toPath();
        Path destino = Paths.get(PASTA_IMAGENS + nomeArquivo);

        Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);
        return destino.toString();
    }

    private void salvar() {
        try {
            String caminhoDaImagemSalva = copiarImagemParaPasta(tfId.getText());

            Palestrante p = new Palestrante();
            p.setId(tfId.getText().isEmpty() ? null : Long.valueOf(tfId.getText()));
            p.setNome(tfNome.getText());
            p.setTemasAbordados(tfTemas.getText());
            p.setMinicurriculo(taMinicurriculo.getText());
            p.setFotoPath(caminhoDaImagemSalva);

            if (service.salvar(p)) {
                JOptionPane.showMessageDialog(this, "Palestrante salvo com sucesso!");
                limparCampos();
                atualizarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar palestrante.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar imagem: " + e.getMessage(), "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
        } catch(Exception e){
            JOptionPane.showMessageDialog(this, "Erro inesperado: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void remover() {
        if (tfId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um palestrante para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente remover?", "Confirmar remoção", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Long id = Long.valueOf(tfId.getText());
            Palestrante palestrante = service.select(id);
            if (palestrante != null && palestrante.getFotoPath() != null) {
                try {
                    Files.deleteIfExists(Paths.get(palestrante.getFotoPath()));
                } catch (IOException e) {
                    System.err.println("Erro ao deletar arquivo de imagem: " + e.getMessage());
                }
            }
            service.remover(id);
            limparCampos();
            atualizarTabela();
        }
    }

    private void limparCampos() {
        tfId.setText("");
        tfNome.setText("");
        tfTemas.setText("");
        taMinicurriculo.setText("");
        lblFotoPreview.setIcon(null);
        lblFotoPreview.setText("Sem imagem");
        arquivoImagemSelecionado = null;
        if (table.getSelectionModel() != null) {
            table.clearSelection();
        }
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        List<Palestrante> palestrantes = service.listarTodos();
        for (Palestrante p : palestrantes) {
            tableModel.addRow(new Object[]{p.getId(), p.getNome(), p.getTemasAbordados()});
        }
    }
}