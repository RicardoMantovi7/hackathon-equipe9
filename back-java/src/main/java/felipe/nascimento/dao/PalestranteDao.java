package felipe.nascimento.dao;

import felipe.nascimento.model.Palestrante;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PalestranteDao extends Dao implements DaoInterface<Palestrante> {

    @Override
    public Boolean insert(Palestrante palestrante) {
        String sql = "INSERT INTO palestrante(nome, minicurrículo, temas_abordados, foto_path) VALUES(?, ?, ?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, palestrante.getNome());
            ps.setString(2, palestrante.getMinicurriculo());
            ps.setString(3, palestrante.getTemasAbordados());
            ps.setString(4, palestrante.getFotoPath()); // Adiciona o novo campo
            ps.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean update(Palestrante palestrante) {
        String sql = "UPDATE palestrante SET nome=?, minicurrículo=?, temas_abordados=?, foto_path=? WHERE id=?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, palestrante.getNome());
            ps.setString(2, palestrante.getMinicurriculo());
            ps.setString(3, palestrante.getTemasAbordados());
            ps.setString(4, palestrante.getFotoPath()); // Adiciona o novo campo
            ps.setLong(5, palestrante.getId());
            ps.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Palestrante select(Long pk) {
        String sql = "SELECT * FROM palestrante WHERE id=?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setLong(1, pk);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Palestrante(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("minicurrículo"),
                        rs.getString("temas_abordados"),
                        rs.getString("foto_path") // Lê o novo campo do banco
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

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
                        rs.getString("foto_path") // Lê o novo campo do banco
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return palestrantes;
    }

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
}