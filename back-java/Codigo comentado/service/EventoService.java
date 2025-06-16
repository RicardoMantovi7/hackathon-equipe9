package felipe.nascimento.service;

import felipe.nascimento.dao.EventoDao;
import felipe.nascimento.model.Evento;
import java.util.List;

/**
 * contem a lógica de negócio dos eventos
 * as operações entre a interface gráfica (EventoGui) e a camada de acesso a dados (EventoDao).
 */
public class EventoService {
    private final EventoDao dao = new EventoDao();

    /**
     * o método decide se um novo evento deve ser criado ou se um evento existente deve ser atualizado.
     * A lógica é: se o ID do evento é nulo, ele ainda não existe no banco, então chamamos 'insert'.
     * Caso contrário, ele já existe, então chamamos 'update'.
     * @return true se a operação (insert ou update) foi bem-sucedida.
     */
    public boolean salvar(Evento evento) {
        // Operador a (condição) ? (valor se verdadeiro) chama insert : (valor se falso) chama update
        return evento.getId() == null ? dao.insert(evento) : dao.update(evento);
    }

    /**
     * Pede ao DAO para remover um evento do banco de dados.
*/
    public boolean remover(Long id) {
        return dao.delete(id);
    }

    /**
     * Pede ao DAO para buscar todos os eventos cadastrados.
     */
    public List<Evento> listarTodos() {
        return dao.selectAll();//retorna a lista com os eventos
    }
}