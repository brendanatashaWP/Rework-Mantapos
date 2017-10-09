package project.blibli.mantapos.ImplementationDao;

import org.springframework.jdbc.core.JdbcTemplate;
import project.blibli.mantapos.Beans_Model.User;
import project.blibli.mantapos.Config.DataSourceConfig;
import project.blibli.mantapos.InterfaceDao.UserDao;

public class UserDaoImpl implements UserDao{
    private static final String table_name = "users";
    private static final String id = "id";
    private static final String username = "username";
    private static final String password = "password";
    private static final String role = "role";
    private static final String status_user = "status";
    private static final String enabled = "enabled";
    private static final String nama_lengkap = "nama_lengkap";
    private static final String nomor_ktp = "nomor_ktp";
    private static final String nomor_telepon = "nomor_telepon";
    private static final String alamat = "alamat";
    private static final String id_restaurant = "id_restaurant";

    private static final String role_type = "role_type";
    private static final String role_owner = "owner";
    private static final String role_manager = "manager";
    private static final String role_cashier = "cashier";
    private static final String status_type = "status_type";
    private static final String status_aktif = "active";
    private static final String status_tidak_aktif = "inactive";

    private static final String table_role = "user_roles";

    private static final String ref_table_restaurant = "restaurant";

    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public UserDaoImpl(){
        jdbcTemplate.setDataSource(DataSourceConfig.dataSource());
    }

    @Override
    public void CreateRole() {
        String query = "CREATE TYPE " + role_type + " AS ENUM " + "(" +
                "'" + role_owner + "'," +
                "'" + role_manager + "'," +
                "'" + role_cashier + "'" +
                ")";
        jdbcTemplate.execute(query);
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
                id_restaurant + " INT NOT NULL, " +
                "CONSTRAINT id_restaurant_FK FOREIGN KEY (" + id_restaurant + ") REFERENCES " + ref_table_restaurant + "(" + id + "))";
        jdbcTemplate.execute(query);
    }

    @Override
    public void CreateTableRole() {
        String query = "CREATE TABLE IF NOT EXISTS " + table_role +
                "(" +
                username + " TEXT NOT NULL, " +
                role + " " + role_type + " NOT NULL)";
        jdbcTemplate.execute(query);
    }

    @Override
    public int Insert(User user) {
        int status;
        String query = "INSERT INTO " + table_name +
                "(" +
                username + "," +
                password + "," +
                enabled + "," +
                nama_lengkap + "," +
                nomor_ktp + "," +
                nomor_telepon + "," +
                alamat + "," +
                id_restaurant +
                ")" + " VALUES (?,?,TRUE,?,?,?,?,?)";
        status = jdbcTemplate.update(query, new Object[]{
                user.getUsername(), user.getPassword(), user.getNama_lengkap(),
                user.getNomor_ktp(), user.getNomor_telepon(), user.getAlamat(),
                user.getId_restaurant()
        });
        String query2 = "INSERT INTO " + table_role +
                "(" +
                username + "," +
                role +
                ")" + " VALUES (?,?::" +role_type + ")";
        jdbcTemplate.update(query2, new Object[]{
                user.getUsername(), user.getRole()
        });
        return status;
    }
}
