package project.blibli.mantapos.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CategoryDao {
    private static final String table_name = "category";
    private static final String id = "id";
    private static final String category = "name";

    public static void CreateTable(){
        Connection connection = DbConnection.startConnection();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + table_name +
                            "(" + id + " SERIAL PRIMARY KEY, " +
                            category + " TEXT NOT NULL)"
            );
            preparedStatement.executeUpdate();
        } catch (Exception ex){
            System.out.println("Failed create table category : " + ex.toString());
        } finally {
            DbConnection.ClosePreparedStatement(preparedStatement);
            DbConnection.CloseConnection(connection);
        }
    }
}
