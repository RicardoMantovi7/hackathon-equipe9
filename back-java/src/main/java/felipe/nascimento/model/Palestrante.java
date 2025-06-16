package felipe.nascimento.model;

public class Palestrante {
    private Long id;
    private String nome;
    private String minicurriculo;
    private String temasAbordados;
    private String fotoPath; // Novo campo para o caminho da imagem

    public Palestrante() {}

    // Construtor atualizado
    public Palestrante(Long id, String nome, String minicurriculo, String temasAbordados, String fotoPath) {
        this.id = id;
        this.nome = nome;
        this.minicurriculo = minicurriculo;
        this.temasAbordados = temasAbordados;
        this.fotoPath = fotoPath;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getMinicurriculo() { return minicurriculo; }
    public void setMinicurriculo(String minicurriculo) { this.minicurriculo = minicurriculo; }
    public String getTemasAbordados() { return temasAbordados; }
    public void setTemasAbordados(String temasAbordados) { this.temasAbordados = temasAbordados; }
    public String getFotoPath() { return fotoPath; }
    public void setFotoPath(String fotoPath) { this.fotoPath = fotoPath; }

    @Override
    public String toString() {
        return getNome();
    }
}