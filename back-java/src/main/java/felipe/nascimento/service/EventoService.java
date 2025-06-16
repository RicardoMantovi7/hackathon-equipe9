package felipe.nascimento.service;

import felipe.nascimento.dao.EventoDao;
import felipe.nascimento.model.Evento;
import java.util.List;

public class EventoService {
    private EventoDao dao = new EventoDao();

    public boolean salvar(Evento evento) {
        return evento.getId() == null ? dao.insert(evento) : dao.update(evento);
    }

    public boolean remover(Long id) {
        return dao.delete(id);
    }

    public List<Evento> listarTodos() {
        return dao.selectAll();
    }
}