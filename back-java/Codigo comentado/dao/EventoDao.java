package felipe.nascimento.dao;

import felipe.nascimento.model.Evento;
import felipe.nascimento.model.Palestrante;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para a entidade Evento. Implementa o CRUD completo.
 */
public class EventoDao extends Dao implements DaoInterface<Evento> {

    // Cria uma instância de PalestranteDao para poder buscar os dados do palestrante associado ao evento.
    private PalestranteDao palestranteDao = new PalestranteDao();

    /**
     * Insere um novo evento no banco de dados.
     * @param evento O objeto Evento com os dados a serem salvos.
     * @return true se a inserção foi bem-sucedida, false caso contrário.
     */
    @Override
    public Boolean insert(Evento evento) {
        String sql = "INSERT INTO evento(nome_evento, data_evento, id_palestrante) VALUES(?, ?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, evento.getNomeEvento());
            ps.setString(2, evento.getDataEvento());
            // Pega o ID do objeto Palestrante que está dentro do objeto Evento
            ps.setLong(3, evento.getPalestrante().getId());
            ps.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Atualiza os dados de um evento existente.
     * @param evento O objeto Evento com os dados atualizados.
     * @return true se a atualização foi bem-sucedida, false caso contrário.
     */
    @Override
    public Boolean update(Evento evento) {
        String sql = "UPDATE evento SET nome_evento=?, data_evento=?, id_palestrante=? WHERE id=?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, evento.getNomeEvento());
            ps.setString(2, evento.getDataEvento());
            ps.setLong(3, evento.getPalestrante().getId());
            ps.setLong(4, evento.getId());
            ps.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deleta um evento do banco de dados pelo seu ID.
     * @param pk O ID do evento a ser deletado.
     * @return true se a deleção foi bem-sucedida, false caso contrário.
     */
    @Override
    public Boolean delete(Long pk) {
        String sql = "DELETE FROM evento WHERE id=?";
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
     * Busca um único evento pelo seu ID.
     * @param pk O ID do evento.
     * @return O objeto Evento se encontrado, ou null.
     */
    @Override
    public Evento select(Long pk) {
        String sql = "SELECT * FROM evento WHERE id=?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setLong(1, pk);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Busca o objeto Palestrante completo usando o 'id_palestrante' do evento
                Palestrante p = palestranteDao.select(rs.getLong("id_palestrante"));
                // Cria e retorna o objeto Evento, já com o palestrante associado
                return new Evento(
                        rs.getLong("id"),
                        rs.getString("nome_evento"),
                        rs.getString("data_evento"),
                        p
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Busca todos os eventos cadastrados.
     * @return Uma lista de todos os Eventos.
     */
    @Override
    public List<Evento> selectAll() {
        String sql = "SELECT * FROM evento ORDER BY data_evento";
        List<Evento> eventos = new ArrayList<>();
        try (PreparedStatement ps = getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                // Para cada evento na lista, busca também os dados do palestrante associado
                Palestrante p = palestranteDao.select(rs.getLong("id_palestrante"));
                eventos.add(new Evento(
                        rs.getLong("id"),
                        rs.getString("nome_evento"),
                        rs.getString("data_evento"),
                        p
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eventos;
    }
}