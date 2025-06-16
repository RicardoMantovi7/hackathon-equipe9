package felipe.nascimento.dao;

import felipe.nascimento.model.Evento;
import felipe.nascimento.model.Palestrante;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EventoDao extends Dao implements DaoInterface<Evento> {

    private PalestranteDao palestranteDao = new PalestranteDao();

    @Override
    public Boolean insert(Evento evento) {
        String sql = "INSERT INTO evento(nome_evento, data_evento, id_palestrante) VALUES(?, ?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, evento.getNomeEvento());
            ps.setString(2, evento.getDataEvento());
            ps.setLong(3, evento.getPalestrante().getId());
            ps.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

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

    @Override
    public Evento select(Long pk) {
        String sql = "SELECT * FROM evento WHERE id=?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setLong(1, pk);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Palestrante p = palestranteDao.select(rs.getLong("id_palestrante"));
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

    @Override
    public List<Evento> selectAll() {
        String sql = "SELECT * FROM evento ORDER BY data_evento";
        List<Evento> eventos = new ArrayList<>();
        try (PreparedStatement ps = getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
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