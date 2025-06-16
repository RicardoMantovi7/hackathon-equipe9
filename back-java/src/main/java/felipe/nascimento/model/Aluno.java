package felipe.nascimento.model;

public class Aluno {
    private Long id;
    private String nome;
    private String cpf;
    private String endereco;

    // Construtores e Getters/Setters já existentes
    public Aluno() {}
    public Aluno(Long id, String nome, String cpf, String endereco) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    /**
     * MÉTODO ADICIONADO
     * Garante que o nome do aluno seja exibido corretamente em listas.
     */
    @Override
    public String toString() {
        return this.nome;
    }
}