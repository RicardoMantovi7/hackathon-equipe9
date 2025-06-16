package felipe.nascimento.service;

import felipe.nascimento.dao.InscricaoDao;

/**
 * Camada de Serviço para a lógica de inscrições de Alunos em Eventos.
 * Esta classe é muito simples, pois a única "regra de negócio" é criar a ligação
 * entre um aluno e um evento, o que é feito diretamente pelo DAO.
 */
public class InscricaoService {
    private final InscricaoDao dao = new InscricaoDao();

    /**
     * Repassa o pedido de inscrição da GUI para o DAO.
     * A GUI fornece os IDs, e este serviço simplesmente os encaminha.
     *
     * @param alunoId O ID do aluno a ser inscrito.
     * @param eventoId O ID do evento no qual o aluno será inscrito.
     * @return true se a inscrição no banco foi bem-sucedida.
     */
    public boolean inscrever(Long alunoId, Long eventoId) {
        return dao.inscrever(alunoId, eventoId);
    }
}