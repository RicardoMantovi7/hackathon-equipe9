package felipe.nascimento.dao;

import felipe.nascimento.model.Palestrante;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para a entidade Palestrante. Implementa o CRUD completo.
 */
public class PalestranteDao extends Dao implements DaoInterface<Palestrante> {

    /**
     * Insere um novo palestrante no banco de dados.
     * @param palestrante O objeto Palestrante com os dados a serem salvos.
     * @return true se a inserção foi bem-sucedida, false caso contrário.
     */
    @Override
    public Boolean insert(Palestrante palestrante) {
        // Query SQL para inserir dados na tabela 'palestrante'
        String sql = "INSERT INTO palestrante(nome, minicurrículo, temas_abordados, foto_path) VALUES(?, ?, ?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            // Define os valores para cada '?' na query SQL
            ps.setString(1, palestrante.getNome());
            ps.setString(2, palestrante.getMinicurriculo());
            ps.setString(3, palestrante.getTemasAbordados());
            ps.setString(4, palestrante.getFotoPath()); // Salva o caminho da foto
            ps.execute(); // Executa o comando no banco
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Atualiza os dados de um palestrante existente no banco.
     * @param palestrante O objeto Palestrante com os dados atualizados.
     * @return true se a atualização foi bem-sucedida, false caso contrário.
     */
    @Override
    public Boolean update(Palestrante palestrante) {
        String sql = "UPDATE palestrante SET nome=?, minicurrículo=?, temas_abordados=?, foto_path=? WHERE id=?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, palestrante.getNome());
            ps.setString(2, palestrante.getMinicurriculo());
            ps.setString(3, palestrante.getTemasAbordados());
            ps.setString(4, palestrante.getFotoPath());
            ps.setLong(5, palestrante.getId()); // O ID é usado na cláusula WHERE para saber qual registro atualizar
            ps.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deleta um palestrante do banco de dados com base no seu ID.
     * @param pk A chave primária (ID) do palestrante a ser deletado.
     * @return true se a deleção foi bem-sucedida, false caso contrário.
     */
    @Override
    public Boolean delete(Long pk) {
        String sql = "DELETE FROM palestrante WHERE id=?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setLong(1, pk);
            ps.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Busca e retorna um único palestrante pelo seu ID.
     * @param pk O ID do palestrante a ser buscado.
     * @return O objeto Palestrante se encontrado, ou null se não existir.
     */
    @Override
    public Palestrante select(Long pk) {
        String sql = "SELECT * FROM palestrante WHERE id=?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setLong(1, pk);
            ResultSet rs = ps.executeQuery();
            // Verifica se a busca retornou algum resultado
            if (rs.next()) {
                // Cria e retorna um objeto Palestrante com os dados encontrados
                return new Palestrante(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("minicurrículo"),
                        rs.getString("temas_abordados"),
                        rs.getString("foto_path")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Retorna nulo se não encontrou o palestrante
    }

    /**
     * Busca e retorna todos os palestrantes cadastrados.
     * @return Uma lista de todos os Palestrantes.
     */
    @Override
    public List<Palestrante> selectAll() {
        String sql = "SELECT * FROM palestrante ORDER BY nome";
        List<Palestrante> palestrantes = new ArrayList<>();
        try (PreparedStatement ps = getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                palestrantes.add(new Palestrante(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("minicurrículo"),
                        rs.getString("temas_abordados"),
                        rs.getString("foto_path")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return palestrantes;
    }
}