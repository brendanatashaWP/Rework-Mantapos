package project.blibli.mantapos.ImplementationDao;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import project.blibli.mantapos.Model.User;
import project.blibli.mantapos.InterfaceDao.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String hashedPassword;

    @Override
    public void createRoleUser() {
        Connection connection;
        PreparedStatement preparedStatement = null;
        connection = DbConnection.openConnection();
        try{
            preparedStatement = connection.prepareStatement(
                    "CREATE TYPE " + roleType + " AS ENUM " + "(" +
                            "'" + roleAdmin + "'," +
                            "'" + roleOwner + "'," +
                            "'" + roleManager + "'," +
                            "'" + roleCashier + "'" +
                            ")"
            );
            preparedStatement.executeUpdate();
        } catch (Exception ex){
            System.out.println("Gagal create role user : " + ex.toString());
        } finally {
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
    }

    @Override
    public void deleteUserAndDependencies(int idResto) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        connection = DbConnection.openConnection();
        try{
            preparedStatement = connection.prepareStatement(
                    "UPDATE " + tableUser + " SET " + enabled + "=? WHERE " + this.idResto + "=?"
            );
            preparedStatement.setBoolean(1, false);
            preparedStatement.setInt(2, idResto);
            preparedStatement.executeUpdate();
        } catch (Exception ex){
            System.out.println("Gagal delete user and dependencies id resto " + idResto + " : " + ex.toString());
        } finally {
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
    }

    @Override
    public void activateUser(int id) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        connection = DbConnection.openConnection();
        try{
            preparedStatement = connection.prepareStatement(
                    "UPDATE " + tableUser + " SET " + enabled + "=? WHERE " + idUser + "=?"
            );
            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (Exception ex){
            System.out.println("Gagal activate user " + id + " : " + ex.toString());
        } finally {
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
    }

    @Override
    public List<User> readAllUsers(int idResto, String role, int itemPerPage, int page) {
        List<User> userList = new ArrayList<>();
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        connection = DbConnection.openConnection();
        try{
            if(role.equals("manager&cashier")){
                preparedStatement = connection.prepareStatement(
                        "SELECT *,users_roles.role FROM " +
                                tableUser + "," + tableUserRoles +
                                " WHERE " + this.idResto + "=?" +
                                " AND users_roles.username=users.username" +
                                " AND users_roles.role IN (?::" + roleType + ", ?::" + roleType + ") LIMIT ? OFFSET ?" //ambil user roles dengan role manager dan cashier
                );
                preparedStatement.setInt(1, idResto);
                preparedStatement.setString(2, "manager");
                preparedStatement.setString(3, "cashier");
                preparedStatement.setInt(4, itemPerPage);
                preparedStatement.setInt(5, (page-1)*itemPerPage);
            } else {
                preparedStatement = connection.prepareStatement(
                        "SELECT *,users_roles.role FROM " +
                                tableUser + "," + tableUserRoles +
                                " WHERE " + idResto + "=?" +
                                " AND users_roles.username=users.username" +
                                " AND users_roles.role=?::" + roleType + " LIMIT ? OFFSET ?"
                );
                preparedStatement.setInt(1, idResto);
                preparedStatement.setString(2, role);
                preparedStatement.setInt(3, itemPerPage);
                preparedStatement.setInt(4, (page-1)*itemPerPage);
            }
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt(idUser));
                user.setNamaLengkap(resultSet.getString(namaLengkap));
                user.setJenisKelamin(resultSet.getString(jenisKelamin));
                user.setUsername(resultSet.getString(usernameUser));
                user.setNomorKtp(resultSet.getString(nomorKtp));
                user.setNomorTelepon(resultSet.getString(nomorTelepon));
                user.setAlamat(resultSet.getString(alamatUser));
                user.setRole(resultSet.getString(roleUser));
                user.setEnabled(resultSet.getBoolean(enabled));
                userList.add(user);
            }
        } catch (Exception ex){
            System.out.println("Gagal read all user data : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return userList;
    }

    @Override
    public int getId(String username) {
        int id=0;
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        connection = DbConnection.openConnection();
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT " + idUser + " FROM " + tableUser + " WHERE " + usernameUser + "=?"
            );
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                id = resultSet.getInt(idUser);
            }
        } catch (Exception ex){
            System.out.println("Gagal get id user berdasarkan username " + username + " : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return id;
    }

    @Override
    public void createTable() throws SQLException {
        Connection connection;
        PreparedStatement preparedStatement = null;
        connection = DbConnection.openConnection();
        try{
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
        } catch (Exception ex){
            System.out.println("Gagal create table " + tableUser + " atau " + tableUserRoles + " : " + ex.toString());
        } finally {
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
    }

    @Override
    public void insert(User modelData, Integer idResto) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        connection = DbConnection.openConnection();
        try{
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
            preparedStatement.setInt(9, idResto);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(
                    "INSERT INTO " + tableUserRoles +
                            "(" +
                            idUser + "," +
                            usernameUser + "," +
                            roleUser +
                            ")" + " VALUES (?,?,?::" + roleType + ")"
            );
            preparedStatement.setInt(1, getId(modelData.getUsername()));
            preparedStatement.setString(2, modelData.getUsername());
            preparedStatement.setString(3, modelData.getRole());
            preparedStatement.executeUpdate();
        } catch (Exception ex){
            System.out.println("Gagal insert user " + modelData.getUsername() + " : " + ex.toString());
        } finally {
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
    }

    //Method readAll di-replace dengan readAllUsers karena butuh parameter role
    @Override
    public List<User> readAll(Integer idResto, Integer itemPerPage, Integer page) {
        return null;
    }

    @Override
    public User readOne(Integer idData) {
        User user = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        connection = DbConnection.openConnection();
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT *, users_roles.role FROM " + tableUser + "," + tableUserRoles +
                            " WHERE users.id=?" + " AND users_roles.id=users.id"
            );
            preparedStatement.setInt(1, idData);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt(idUser));
                user.setUsername(resultSet.getString(usernameUser));
                user.setPassword(resultSet.getString(passwordUser));
                user.setNamaLengkap(resultSet.getString(namaLengkap));
                user.setJenisKelamin(resultSet.getString(jenisKelamin));
                user.setAlamat(resultSet.getString(alamatUser));
                user.setNomorKtp(resultSet.getString(nomorKtp));
                user.setNomorTelepon(resultSet.getString(nomorTelepon));
                user.setRole(resultSet.getString(roleUser));
                user.setEnabled(resultSet.getBoolean(enabled));
            }
        } catch (Exception ex){
            System.out.println("Gagal readOne user with id " + idData + " : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return user;
    }

    //Sudah diganti dengan method getId berdasarkan username
    @Override
    public int getLastId(Integer idResto) {
        return 0;
    }

    @Override
    public void update(User modelData, Integer idResto) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        connection = DbConnection.openConnection();
        try {
            if (modelData.getPassword().equals("") || modelData.getPassword().isEmpty()) {
                preparedStatement = connection.prepareStatement(
                        "UPDATE " + tableUser + " SET " + namaLengkap + "=?" +
                                "," + usernameUser + "=?" +
                                "," + nomorTelepon + "=?" +
                                "," + nomorKtp + "=?" +
                                "," + alamatUser + "=?" +
                                "," + jenisKelamin + "=?" +
                                " WHERE " + idUser + "=? AND " + this.idResto + "=?"
                );
                preparedStatement.setString(1, modelData.getNamaLengkap());
                preparedStatement.setString(2, modelData.getUsername());
                preparedStatement.setString(3, modelData.getNomorTelepon());
                preparedStatement.setString(4, modelData.getNomorKtp());
                preparedStatement.setString(5, modelData.getAlamat());
                preparedStatement.setString(6, modelData.getJenisKelamin());
                preparedStatement.setInt(7, modelData.getId());
                preparedStatement.setInt(8, idResto);
                preparedStatement.executeUpdate();
            } else {
                preparedStatement = connection.prepareStatement(
                        "UPDATE " + tableUser + " SET " + namaLengkap + "=?" +
                                "," + usernameUser + "=?" +
                                "," + passwordUser + "=?" +
                                "," + nomorTelepon + "=?" +
                                "," + nomorKtp + "=?" +
                                "," + alamatUser + "=?" +
                                "," + jenisKelamin + "=?" +
                                " WHERE " + idUser + "=? AND " + this.idResto + "=?"
                );
                preparedStatement.setString(1, modelData.getNamaLengkap());
                preparedStatement.setString(2, modelData.getUsername());
                preparedStatement.setString(3, passwordEncoder.encode(modelData.getPassword()));
                preparedStatement.setString(4, modelData.getNomorTelepon());
                preparedStatement.setString(5, modelData.getNomorKtp());
                preparedStatement.setString(6, modelData.getAlamat());
                preparedStatement.setString(7, modelData.getJenisKelamin());
                preparedStatement.setInt(8, modelData.getId());
                preparedStatement.setInt(9, idResto);
                preparedStatement.executeUpdate();
            }
            preparedStatement = connection.prepareStatement(
                    "UPDATE " + tableUserRoles + " SET " + usernameUser + "=?, " + roleUser + "=?::" + roleType + " WHERE " + idUser + "=?"
            );
            preparedStatement.setString(1, modelData.getUsername());
            preparedStatement.setString(2, modelData.getRole());
            preparedStatement.setInt(3, modelData.getId());
            preparedStatement.executeUpdate();
        } catch (Exception ex){
            System.out.println("Gagal update user id " + modelData.getId() + " id resto " + idResto + " : " + ex.toString());
        } finally {
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
    }

    @Override
    public void delete(Integer idData) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        connection = DbConnection.openConnection();
        try{
            preparedStatement = connection.prepareStatement(
                    "UPDATE " + tableUser + " SET " + enabled + "=? WHERE " + idUser + "=?"
            );
            preparedStatement.setBoolean(1, false);
            preparedStatement.setInt(2, idData);
            preparedStatement.executeUpdate();
        } catch (Exception ex){
            System.out.println("Gagal delete user : " + ex.toString());
        } finally {
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
    }

    @Override
    public int count(Integer idResto) {
        int count=0;
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        connection = DbConnection.openConnection();
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT COUNT(*) FROM " + tableUser + " WHERE " + this.idResto + "=?"
            );
            preparedStatement.setInt(1, idResto);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                count = resultSet.getInt(1);
            }
        } catch (Exception ex){
            System.out.println("Gagal count users " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return count;
    }
}
