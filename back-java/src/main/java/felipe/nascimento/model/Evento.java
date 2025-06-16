package felipe.nascimento.model;

public class Evento {
    private Long id;
    private String nomeEvento;
    private String dataEvento;
    private Palestrante palestrante;

    public Evento() {}

    public Evento(Long id, String nomeEvento, String dataEvento, Palestrante palestrante) {
        this.id = id;
        this.nomeEvento = nomeEvento;
        this.dataEvento = dataEvento;
        this.palestrante = palestrante;
    }

    // Getters e Setters (já existentes)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNomeEvento() { return nomeEvento; }
    public void setNomeEvento(String nomeEvento) { this.nomeEvento = nomeEvento; }
    public String getDataEvento() { return dataEvento; }
    public void setDataEvento(String dataEvento) { this.dataEvento = dataEvento; }
    public Palestrante getPalestrante() { return palestrante; }
    public void setPalestrante(Palestrante palestrante) { this.palestrante = palestrante; }

    /**
     * MÉTODO ADICIONADO
     * Este método diz ao JComboBox para exibir o nome do evento.
     */
    @Override
    public String toString() {
        return this.nomeEvento;
    }
}