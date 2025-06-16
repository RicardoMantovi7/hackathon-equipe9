package felipe.nascimento.service;

import felipe.nascimento.dao.PalestranteDao;
import felipe.nascimento.model.Palestrante;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Camada de Serviço para a entidade Palestrante.
 * Orquestra as operações entre a GUI e o PalestranteDao, aplicando regras de negócio.
 */
public class PalestranteService {
    private final PalestranteDao dao = new PalestranteDao();

    /**
     * Centraliza a lógica para salvar um palestrante (novo ou existente).
     * A regra de negócio é: se o ID é nulo, insere um novo palestrante. Senão, atualiza o existente.
     *
     * @param p O objeto Palestrante com os dados vindos da GUI.
     * @return true se a operação foi bem-sucedida.
     */
    public boolean salvar(Palestrante p) {
        return p.getId() == null ? dao.insert(p) : dao.update(p);
    }

    /**
     * Orquestra a remoção de um palestrante.
     * Lógica de negócio: além de remover do banco, também deleta o arquivo da foto do disco.
     *
     * @param id O ID do palestrante a ser removido.
     * @return true se a remoção do banco foi bem-sucedida.
     */
    public boolean remover(Long id) {
        // Primeiro, busca o palestrante no banco para pegar o caminho da foto.
        Palestrante palestrante = dao.select(id);
        if (palestrante != null && palestrante.getFotoPath() != null) {
            try {
                // Tenta deletar o arquivo de imagem do disco.
                Files.deleteIfExists(Paths.get(palestrante.getFotoPath()));
            } catch (IOException e) {
                // Se der erro ao apagar o arquivo, imprime no console, mas continua para apagar do banco.
                System.err.println("Erro ao deletar arquivo de imagem: " + e.getMessage());
            }
        }
        // Finalmente, pede ao DAO para remover o registro do banco.
        return dao.delete(id);
    }

    /**
     * Pede ao DAO para buscar todos os palestrantes.
     * @return Uma lista com todos os objetos Palestrante.
     */
    public List<Palestrante> listarTodos() {
        return dao.selectAll();
    }

    /**
     * Pede ao DAO para buscar um único palestrante pelo seu ID.
     * @param id O ID do palestrante.
     * @return O objeto Palestrante encontrado, ou null.
     */
    public Palestrante select(Long id) {
        return dao.select(id);
    }
}