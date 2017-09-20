package project.blibli.mantapos.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class OrderedMenuDao {
    private static final String table_name = "ordered_menu";
    private static final String id_order = "id_order";
    private static final String id_menu = "id_menu";
    private static final String qty = "quantity";

    public static void CreateTable(){
        Connection connection = DbConnection.startConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + table_name +
                            "(" +
                            id_order + " INT NOT NULL, " +
                            id_menu + " INT NOT NULL, " +
                            qty + " INT NOT NULL)"
            );
            preparedStatement.executeUpdate();
            System.out.println("Success create table ordered menu!");
        } catch (Exception ex){
            System.out.println("Error creating table ordered menu : " + ex.toString());
        } finally {
            DbConnection.ClosePreparedStatement(preparedStatement);
            DbConnection.CloseConnection(connection);
        }
    }
}
