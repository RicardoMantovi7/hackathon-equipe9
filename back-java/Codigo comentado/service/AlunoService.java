package felipe.nascimento.service;

import felipe.nascimento.dao.AlunoDao;
import felipe.nascimento.model.Aluno;
import java.util.List;

/**
 * ATENÇÃO: Conforme a arquitetura do projeto, esta camada tem responsabilidade de apenas LEITURA.
 * A criação e edição de alunos é feita pelo front-end em PHP. O sistema Java apenas consome os dados.
 * Esta classe serve como uma ponte entre a GUI e o AlunoDao.
 */
public class AlunoService {
    // Cria uma instância final do DAO. 'final' significa que a referência não pode ser mudada.
    private final AlunoDao dao = new AlunoDao();

    /**
     * Busca e retorna a lista de todos os alunos cadastrados no banco.
     * Repassa a chamada diretamente para o DAO.
     * É utilizado, por exemplo, para preencher a lista de alunos na tela de inscrição.
     */
    public List<Aluno> listarTodos() {
        return dao.selectAll();
    }

    /**
     * Busca e retorna uma lista de alunos inscritos em um evento específico.
     */
    public List<Aluno> listarPorEvento(Long eventoId) {
        return dao.listarPorEvento(eventoId);
    }
}