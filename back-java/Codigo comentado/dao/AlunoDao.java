package felipe.nascimento.dao;

import felipe.nascimento.model.Aluno;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para Aluno, com responsabilidade de apenas LEITURA de dados do banco.
 * Não possui insert, update ou delete, pois os alunos vêm do PHP.
 */
public class AlunoDao extends Dao {

    /**
     * Seleciona todos os alunos do banco de dados.
     * @return Uma lista com todos os alunos cadastrados.
     */
    public List<Aluno> selectAll() {
        String sql = "SELECT * FROM aluno ORDER BY nome"; // Query SQL
        List<Aluno> alunos = new ArrayList<>();
        // 'try-with-resources' garante que o PreparedStatement e o ResultSet sejam fechados
        try (PreparedStatement ps = getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            // Percorre cada linha do resultado da busca
            while (rs.next()) {
                // Cria um objeto Aluno com os dados da linha atual e adiciona na lista
                alunos.add(new Aluno(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("endereco")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace(); // Imprime o erro no console se algo der errado
        }
        return alunos; // Retorna a lista de alunos
    }

    /**
     * Busca todos os alunos que estão inscritos em um evento específico.
     * @param eventoId O ID do evento para usar como filtro.
     * @return Uma lista de Alunos inscritos.
     */
    public List<Aluno> listarPorEvento(Long eventoId) {
        List<Aluno> alunos = new ArrayList<>();
        // SQL que junta a tabela 'aluno' com a 'inscricao' para filtrar pelo evento
        String sql = "SELECT a.* FROM aluno a " +
                "JOIN inscricao i ON a.id = i.id_aluno " +
                "WHERE i.id_evento = ?";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            // Substitui o '?' na query pelo ID do evento recebido
            ps.setLong(1, eventoId);
            ResultSet rs = ps.executeQuery(); // Executa a busca
            // Percorre o resultado e cria a lista de alunos
            while (rs.next()) {
                alunos.add(new Aluno(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("endereco")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alunos;
    }
}