package project.blibli.mantapos.Dao;

import project.blibli.mantapos.Beans_Model.Restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RestaurantDao {
    private static final String table_name = "restaurant";
    private static final String id = "id";
    private static final String restaurant_name = "restaurant_name";
    private static final String restaurant_address = "restaurant_address";

    public static void CreateTable(){
        Connection connection = DbConnection.startConnection();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + table_name +
                            "(" +
                            id + " SERIAL PRIMARY KEY, " +
                            restaurant_name + " TEXT NOT NULL, " +
                            restaurant_address + " TEXT NOT NULL)"
            );
            preparedStatement.executeUpdate();
            System.out.println("Create table restaurant success!");
        } catch (Exception ex){
            System.out.println("Create table restaurant failed : " + ex.toString());
        } finally {
            DbConnection.ClosePreparedStatement(preparedStatement);
            DbConnection.CloseConnection(connection);
        }
    }

    public static int Insert(Restaurant restaurant){
        int status = 0;
        Connection connection = DbConnection.startConnection();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO " + table_name +
                            "(" +
                            restaurant_name + "," +
                            restaurant_address +
                            ")" +
                            " VALUES (?,?)"
            );
            preparedStatement.setString(1, restaurant.getRestaurantName());
            preparedStatement.setString(2, restaurant.getRestaurantAddress());
            status = preparedStatement.executeUpdate();
            if (status==1)
                System.out.println("Insert into table restaurant success!");
        } catch (Exception ex){
            System.out.println("Insert into table restaurant failed : " + ex.toString());
        } finally {
            DbConnection.ClosePreparedStatement(preparedStatement);
            DbConnection.CloseConnection(connection);
        }
        return status;
    }
}
