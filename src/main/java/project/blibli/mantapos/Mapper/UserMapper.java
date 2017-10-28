package project.blibli.mantapos.Mapper;

import org.springframework.jdbc.core.RowMapper;
import project.blibli.mantapos.Beans_Model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setNama_lengkap(resultSet.getString("nama_lengkap"));
        user.setUsername(resultSet.getString("username"));
        user.setNomor_ktp(resultSet.getString("nomor_ktp"));
        user.setNomor_telepon(resultSet.getString("nomor_telepon"));
        user.setAlamat(resultSet.getString("alamat"));
        user.setRole(resultSet.getString("role"));
        user.setEnabled(resultSet.getBoolean("enabled"));
        return user;
    }
}
