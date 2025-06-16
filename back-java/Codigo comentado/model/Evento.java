package felipe.nascimento.model;

/**
 * Classe de Modelo (Model) que representa a entidade 'Evento'.
 * Funciona como uma planta ou molde para criar objetos que guardam todas as
 * informações de um único evento acadêmico, como seu nome, data e o palestrante associado.
 * Cada instância desta classe corresponde a uma linha na tabela 'evento' do banco de dados.
 */
public class Evento {

    // --- Atributos da Classe ---
    private Long id;                // Identificador único do evento no banco de dados (chave primária).
    private String nomeEvento;      // Nome oficial do evento (ex: "Semana de Tecnologia").
    private String dataEvento;      // Data de realização do evento, armazenada como texto (ex: "25/10/2025").
    private Palestrante palestrante; // Objeto Palestrante associado a este evento, representando o relacionamento entre as tabelas.

    /**
     * Construtor padrão (sem argumentos).
     * É uma boa prática ter um construtor vazio, pois algumas bibliotecas e frameworks
     * precisam dele para criar instâncias da classe de forma automática.
     */
    public Evento() {}

    /**
     * Construtor completo (com argumentos).
     * Permite criar um objeto Evento já com todos os seus dados definidos de uma só vez.
     * É útil ao ler dados do banco e transformá-los em objetos.
     *
     * @param id O ID do evento.
     * @param nomeEvento O nome do evento.
     * @param dataEvento A data do evento.
     * @param palestrante O objeto Palestrante responsável pelo evento.
     */
    public Evento(Long id, String nomeEvento, String dataEvento, Palestrante palestrante) {
        this.id = id;
        this.nomeEvento = nomeEvento;
        this.dataEvento = dataEvento;
        this.palestrante = palestrante;
    }

    // --- Getters e Setters ---
    // Métodos públicos que permitem que outras partes do programa acessem e modifiquem
    // os atributos privados da classe de forma segura e controlada (Encapsulamento).

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeEvento() { return nomeEvento; }
    public void setNomeEvento(String nomeEvento) { this.nomeEvento = nomeEvento; }

    public String getDataEvento() { return dataEvento; }
    public void setDataEvento(String dataEvento) { this.dataEvento = dataEvento; }

    public Palestrante getPalestrante() { return palestrante; }
    public void setPalestrante(Palestrante palestrante) { this.palestrante = palestrante; }

    /**
     * Sobrescreve (Overriding) o método padrão 'toString' da classe Object.
     * Este é um método crucial para componentes visuais do Swing como o JComboBox (lista de seleção).
     * Quando um objeto 'Evento' é adicionado a essa lista, este método é chamado para determinar
     * qual texto deve ser exibido para o usuário. Aqui, definimos que será o nome do evento.
     * Sem isso, seria exibido algo como "felipe.nascimento.model.Evento@1c885df5".
     *
     * @return O nome do evento, que será exibido nas listas.
     */
    @Override
    public String toString() {
        return this.nomeEvento;
    }
}