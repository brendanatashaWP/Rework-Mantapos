package project.blibli.mantapos.Mapper;

import org.springframework.jdbc.core.RowMapper;
import project.blibli.mantapos.Model.Menu;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuMapper implements RowMapper<Menu>{
    @Override
    public Menu mapRow(ResultSet resultSet, int i) throws SQLException {
        Menu menu = new Menu();
        menu.setId(resultSet.getInt("id"));
        menu.setNama_menu(resultSet.getString("nama_menu"));
        menu.setHarga_menu(resultSet.getDouble("harga_menu"));
        menu.setLokasi_gambar_menu(resultSet.getString("lokasi_gambar_menu"));
        menu.setKategori_menu(resultSet.getString("kategori_menu"));
        return menu;
    }
}
