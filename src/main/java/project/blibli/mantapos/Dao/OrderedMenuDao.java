package project.blibli.mantapos.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class OrderedMenuDao {
    private static final String table_name = "ordered_menu";
    private static final String id_order = "id_order";
    private static final String id_menu = "id_menu";
    private static final String menu_name = "menu";
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
                            menu_name + " TEXT NOT NULL, " +
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

    public static int Insert(int lastId_IdOrder, int id_order_menu, int qty_insert){
        int status = 0;
        Connection connection = DbConnection.startConnection();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO " + table_name +
                            "(" +
                            id_order + "," +
                            id_menu + "," +
                            menu_name + "," +
                            qty + ")" +
                            " VALUES (?,?,?,?)"
            );
            preparedStatement.setInt(1, lastId_IdOrder);
            preparedStatement.setInt(2, id_order_menu);
            preparedStatement.setString(3, MenuDao.getMenuNameById(id_order_menu));
            preparedStatement.setInt(4, qty_insert);

            status = preparedStatement.executeUpdate();
            if (status==1)
                System.out.println("Insert into table ordered_menu success!");
        } catch (Exception ex){
            System.out.println("Failed insert array id order : " + ex.toString());
        } finally {
            DbConnection.ClosePreparedStatement(preparedStatement);
            DbConnection.CloseConnection(connection);
        }
        return status;
    }
}
