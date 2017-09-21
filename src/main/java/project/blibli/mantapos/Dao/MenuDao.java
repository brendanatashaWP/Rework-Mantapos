package project.blibli.mantapos.Dao;

import project.blibli.mantapos.Beans_Model.Menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MenuDao {
    private static final String table_name = "menu";
    private static final String id = "id";
    private static final String name = "name";
    private static final String price = "price";
    private static final String photo_link = "photo_link";
    private static final String category = "category";

    public static void CreateTable(){
        Connection connection = DbConnection.startConnection();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + table_name +
                            "(" + id + " SERIAL PRIMARY KEY, " +
                            name + " TEXT NOT NULL, " +
                            price + " REAL NOT NULL, " +
                            photo_link + " TEXT NOT NULL, " +
                            category + " TEXT NOT NULL)"
            );
            preparedStatement.executeUpdate();
            System.out.println("Success create table menu!");
        } catch (Exception ex){
            System.out.println("Failed create table menu : " + ex.toString());
        } finally {
            DbConnection.ClosePreparedStatement(preparedStatement);
            DbConnection.CloseConnection(connection);
        }
    }

    public static int Insert(Menu menu){
        int status=0;
        Connection connection = DbConnection.startConnection();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO " + table_name +
                            "(" +
                            name + "," +
                            price + "," +
                            photo_link + "," +
                            category + ")" +
                            "VALUES (?,?,?,?)"
            );
            preparedStatement.setString(1, menu.getName_menu());
            preparedStatement.setInt(2, menu.getPrice_total_menu());
            preparedStatement.setString(3, menu.getPhoto_location_menu());
            preparedStatement.setString(4, menu.getCategory_menu());
            status = preparedStatement.executeUpdate();
            if (status==1){
                System.out.println("Success insert into table menu");
            }
        } catch (Exception ex){
            System.out.println("Failed inserting menu : " + ex.toString());
        } finally {
            DbConnection.ClosePreparedStatement(preparedStatement);
            DbConnection.CloseConnection(connection);
        }
        return status;
    }

    public static List<Menu> getAll(){
        List<Menu> menuList = new ArrayList<>();
        Connection connection = DbConnection.startConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM " + table_name
            );
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Menu menu = new Menu();
                menu.setId_menu(resultSet.getInt(id));
                menu.setName_menu(resultSet.getString(name));
                menu.setPrice_total_menu(resultSet.getInt(price));
                menu.setPhoto_location_menu(resultSet.getString(photo_link));
                menu.setCategory_menu(resultSet.getString(category));
                menuList.add(menu);
            }
        } catch (Exception ex){
            System.out.println("Failed get all data from menu : " + ex.toString());
        } finally {
            DbConnection.CloseResultSet(resultSet);
            DbConnection.ClosePreparedStatement(preparedStatement);
            DbConnection.CloseConnection(connection);
        }
        return menuList;
    }

    public static String getMenuNameById(int id_target){
        String menu_name = null;
        Connection connection = DbConnection.startConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT " + name + " FROM " + table_name + " WHERE " + id + "=?"
            );
            preparedStatement.setInt(1, id_target);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                menu_name = resultSet.getString(name);
            }
        } catch (Exception ex){
            System.out.println("Error get nemu name by Id : " + ex.toString());
        } finally {
            DbConnection.CloseResultSet(resultSet);
            DbConnection.ClosePreparedStatement(preparedStatement);
            DbConnection.CloseConnection(connection);
        }
        return menu_name;
    }
}
