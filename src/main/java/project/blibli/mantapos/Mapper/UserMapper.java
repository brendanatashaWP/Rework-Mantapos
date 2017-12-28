package project.blibli.mantapos.Mapper;

import org.springframework.jdbc.core.RowMapper;
import project.blibli.mantapos.Model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    //Class Mapper, untuk mapping value dari resultSet ke model yang bersesuaian.

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setNamaLengkap(resultSet.getString("nama_lengkap"));
        user.setUsername(resultSet.getString("username"));
        user.setNomorKtp(resultSet.getString("nomor_ktp"));
        user.setNomorTelepon(resultSet.getString("nomor_telepon"));
        user.setAlamat(resultSet.getString("alamat"));
        user.setRole(resultSet.getString("role"));
        user.setEnabled(resultSet.getBoolean("enabled"));
        return user;
    }
}
