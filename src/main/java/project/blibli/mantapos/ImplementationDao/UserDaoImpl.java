package project.blibli.mantapos.ImplementationDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import project.blibli.mantapos.Beans_Model.User;
import project.blibli.mantapos.Config.DataSourceConfig;
import project.blibli.mantapos.InterfaceDao.UserDao;
import project.blibli.mantapos.Mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao{
    private static final String table_name = "users";
    private static final String id = "id";
    private static final String username = "username";
    private static final String password = "password";
    private static final String role = "role";
    private static final String enabled = "enabled";
    private static final String nama_lengkap = "nama_lengkap";
    private static final String nomor_ktp = "nomor_ktp";
    private static final String nomor_telepon = "nomor_telepon";
    private static final String alamat = "alamat";
    private static final String id_resto = "id_resto";

    private static final String role_type = "role_type";
    private static final String role_admin = "admin";
    private static final String role_owner = "owner";
    private static final String role_manager = "manager";
    private static final String role_cashier = "cashier";

    private static final String table_role = "user_roles";

    private static final String ref_table_resto = "restoran";

    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public UserDaoImpl(){
        jdbcTemplate.setDataSource(DataSourceConfig.dataSource());
    }

    @Override
    public void CreateRole() {
        String query = "CREATE TYPE " + role_type + " AS ENUM " + "(" +
                "'" + role_admin + "'," +
                "'" + role_owner + "'," +
                "'" + role_manager + "'," +
                "'" + role_cashier + "'" +
                ")";
        try{
            jdbcTemplate.execute(query);
        } catch (Exception ex){
            System.out.println("Gagal create role : " + ex.toString());
        }
    }

    @Override
    public void CreateTableUser() {
        String query="CREATE TABLE IF NOT EXISTS " + table_name +
                "(" +
                id + " SERIAL PRIMARY KEY, " +
                username + " TEXT NOT NULL, " +
                password + " TEXT NOT NULL, " +
                enabled + " boolean NOT NULL DEFAULT FALSE, " +
                nama_lengkap + " TEXT NOT NULL, " +
                nomor_ktp + " TEXT NOT NULL, " +
                nomor_telepon + " TEXT NOT NULL, " +
                alamat + " TEXT NOT NULL, " +
                id_resto + " INT NOT NULL, " +
                "UNIQUE (" + username + ")," +
                "CONSTRAINT id_restoran_fk FOREIGN KEY (" + id_resto + ") REFERENCES " + ref_table_resto + "(" + id + "))";
        try{
            jdbcTemplate.execute(query);
        } catch (Exception ex){
            System.out.println("Gagal create table user : " + ex.toString());
        }
    }

    @Override
    public void CreateTableRole() {
        String query = "CREATE TABLE IF NOT EXISTS " + table_role +
                "(" +
                id + " INT NOT NULL, " +
                username + " TEXT NOT NULL, " +
                role + " " + role_type + " NOT NULL, " +
                "UNIQUE (" + username + "), " +
                "CONSTRAINT id_user_fk FOREIGN KEY (" + id + ")" + "REFERENCES " + table_name + "(" + id + "))";
        try{
            jdbcTemplate.execute(query);
        } catch (Exception ex){
            System.out.println("Gagal create table role : " + ex.toString());
        }
    }

    @Override
    public void Insert(User user) {
        String query = "INSERT INTO " + table_name +
                "(" +
                username + "," +
                password + "," +
                enabled + "," +
                nama_lengkap + "," +
                nomor_ktp + "," +
                nomor_telepon + "," +
                alamat + "," +
                id_resto +
                ")" + " VALUES (?,?,TRUE,?,?,?,?,?)";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        try{
            jdbcTemplate.update(query, new Object[]{
                    user.getUsername(), hashedPassword, user.getNama_lengkap(),
                    user.getNomor_ktp(), user.getNomor_telepon(), user.getAlamat(),
                    user.getId_resto()
            });
        } catch (Exception ex){
            System.out.println("Gagal insert user : " + ex.toString());
        }
        String query2 = "INSERT INTO " + table_role +
                "(" +
                id + "," +
                username + "," +
                role +
                ")" + " VALUES (?,?,?::" +role_type + ")";
        try {
            jdbcTemplate.update(query2, new Object[]{
                    GetUserIdBerdasarkanUsername(user.getUsername()), user.getUsername(), user.getRole()
            });
        } catch (Exception ex){
            System.out.println("Gagal insert user role : " + ex.toString());
        }
    }

    @Override
    public List<User> getAllUser(int id_restoo, String role) {
        List<User> userList = new ArrayList<>();
        if(role.equals("manager&cashier")){
            String query = "SELECT *,user_roles.role FROM " +
                    table_name + "," + table_role +
                    " WHERE " + id_resto + "=?" +
                    " AND user_roles.username=users.username" +
                    " AND user_roles.role IN (?::" + role_type + ", ?::" + role_type + ")"; //ambil user roles dengan role manager dan cashier
            try{
                userList = jdbcTemplate.query(query, new Object[] {id_restoo, "manager", "cashier"}, new UserMapper());
            } catch (Exception ex){
                System.out.println("Gagal get all user : " + ex.toString());
            }
        } else{
            String query = "SELECT *,user_roles.role FROM " +
                    table_name + "," + table_role +
                    " WHERE " + id_resto + "=?" +
                    " AND user_roles.username=users.username" +
                    " AND user_roles.role=?::" + role_type;
            try{
                userList = jdbcTemplate.query(query, new Object[] {id_restoo, role}, new UserMapper());
            } catch (Exception ex){
                System.out.println("Gagal get all user : " + ex.toString());
            }
        }
        return userList;
    }

    @Override
    public int GetUserIdBerdasarkanUsername(String usernamee) {
        int last_id=0;
//        String query = "SELECT MAX(" + id + ") FROM " + table_name;
        String query = "SELECT " + id + " FROM " + table_name + " WHERE " + username + "=?";
        try{
            last_id = jdbcTemplate.queryForObject(query, new Object[] {usernamee}, Integer.class);
        } catch (Exception ex){
            System.out.println("Gagal get last inserted user id : " + ex.toString());
        }
        return last_id;
    }

    @Override
    public void DeleteUser(int idd) {
        String query = "UPDATE " + table_name + " SET " + enabled + "=? WHERE " + id + "=?";
        jdbcTemplate.update(query, new Object[] {false, idd});
    }

    @Override
    public void DeleteUserAndDependencies(int id_restoo) {
        String query = "UPDATE " + table_name + " SET " + enabled + "=? WHERE " + id_resto + "=?";
        jdbcTemplate.update(query, new Object[] {false, id_restoo});
    }

    @Override
    public void ActivateUser(int idd) {
        String query = "UPDATE " + table_name + " SET " + enabled + "=? WHERE " + id + "=?";
        jdbcTemplate.update(query, new Object[] {true, idd});
    }
}
