package project.blibli.mantapos.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserDao {
    private static final String table_name = "user";
    private static final String id = "id";
    private static final String username = "username";
    private static final String password = "password";
    private static final String role = "role";
    private static final String status = "status";
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

    private static final String ref_table_restaurant = "restaurant";

    public static void CreateTable(){
        Connection connection = DbConnection.startConnection();
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatementRole = null;
        PreparedStatement preparedStatementStatus = null;
        try{
            //Create ENUM FOR ROLE TYPE : OWNER, MANAGER, CASHIER
            preparedStatementRole = connection.prepareStatement(
                    "CREATE TYPE " + role_type + " AS ENUM " + "(" +
                            role_owner + "," +
                            role_manager + "," +
                            role_cashier +
                            ")"
            );
            //Create ENUM FOR STATUS TYPE : ACTIVE (Still hired) or INACTIVE (Not hired anymore)
            preparedStatementStatus = connection.prepareStatement(
                    "CREATE TYPE " + status_type + " AS ENUM " + "(" +
                            status_aktif + "," +
                            status_tidak_aktif +
                            ")"
            );
            //Create TABLE user
            preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + table_name +
                            "(" +
                            id + " SERIAL PRIMARY KEY, " +
                            username + " TEXT NOT NULL, " +
                            password + " TEXT NOT NULL, " +
                            role + " " + role_type + " NOT NULL, " +
                            status + " " + status_type + " NOT NULL, " +
                            nama_lengkap + " TEXT NOT NULL, " +
                            nomor_ktp + " TEXT NOT NULL, " +
                            nomor_telepon + " TEXT NOT NULL, " +
                            alamat + " TEXT NOT NULL, " +
                            id_restaurant + " INT NOT NULL, " +
                            "CONSTRAINT id_restaurant_FK FOREIGN KEY (" + id_restaurant + ") REFERENCES " +ref_table_restaurant + "(" + id + ")"
            );
            preparedStatement.executeUpdate();
            preparedStatementRole.executeUpdate();
            preparedStatementStatus.executeUpdate();
            System.out.println("Create table user success!");
        } catch (Exception ex){
            System.out.println("Create table user failed : " + ex.toString());
        } finally {
            DbConnection.ClosePreparedStatement(preparedStatement);
            DbConnection.ClosePreparedStatement(preparedStatementRole);
            DbConnection.ClosePreparedStatement(preparedStatementStatus);
            DbConnection.CloseConnection(connection);
        }
    }
}
