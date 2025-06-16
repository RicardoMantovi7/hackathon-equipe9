package felipe.nascimento.dao;

import java.sql.PreparedStatement;

/**
 * DAO para gerenciar as operações na tabela 'inscricao'.
 * Esta tabela faz a ligação entre Aluno e Evento.
 */
public class InscricaoDao extends Dao {

    /**
     * Inscreve um aluno em um evento, inserindo uma nova linha na tabela 'inscricao'.
     * @param alunoId O ID do aluno.
     * @param eventoId O ID do evento.
     * @return true se a inscrição foi bem-sucedida, false caso contrário.
     */
    public boolean inscrever(Long alunoId, Long eventoId) {
        String sql = "INSERT INTO inscricao(id_aluno, id_evento) VALUES(?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setLong(1, alunoId);
            ps.setLong(2, eventoId);
            ps.execute();
            return true;
        } catch (Exception e) {
            // A exceção pode ocorrer se a inscrição já existir (violação de chave primária)
            System.err.println("Erro ao inscrever aluno: " + e.getMessage());
            return false;
        }
    }
}