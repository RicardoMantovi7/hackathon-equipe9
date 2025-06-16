package felipe.nascimento.service;

import felipe.nascimento.dao.AlunoDao;
import felipe.nascimento.model.Aluno;
import java.util.List;

/**
 * Camada de serviço para Aluno com responsabilidade de apenas LEITURA.
 */
public class AlunoService {
    private final AlunoDao dao = new AlunoDao();

    public List<Aluno> listarTodos() {
        return dao.selectAll();
    }

    public List<Aluno> listarPorEvento(Long eventoId) {
        return dao.listarPorEvento(eventoId);
    }
}