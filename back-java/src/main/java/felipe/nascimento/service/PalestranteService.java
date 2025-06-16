package felipe.nascimento.service;

import felipe.nascimento.dao.PalestranteDao;
import felipe.nascimento.model.Palestrante;
import java.util.List;

public class PalestranteService {
    private final PalestranteDao dao = new PalestranteDao();

    public boolean salvar(Palestrante p) {
        return p.getId() == null ? dao.insert(p) : dao.update(p);
    }

    public boolean remover(Long id) {
        return dao.delete(id);
    }

    public List<Palestrante> listarTodos() {
        return dao.selectAll();
    }

    /**
     * Busca um palestrante específico pelo seu ID.
     * @param id A chave primária do palestrante.
     * @return O objeto Palestrante encontrado, ou null se não encontrar.
     */
    public Palestrante select(Long id) {
        return dao.select(id);
    }
}