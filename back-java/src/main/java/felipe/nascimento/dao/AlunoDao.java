package felipe.nascimento.dao;

import felipe.nascimento.model.Aluno;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para Aluno com responsabilidade de apenas LEITURA de dados.
 */
public class AlunoDao extends Dao {

    public List<Aluno> selectAll() {
        String sql = "SELECT * FROM aluno ORDER BY nome";
        List<Aluno> alunos = new ArrayList<>();
        try (PreparedStatement ps = getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
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

    /**
     * Busca todos os alunos inscritos em um determinado evento.
     * @param eventoId O ID do evento para filtrar.
     * @return Uma lista de Alunos inscritos.
     */
    public List<Aluno> listarPorEvento(Long eventoId) {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT a.* FROM aluno a " +
                "JOIN inscricao i ON a.id = i.id_aluno " +
                "WHERE i.id_evento = ?";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setLong(1, eventoId);
            ResultSet rs = ps.executeQuery();
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