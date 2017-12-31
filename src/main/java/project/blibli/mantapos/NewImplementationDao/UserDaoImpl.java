package project.blibli.mantapos.NewImplementationDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import project.blibli.mantapos.ImplementationDao.DbConnection;
import project.blibli.mantapos.Model.User;
import project.blibli.mantapos.NewInterfaceDao.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoImpl implements UserDao {

    private static final String tableUser = "users";
    private static final String idUser = "id";
    private static final String roleUser = "role";
    private static final String usernameUser = "username";
    private static final String passwordUser = "password";
    private static final String enabled = "enabled";
    private static final String namaLengkap = "nama_lengkap";
    private static final String jenisKelamin = "jenis_kelamin";
    private static final String nomorKtp = "nomor_ktp";
    private static final String nomorTelepon = "nomor_telepon";
    private static final String alamatUser = "alamat";
    private static final String idResto = "id_resto";

    private static final String roleType = "role_type";
    private static final String roleAdmin = "admin";
    private static final String roleOwner = "owner";
    private static final String roleManager = "manager";
    private static final String roleCashier = "cashier";

    private static final String tableUserRoles = "users_roles";
    private static final String refTableRestoran = "restoran";

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    String hashedPassword;

    @Override
    public void createUsersRole() throws SQLException {
        Connection connection;
        PreparedStatement preparedStatement;
        connection = DbConnection.openConnection();
        preparedStatement = connection.prepareStatement(
                "CREATE TYPE " + roleType + " AS ENUM " + "(" +
                        "'" + roleAdmin + "'," +
                        "'" + roleOwner + "'," +
                        "'" + roleManager + "'," +
                        "'" + roleCashier + "'" +
                        ")"
        );
        preparedStatement.executeUpdate();
        DbConnection.closePreparedStatement(preparedStatement);
        DbConnection.closeConnection(connection);
    }

    @Override
    public void createTableUsersRole() throws SQLException {
        Connection connection;
        PreparedStatement preparedStatement;
        connection = DbConnection.openConnection();
        preparedStatement = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS " + tableUserRoles +
                        "(" +
                        idUser + " INT NOT NULL, " +
                        usernameUser + " TEXT NOT NULL, " +
                        roleUser + " " + roleType + " NOT NULL, " +
                        "UNIQUE (" + usernameUser + "), " +
                        "CONSTRAINT id_user_fk FOREIGN KEY (" + idUser + ")" + "REFERENCES " + tableUser + "(" + idUser + "))"
        );
        preparedStatement.executeUpdate();
        DbConnection.closePreparedStatement(preparedStatement);
        DbConnection.closeConnection(connection);
    }

    @Override
    public void insertToTableUsersRole(HashMap<Integer, String> condition) throws SQLException {
        Connection connection;
        PreparedStatement preparedStatement;
        connection = DbConnection.openConnection();
        preparedStatement = connection.prepareStatement(
                "INSERT INTO " + tableUserRoles +
                        "(" +
                        idUser + "," +
                        usernameUser + "," +
                        roleUser +
                        ")" + " VALUES (?,?,?::" + roleType + ")"
        );
        preparedStatement.setInt(1, readId(entry.getValue().toString()));
        for (Map.Entry<Integer, String> entry : condition.entrySet()){
            preparedStatement.setString(2, modelData.getUsername());
            preparedStatement.setString(3, modelData.getRole());
        }
        preparedStatement.executeUpdate();
        DbConnection.closePreparedStatement(preparedStatement);
        DbConnection.closeConnection(connection);
    }

    @Override
    public void createTable() throws SQLException {
        Connection connection;
        PreparedStatement preparedStatement;
        connection = DbConnection.openConnection();
        preparedStatement = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS " + tableUser +
                        "(" +
                        idUser + " SERIAL PRIMARY KEY, " +
                        usernameUser + " TEXT NOT NULL, " +
                        passwordUser + " TEXT NOT NULL, " +
                        enabled + " boolean NOT NULL DEFAULT FALSE, " +
                        namaLengkap + " TEXT NOT NULL, " +
                        jenisKelamin + " TEXT NOT NULL, " +
                        nomorKtp + " TEXT NOT NULL, " +
                        nomorTelepon + " TEXT NOT NULL, " +
                        alamatUser + " TEXT NOT NULL, " +
                        idResto + " INT NOT NULL, " +
                        "UNIQUE (" + usernameUser + ")," +
                        "CONSTRAINT id_restoran_fk FOREIGN KEY (" + idResto + ") REFERENCES " + refTableRestoran + "(id))"
        );
        preparedStatement.executeUpdate();
        DbConnection.closePreparedStatement(preparedStatement);
        DbConnection.closeConnection(connection);
    }

    @Override
    public void insert(User modelData) throws SQLException {
        Connection connection;
        PreparedStatement preparedStatement = null;
        connection = DbConnection.openConnection();
        preparedStatement = connection.prepareStatement(
                "INSERT INTO " + tableUser +
                        "(" +
                        usernameUser + "," +
                        passwordUser + "," +
                        enabled + "," +
                        namaLengkap + "," +
                        jenisKelamin + "," +
                        nomorKtp + "," +
                        nomorTelepon + "," +
                        alamatUser + "," +
                        this.idResto +
                        ")" + " VALUES (?,?,?,?,?,?,?,?,?)"
        );
        preparedStatement.setString(1, modelData.getUsername());
        hashedPassword = passwordEncoder.encode(modelData.getPassword());
        preparedStatement.setString(2, hashedPassword);
        preparedStatement.setBoolean(3, true);
        preparedStatement.setString(4, modelData.getNamaLengkap());
        preparedStatement.setString(5, modelData.getJenisKelamin());
        preparedStatement.setString(6, modelData.getNomorKtp());
        preparedStatement.setString(7, modelData.getNomorTelepon());
        preparedStatement.setString(8, modelData.getAlamat());
        preparedStatement.setInt(9, modelData.getIdResto());
        preparedStatement.executeUpdate();
        DbConnection.closePreparedStatement(preparedStatement);
        DbConnection.closeConnection(connection);
    }

    @Override
    public List<User> readAll(HashMap<Integer, String> condition) throws SQLException {
        return null;
    }

    @Override
    public User readOne(HashMap<Integer, String> condition) throws SQLException {
        return null;
    }

    @Override
    public int readId(HashMap<Integer, String> condition) throws SQLException {
        int id=0;
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        connection = DbConnection.openConnection();
        preparedStatement = connection.prepareStatement(
                "SELECT " + idUser + " FROM " + tableUser + " WHERE " + usernameUser + "=?"
        );
        for (Map.Entry<Integer, String> entry : condition.entrySet()){
            preparedStatement.setString(1, entry.getValue());
        }
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            id = resultSet.getInt(idUser);
        }
        DbConnection.closeResultSet(resultSet);
        DbConnection.closePreparedStatement(preparedStatement);
        DbConnection.closeConnection(connection);
        return id;
    }

    @Override
    public int count(HashMap<Integer, String> condition) throws SQLException {
        return 0;
    }

    @Override
    public void deactivate(HashMap<Integer, String> condition) throws SQLException {

    }

    @Override
    public void activate(HashMap<Integer, String> condition) throws SQLException {

    }
}
