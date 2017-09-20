package project.blibli.mantapos.Dao;

import java.sql.*;

public class DbConnection {
    private static Connection connection;

    public static Connection startConnection(){
        try{
            connection = DriverManager.getConnection(
              "jdbc:postgresql://localhost:1414/SinauSpringBoot",
              "postgres",
              "password"
            );
            System.out.println("DB connection success!");
        } catch (Exception ex){
            System.out.println("Error start connection DB : " + ex.toString());
        }
        return connection;
    }

    public static void CloseConnection(Connection connection){
        if(connection==null){
            return;
        } else{
            try {
                connection.close();
                System.out.println("Connection closed successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed closing connection : " + e.toString());
            }
        }
    }
    public static void ClosePreparedStatement(PreparedStatement preparedStatement){
        if(preparedStatement==null){
            return;
        } else{
            try {
                preparedStatement.close();
                System.out.println("Prepared Statement closed successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed closing prepared statement : " + e.toString());
            }
        }
    }
    public static void CloseResultSet(ResultSet resultSet){
        if(resultSet==null){
            return;
        } else{
            try {
                resultSet.close();
                System.out.println("Result Set closed successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed closing result set : " + e.toString());
            }
        }
    }
}
