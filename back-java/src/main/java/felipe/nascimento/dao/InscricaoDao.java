package felipe.nascimento.dao;

import java.sql.PreparedStatement;

/**
 * DAO para gerenciar as operações na tabela 'inscricao'.
 */
public class InscricaoDao extends Dao {

    public boolean inscrever(Long alunoId, Long eventoId) {
        String sql = "INSERT INTO inscricao(id_aluno, id_evento) VALUES(?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setLong(1, alunoId);
            ps.setLong(2, eventoId);
            ps.execute();
            return true;
        } catch (Exception e) {
            // A exceção pode ocorrer se a inscrição já existir
            System.err.println("Erro ao inscrever aluno: " + e.getMessage());
            return false;
        }
    }
}