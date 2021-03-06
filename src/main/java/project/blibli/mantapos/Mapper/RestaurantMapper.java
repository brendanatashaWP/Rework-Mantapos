package project.blibli.mantapos.Mapper;

import org.springframework.jdbc.core.RowMapper;
import project.blibli.mantapos.Beans_Model.Restoran;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RestaurantMapper implements RowMapper<Restoran> {
    @Override
    public Restoran mapRow(ResultSet resultSet, int i) throws SQLException {
        Restoran restoran = new Restoran();
        restoran.setId(resultSet.getInt("id"));
        restoran.setNama_resto(resultSet.getString("nama_resto"));
        restoran.setLokasi_resto(resultSet.getString("lokasi_resto"));
        return restoran;
    }
}
