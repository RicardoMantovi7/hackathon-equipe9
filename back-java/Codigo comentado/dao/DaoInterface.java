package felipe.nascimento.dao;

import java.util.List;

/**
 * Define um "contrato" que as classes DAO que fazem CRUD completo devem seguir.
 * Obriga a implementação dos métodos básicos de persistência.
 * O <T> torna a interface genérica, pode ser usada para Palestrante, Evento, etc.
 */
public interface DaoInterface<T> {
    Boolean insert(T entity); // Insere um novo registro
    Boolean update(T entity); // Atualiza um registro existente
    Boolean delete(Long pk);  // Deleta um registro pela chave primária (pk)
    T select(Long pk);        // Busca um único registro pela chave primária
    List<T> selectAll();      // Busca todos os registros da tabela
}