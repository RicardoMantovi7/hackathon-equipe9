package felipe.nascimento.model;

/**
 * Representa a entidade Aluno. Guarda os dados de um aluno.
 * É usado apenas para leitura, pois os dados vêm do PHP.
 */
public class Aluno {
    private Long id;
    private String nome;
    private String cpf;
    private String endereco;

    public Aluno() {}

    public Aluno(Long id, String nome, String cpf, String endereco) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
    }

    // Getters e Setters para acessar e modificar os atributos.
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    /**
     * Sobrescreve o método toString.
     * Isso faz com que, ao adicionar um objeto Aluno a um JComboBox (lista de seleção),
     * seja exibido o nome do aluno em vez de um código de memória.
     */
    @Override
    public String toString() {
        return this.nome;
    }
}