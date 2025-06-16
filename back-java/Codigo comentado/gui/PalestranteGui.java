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

/**
 * Tela para o CRUD (Criar, Ler, Atualizar, Deletar) de Palestrantes.
 */
public class PalestranteGui extends JFrame {
    // Camada de serviço, que faz a ponte com o DAO
    private final PalestranteService service;

    // --- Componentes da Interface Gráfica ---
    private JTextField tfId, tfNome, tfTemas;
    private JTextArea taMinicurriculo;
    private JTable table; // Tabela para listar os palestrantes
    private DefaultTableModel tableModel; // Modelo de dados da tabela

    // --- Componentes para a imagem ---
    private JLabel lblFotoPreview; // Label para mostrar a miniatura da imagem
    private File arquivoImagemSelecionado; // Guarda a referência do arquivo que o usuário escolheu
    // Caminho da pasta onde as imagens serão salvas
    private static final String PASTA_IMAGENS = "C:\\Faculdade estudar\\Trabalhos 3 p\\Hackathon\\vai_funcionar\\imagens\\";

    public PalestranteGui() {
        this.service = new PalestranteService();
        setTitle("Cadastro de Palestrantes");
        setSize(900, 700);
        setLocationRelativeTo(null);
        // Fecha apenas esta janela, não o programa inteiro
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));
        add(criarPainelPrincipal(), BorderLayout.CENTER);
        add(criarPainelTabela(), BorderLayout.SOUTH);

        // Garante que a pasta de imagens exista
        criarPastaDeImagensSeNaoExistir();
        // Carrega os dados na tabela ao iniciar a tela
        atualizarTabela();
    }

    /**
     * Monta o painel principal, que combina o formulário e a área da foto.
     */
    private JPanel criarPainelPrincipal() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.add(criarPainelFormulario(), BorderLayout.CENTER);
        painelPrincipal.add(criarPainelFoto(), BorderLayout.EAST);
        return painelPrincipal;
    }

    /**
     * Verifica se a pasta de imagens existe, e a cria se não existir.
     */
    private void criarPastaDeImagensSeNaoExistir() {
        Path pasta = Paths.get(PASTA_IMAGENS);
        // Verifica se a pasta NÃO existe
        if (!Files.exists(pasta)) {
            try {
                Files.createDirectories(pasta); // Cria a pasta
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao criar pasta de imagens:\n" + e.getMessage(), "Erro Crítico", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Cria o painel direito, responsável por mostrar a preview da foto e o botão de seleção.
     */
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

    /**
     * Abre uma janela para o usuário selecionar um arquivo de imagem do computador.
     */
    private void selecionarImagem() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Escolha uma imagem");
        // Filtra para mostrar apenas arquivos de imagem comuns
        fileChooser.setFileFilter(new FileNameExtensionFilter("Imagens (jpg, png, gif)", "jpg", "png", "gif"));

        // Abre a janela de seleção e verifica se o usuário confirmou
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            // Guarda o arquivo selecionado na variável
            arquivoImagemSelecionado = fileChooser.getSelectedFile();
            // Mostra a imagem selecionada na tela
            exibirPreviewImagem(arquivoImagemSelecionado.getAbsolutePath());
        }
    }

    /**
     * Exibe a imagem escolhida pelo usuário em um JLabel.
     * @param imagePath O caminho do arquivo de imagem.
     */
    private void exibirPreviewImagem(String imagePath) {
        // Verifica se o caminho da imagem é nulo ou se o arquivo não existe
        if (imagePath == null || imagePath.isEmpty() || !new File(imagePath).exists()) {
            lblFotoPreview.setIcon(null); // Remove qualquer imagem anterior
            lblFotoPreview.setText("Sem imagem"); // Mostra o texto padrão
            return;
        }

        // Carrega a imagem e a redimensiona para caber no JLabel de preview
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image resizedImage = originalIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        lblFotoPreview.setIcon(new ImageIcon(resizedImage));
        lblFotoPreview.setText(null); // Remove o texto "Sem imagem"
    }

    /**
     * Cria e retorna o painel com os campos do formulário.
     */
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

    /**
     * Cria e retorna o painel que contém a tabela de palestrantes.
     */
    private JScrollPane criarPainelTabela() {
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Temas Abordados"}, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Trava a edição direto na tabela
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

    /**
     * Pega os dados da linha selecionada na tabela e preenche os campos do formulário.
     */
    private void preencherCamposComLinhaSelecionada() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) return; // Se nenhuma linha estiver selecionada, não faz nada

        Long id = (Long) tableModel.getValueAt(selectedRow, 0);

        // Busca o objeto Palestrante completo
        Palestrante palestrante = service.select(id);

        if (palestrante != null) {
            tfId.setText(palestrante.getId().toString());
            tfNome.setText(palestrante.getNome());
            tfTemas.setText(palestrante.getTemasAbordados());
            taMinicurriculo.setText(palestrante.getMinicurriculo());

            // Limpa a seleção de imagem anterior e exibe a foto salva no banco
            arquivoImagemSelecionado = null;
            exibirPreviewImagem(palestrante.getFotoPath());
        }
    }

    /**
     * Copia o arquivo de imagem selecionado para a pasta de imagens do projeto.
     * @param idAtual O ID do palestrante que está sendo editado.
     * @return O caminho completo do novo arquivo de imagem salvo.
     */
    private String copiarImagemParaPasta(String idAtual) throws IOException {
        // Verificação: Se o usuário não selecionou uma nova imagem...
        if (arquivoImagemSelecionado == null) {
            // ... e se está editando um palestrante (o campo ID não está vazio)...
            if (idAtual != null && !idAtual.isEmpty()) {
                // ... busca o palestrante no banco para não perder a foto antiga.
                Palestrante pExistente = service.select(Long.valueOf(idAtual));
                return pExistente != null ? pExistente.getFotoPath() : null;
            }
            return null; // Se for um novo palestrante sem foto, retorna nulo.
        }

        // Cria um nome de arquivo único usando o tempo em milissegundos para evitar nomes repetidos
        String nomeArquivo = System.currentTimeMillis() + "_" + arquivoImagemSelecionado.getName();
        Path origem = arquivoImagemSelecionado.toPath();
        Path destino = Paths.get(PASTA_IMAGENS + nomeArquivo);

        // Copia o arquivo da origem (onde o usuário escolheu) para o destino (pasta do projeto)
        Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);

        return destino.toString(); // Retorna o caminho completo da imagem salva
    }

    /**
     * Pega os dados do formulário, copia a imagem e envia para a camada de serviço salvar.
     */
    private void salvar() {
        try {
            // 1. Tenta copiar a imagem para a pasta de destino
            String caminhoDaImagemSalva = copiarImagemParaPasta(tfId.getText());

            // 2. Cria um objeto Palestrante com os dados da tela
            Palestrante p = new Palestrante();
            p.setId(tfId.getText().isEmpty() ? null : Long.valueOf(tfId.getText()));
            p.setNome(tfNome.getText());
            p.setTemasAbordados(tfTemas.getText());
            p.setMinicurriculo(taMinicurriculo.getText());
            p.setFotoPath(caminhoDaImagemSalva); // Define o caminho da foto no objeto

            // 3. Manda para o serviço salvar
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

    /**
     * Remove o palestrante selecionado e sua foto.
     */
    private void remover() {
        if (tfId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um palestrante para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente remover?", "Confirmar remoção", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Long id = Long.valueOf(tfId.getText());
            Palestrante palestrante = service.select(id); // Busca o palestrante para pegar o caminho da foto

            // Verificação: Se o palestrante tem uma foto, tenta deletar o arquivo do disco
            if (palestrante != null && palestrante.getFotoPath() != null) {
                try {
                    Files.deleteIfExists(Paths.get(palestrante.getFotoPath()));
                } catch (IOException e) {
                    System.err.println("Erro ao deletar arquivo de imagem: " + e.getMessage());
                }
            }

            service.remover(id); // Remove o registro do banco
            limparCampos();
            atualizarTabela();
        }
    }

    /**
     * Limpa todos os campos do formulário e a seleção de imagem.
     */
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

    /**
     * Limpa a tabela e a preenche novamente com os dados do banco.
     */
    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa as linhas existentes
        List<Palestrante> palestrantes = service.listarTodos();
        for (Palestrante p : palestrantes) {
            tableModel.addRow(new Object[]{p.getId(), p.getNome(), p.getTemasAbordados()});
        }
    }
}